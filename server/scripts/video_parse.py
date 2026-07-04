#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
视频无水印解析脚本（抖音 / 小红书）
供 Java 后端通过 subprocess 调用。

依赖：
    - Python 3.8+
    - requests
    - playwright（并执行 playwright install chromium）

用法：
    python video_parse.py "https://v.douyin.com/xxxxx"
    python video_parse.py "https://www.xiaohongshu.com/explore/xxxxx"

输出（stdout）：
    成功：{"platform": "douyin", "videoUrl": "...", "coverUrl": "...", "title": "...", "author": "...", "images": [], "text": "..."}
    失败：{"error": "友好的错误说明"}
"""

import json
import re
import ssl
import sys
import urllib.parse
import urllib.request
from typing import Dict, List, Optional

import requests

# ---------------------------------------------------------------------------
# 通用工具
# ---------------------------------------------------------------------------

SSL_CONTEXT = ssl.create_default_context()
SSL_CONTEXT.check_hostname = False
SSL_CONTEXT.verify_mode = ssl.CERT_NONE


def _json_friendly(obj: dict) -> str:
    return json.dumps(obj, ensure_ascii=False)


def _fail(message: str) -> str:
    return _json_friendly({"error": message})


# ---------------------------------------------------------------------------
# 抖音解析
# ---------------------------------------------------------------------------

DOUYIN_UA = (
    "Mozilla/5.0 (iPhone; CPU iPhone OS 16_6 like Mac OS X) "
    "AppleWebKit/605.1.15 (KHTML, like Gecko) Version/16.6 "
    "Mobile/15E148 Safari/604.1"
)


def _extract_douyin_aweme_id(short_url: str) -> str:
    req = urllib.request.Request(
        short_url,
        headers={
            "User-Agent": DOUYIN_UA,
            "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
        },
        method="HEAD",
    )
    try:
        with urllib.request.urlopen(req, timeout=20, context=SSL_CONTEXT) as resp:
            final_url = resp.geturl()
    except Exception as e:
        raise ValueError(f"访问抖音链接失败: {e}")

    match = re.search(r"/video/(\d+)", final_url)
    if match:
        return match.group(1)
    raise ValueError("无法从抖音链接中提取视频ID")


def _build_douyin_detail_url(aweme_id: str) -> str:
    params = {
        "aweme_id": aweme_id,
        "aid": "6383",
        "device_platform": "webapp",
        "channel": "channel_pc_web",
        "pc_client_type": "1",
        "version_code": "170400",
        "version_name": "17.4.0",
        "cookie_enabled": "true",
        "platform": "PC",
        "downlink": "10",
    }
    return f"https://www.douyin.com/aweme/v1/web/aweme/detail/?{urllib.parse.urlencode(params)}"


def _parse_douyin_with_playwright(link: str) -> dict:
    try:
        from playwright.sync_api import sync_playwright
    except ImportError as e:
        raise RuntimeError(f"缺少 playwright 依赖: {e}")

    aweme_id = _extract_douyin_aweme_id(link)
    api_url = _build_douyin_detail_url(aweme_id)

    with sync_playwright() as p:
        browser = p.chromium.launch(
            headless=True,
            args=[
                "--disable-blink-features=AutomationControlled",
                "--disable-web-security",
                "--disable-features=IsolateOrigins,site-per-process",
            ],
        )
        context = browser.new_context(
            viewport={"width": 1280, "height": 800},
            user_agent=(
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 "
                "(KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"
            ),
            locale="zh-CN",
            timezone_id="Asia/Shanghai",
        )
        page = context.new_page()
        try:
            page.goto("https://www.douyin.com/", wait_until="domcontentloaded")
            js = f"""
            (async () => {{
                try {{
                    const resp = await fetch('{api_url}', {{
                        headers: {{ 'Accept': 'application/json, text/plain, */*', 'Referer': 'https://www.douyin.com/' }}
                    }});
                    const text = await resp.text();
                    return {{ ok: resp.ok, status: resp.status, text: text }};
                }} catch (e) {{
                    return {{ error: e.toString() }};
                }}
            }})()
            """
            eval_result = page.evaluate(js)
        finally:
            context.close()
            browser.close()

    if eval_result.get("error"):
        raise ValueError(f"浏览器请求抖音接口失败: {eval_result['error']}")
    if not eval_result.get("ok"):
        raise ValueError(f"抖音接口返回 HTTP {eval_result.get('status')}")

    text = eval_result.get("text", "")
    if not text:
        raise ValueError("抖音接口返回为空，可能是反爬策略已升级")

    try:
        api_json = json.loads(text)
    except json.JSONDecodeError as e:
        raise ValueError(f"抖音接口返回非 JSON: {e}")

    if api_json.get("error"):
        raise ValueError(f"抖音接口错误: {api_json['error']}")
    if "aweme_detail" not in api_json:
        status_msg = api_json.get("status_msg", "未知错误")
        raise ValueError(f"抖音接口返回异常: {status_msg}")

    detail = api_json["aweme_detail"]
    video = detail.get("video", {})
    author = detail.get("author", {})
    play_addr = video.get("play_addr", {})
    play_urls = play_addr.get("url_list", []) if isinstance(play_addr, dict) else []
    if not play_urls:
        raise ValueError("未找到抖音无水印视频地址")

    cover_url = ""
    if video.get("cover"):
        cover_url = video["cover"].get("url_list", [""])[0]

    return {
        "platform": "douyin",
        "videoUrl": play_urls[0],
        "coverUrl": cover_url,
        "title": detail.get("desc", ""),
        "author": author.get("nickname", ""),
        "images": [],
        "text": detail.get("desc", ""),
    }


# ---------------------------------------------------------------------------
# 小红书解析
# ---------------------------------------------------------------------------

XHS_UA = (
    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
    "AppleWebKit/537.36 (KHTML, like Gecko) "
    "Chrome/128.0.0.0 Safari/537.36"
)

XHS_HEADERS = {
    "User-Agent": XHS_UA,
    "Referer": "https://www.xiaohongshu.com/",
    "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8",
    "Accept-Language": "zh-CN,zh;q=0.9,en;q=0.8",
    "Cache-Control": "no-cache",
    "Pragma": "no-cache",
    "Sec-Ch-Ua": '"Chromium";v="128", "Not;A=Brand";v="24", "Google Chrome";v="128"',
    "Sec-Ch-Ua-Mobile": "?0",
    "Sec-Ch-Ua-Platform": '"Windows"',
    "Sec-Fetch-Dest": "document",
    "Sec-Fetch-Mode": "navigate",
    "Sec-Fetch-Site": "none",
    "Sec-Fetch-User": "?1",
    "Upgrade-Insecure-Requests": "1",
}


def _extract_xhs_note_id(url: str) -> str:
    m = re.search(r"/(?:discovery/item|explore)/([a-zA-Z0-9]+)", url)
    if m:
        return m.group(1)
    if "xhslink" in url.lower():
        try:
            r = requests.head(url, headers=XHS_HEADERS, allow_redirects=True, timeout=20)
            final = r.url
            m = re.search(r"/(?:discovery/item|explore)/([a-zA-Z0-9]+)", final)
            if m:
                return m.group(1)
        except Exception as e:
            raise ValueError(f"小红书短链解析失败: {e}")
    raise ValueError("无法从小红书链接中提取笔记ID")


def _extract_balanced_json(text: str, start_idx: int) -> str:
    depth = 0
    in_string = False
    escape = False
    for i in range(start_idx, len(text)):
        ch = text[i]
        if escape:
            escape = False
            continue
        if ch == "\\" and in_string:
            escape = True
            continue
        if ch == '"' and not in_string:
            in_string = True
            continue
        if ch == '"' and in_string:
            in_string = False
            continue
        if not in_string:
            if ch == "{":
                depth += 1
            elif ch == "}":
                depth -= 1
                if depth == 0:
                    return text[start_idx : i + 1]
    return text[start_idx:]


def _sanitize_js_json(raw: str) -> str:
    raw = re.sub(r":\s*undefined\s*([,}])", r": null\1", raw)
    raw = re.sub(r":\s*undefined\s*$", ": null", raw)
    return raw


def _select_xhs_stream(streams: dict) -> Optional[dict]:
    candidates = []
    for codec in ["h265", "h264", "h266", "av1"]:
        for s in streams.get(codec, []):
            desc = s.get("streamDesc", "")
            is_wm = desc.startswith("WM_")
            url = s.get("masterUrl")
            if not url and s.get("backupUrls"):
                url = s["backupUrls"][0]
            if not url:
                continue
            candidates.append(
                {
                    "codec": codec,
                    "desc": desc,
                    "url": url,
                    "size": s.get("size"),
                    "width": s.get("width"),
                    "height": s.get("height"),
                    "watermarked": is_wm,
                    "priority": (0 if codec == "h265" else 1) + (10 if is_wm else 0),
                }
            )
    if not candidates:
        return None
    candidates.sort(key=lambda x: x["priority"])
    return candidates[0]


def _parse_xiaohongshu(link: str) -> dict:
    note_id = _extract_xhs_note_id(link)
    url = f"https://www.xiaohongshu.com/explore/{note_id}"
    try:
        r = requests.get(url, headers=XHS_HEADERS, timeout=30)
        r.raise_for_status()
        html = r.text
    except Exception as e:
        raise ValueError(f"请求小红书页面失败: {e}")

    marker = "window.__INITIAL_STATE__="
    start = html.find(marker)
    if start == -1:
        raise ValueError("小红书页面未找到初始化数据")

    raw = _extract_balanced_json(html, start + len(marker))
    raw = _sanitize_js_json(raw)
    try:
        state = json.loads(raw)
    except json.JSONDecodeError as e:
        raise ValueError(f"解析小红书数据失败: {e}")

    note_state = state.get("note", {})
    first_note_id = note_state.get("firstNoteId", "")
    note_detail_map = note_state.get("noteDetailMap", {})
    note_detail = note_detail_map.get(first_note_id, {}) if first_note_id else {}
    note = note_detail.get("note", note_detail)

    if not isinstance(note, dict) or not note:
        raise ValueError("小红书笔记数据为空，请检查链接是否有效或是否需要登录/xsec_token")

    # 方案 A：优先使用 originVideoKey（无水印原始视频）
    video_url = ""
    video = note.get("video", {})
    consumer = video.get("consumer", {}) if isinstance(video, dict) else {}
    origin_key = consumer.get("originVideoKey")
    if origin_key:
        video_key = origin_key.replace("\\u002F", "/")
        video_url = f"http://sns-video-bd.xhscdn.com/{video_key}"

    # 方案 B：从 stream 中挑选无水印流
    if not video_url:
        media = video.get("media", {}) if isinstance(video, dict) else {}
        streams = media.get("stream", {})
        best = _select_xhs_stream(streams)
        if best:
            video_url = best["url"]

    if not video_url:
        raise ValueError("未找到小红书无水印视频地址")

    title = note.get("title", "") or ""
    desc = note.get("desc", "") or ""
    text = title or desc
    cover_url = ""
    image_list = note.get("imageList", [])
    if image_list and isinstance(image_list, list):
        first = image_list[0]
        if isinstance(first, dict):
            cover_url = first.get("urlDefault", "") or first.get("url", "")

    author = ""
    user = note.get("user", {})
    if isinstance(user, dict):
        author = user.get("nickname", "")

    return {
        "platform": "xiaohongshu",
        "videoUrl": video_url,
        "coverUrl": cover_url,
        "title": title,
        "author": author,
        "images": [],
        "text": text,
    }


# ---------------------------------------------------------------------------
# 平台识别与入口
# ---------------------------------------------------------------------------

def _detect_platform(link: str) -> str:
    lower = link.lower()
    if "douyin" in lower or "iesdouyin" in lower:
        return "douyin"
    if "xiaohongshu" in lower or "xhslink" in lower:
        return "xiaohongshu"
    raise ValueError("暂不支持该平台链接解析，目前仅支持抖音、小红书")


def main():
    try:
        sys.stdout.reconfigure(encoding='utf-8')
        sys.stderr.reconfigure(encoding='utf-8')
    except Exception:
        pass

    if len(sys.argv) < 2:
        print(_fail("缺少分享链接参数"))
        sys.exit(1)

    link = sys.argv[1].strip()
    if not link.startswith("http"):
        print(_fail("链接格式不正确，请以 http(s):// 开头"))
        sys.exit(1)

    try:
        platform = _detect_platform(link)
        if platform == "douyin":
            result = _parse_douyin_with_playwright(link)
        else:
            result = _parse_xiaohongshu(link)
        print(_json_friendly(result))
    except Exception as e:
        print(_fail(str(e)))
        sys.exit(1)


if __name__ == "__main__":
    main()
