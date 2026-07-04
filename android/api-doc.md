# 批量剪辑 APP - batch-video-server 后端 API 对接文档

> 后端工程：`D:/project/batch-video-editor/batch-video-server`（RuoYi 3.9.2）  
> 后端端口：`8080`  
> 应用上下文：`/`（无前缀）  
> 基础 Base URL（本地）：`http://localhost:8080/`  
> Android Emulator 访问本地后端请使用：`http://10.0.2.2:8080/`

---

## 1. 通用约定

### 1.1 认证方式

- 后端使用 RuoYi JWT Token 认证。
- 登录成功后返回 `token`，APP 需在后续请求中携带请求头：
  ```http
  Authorization: Bearer <token>
  ```
- Token 配置（`application.yml`）：
  - `token.header = Authorization`
  - `token.prefix = Bearer `
  - `token.expireTime = 30`（分钟）

### 1.2 匿名放行接口

Spring Security 中明确放行：

```
/login
/register
/captchaImage
/                      （静态首页）
/*.html / **.html / **.css / **.js
/profile/**
/swagger-ui.html / /v3/api-docs/** / /swagger-ui/** / /druid/**
```

**所有 `/batch/*` 接口均未加 `@Anonymous` 注解，且 `anyRequest().authenticated()`，因此全部需要 JWT 认证。**  
> 当前后端 `/batch/customer/phone/{phone}` 需要 `batch:customer:query` 权限，**不适合直接作为 APP 登录接口**。APP 手机号+密码登录目前应使用 `/login`，但需要后端在创建 `batch_customer` 时同步创建/维护对应的 `sys_user`（用户名=手机号）账号。

### 1.3 统一响应结构

#### 1.3.1 普通接口：AjaxResult

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": { ... }
}
```

- `code = 200` 表示业务成功；`500` 表示业务失败；`401` 未授权/Token 过期；`403` 无权限；`601` 警告消息。
- 登录接口特殊字段：返回 `token`（与 `data` 同级）：
  ```json
  {
    "code": 200,
    "msg": "操作成功",
    "token": "eyJhbGciOiJIUzUxMiJ9..."
  }
  ```

#### 1.3.2 列表/分页接口：TableDataInfo

```json
{
  "code": 200,
  "msg": "查询成功",
  "total": 100,
  "rows": [
    { ... },
    { ... }
  ]
}
```

- `total`：总记录数。
- `rows`：当前页数据数组。
- 后端使用 PageHelper 分页，请求参数可传 `pageNum`（页码，默认 1）与 `pageSize`（每页条数，默认 10）。

### 1.4 图片/资源 URL

- 后端上传文件保存在 `D:/ruoyi/uploadPath`。
- 访问地址：`http://<host>:8080/profile/<relativePath>`。
- 业务表中图片字段（如 `imageUrl`、`coverUrl`、`qrCodeUrl`）返回的是完整 URL，APP 可直接加载。

---

## 2. 认证相关接口

### 2.1 登录

```http
POST /login
Content-Type: application/json
```

**请求体（LoginBody）：**

| 字段     | 类型   | 必填 | 说明                   |
|----------|--------|------|------------------------|
| username | string | 是   | 手机号/用户名          |
| password | string | 是   | 密码（BCrypt）         |
| code     | string | 否   | 验证码（math 类型）    |
| uuid     | string | 否   | 验证码唯一标识         |

**响应：**

```json
{
  "code": 200,
  "msg": "操作成功",
  "token": "eyJhbGciOiJIUzUxMiJ9..."
}
```

**注意：** 当前 `/login` 校验 `sys_user` 表。若 APP 账号只存在于 `batch_customer` 而无对应 `sys_user`，则登录会失败，需要后端补充 APP 账号登录/注册逻辑。

### 2.2 获取当前登录用户信息

```http
GET /getInfo
Authorization: Bearer <token>
```

**响应：**

```json
{
  "code": 200,
  "msg": "操作成功",
  "user": { "userId": 1, "userName": "admin", "phonenumber": "13800138000", ... },
  "roles": ["admin"],
  "permissions": ["*:*:*"]
}
```

### 2.3 获取路由（后台菜单）

```http
GET /getRouters
Authorization: Bearer <token>
```

### 2.4 注册

```http
POST /register
Content-Type: application/json
```

**请求体（RegisterBody）：**

| 字段         | 类型   | 必填 | 说明               |
|--------------|--------|------|--------------------|
| username     | string | 是   | 用户名             |
| password     | string | 是   | 密码               |
| confirmPassword | string | 是 | 确认密码           |
| code         | string | 否   | 验证码             |
| uuid         | string | 否   | 验证码唯一标识     |

**说明：** `/register` 默认写入 `sys_user`，且受 `sys.account.registerUser` 配置开关控制。APP 扫码注册可能需要后端新增 `/batch/customer/register` 之类的接口。

### 2.5 验证码

```http
GET /captchaImage
```

返回图片流；同时响应头或 Cookie 不携带 uuid，需配合后端默认实现获取。

---

## 3. 客户/APP 账号接口（`/batch/customer`）

权限前缀：`batch:customer`

| 方法 | URL | 权限 | 说明 |
|------|-----|------|------|
| GET | `/batch/customer/list` | `batch:customer:list` | 客户列表（分公司管理员仅看本分公司数据） |
| POST | `/batch/customer/export` | `batch:customer:export` | 导出 Excel |
| POST | `/batch/customer/importTemplate` | `batch:customer:add` | 下载导入模板 |
| POST | `/batch/customer/importData` | `batch:customer:add` | 导入客户（Multipart） |
| GET | `/batch/customer/{customerId}` | `batch:customer:query` | 根据 ID 查询详情 |
| GET | `/batch/customer/phone/{phone}` | `batch:customer:query` | 根据手机号查询客户 |
| POST | `/batch/customer` | `batch:customer:add` | 新增客户 |
| PUT | `/batch/customer` | `batch:customer:edit` | 修改客户 |
| PUT | `/batch/customer/changeStatus` | `batch:customer:edit` | 修改状态（启用/禁用） |
| DELETE | `/batch/customer/{customerIds}` | `batch:customer:remove` | 删除客户 |
| PUT | `/batch/customer/qrCode/{customerId}` | `batch:customer:resetQr` | 生成/重置注册二维码 |
| PUT | `/batch/customer/upgrade/{customerId}` | `batch:customer:upgrade` | 账号升级 |
| PUT | `/batch/customer/migrate/{customerId}` | `batch:customer:migrate` | 账号迁移 |

**BatchCustomer 主要字段：**

| 字段 | 类型 | 说明 |
|------|------|------|
| customerId | long | 客户 ID |
| customerType | int | 1 分公司 / 2 服务商 / 3 个人 |
| customerName | string | 账号名称 |
| contactName | string | 联系人 |
| phone | string | 手机号（全局唯一） |
| parentPhone | string | 上级手机号 |
| branchPhone | string | 所属分公司手机号 |
| maxServiceProvider | int | 分公司最大服务商数量 |
| totalIndividualCapacity | int | 分公司个人账号总容量 |
| maxIndividual | int | 服务商可创建个人上限 |
| computingPowerTotal | decimal | 算力总配额 |
| computingPowerUsed | decimal | 已消耗算力 |
| computingPowerRemain | decimal | 剩余算力 |
| vipExpireDate | date | VIP 有效期（yyyy-MM-dd） |
| qrCodeUrl | string | 注册二维码 URL |
| qrCodeKey | string | 二维码唯一 key |
| status | int | 0 启用 / 1 禁用 |
| subordinateCount | int | 下级数量（非表字段） |

**新增客户请求示例：**

```json
{
  "customerType": 3,
  "customerName": "测试个人",
  "contactName": "张三",
  "phone": "13800138001",
  "parentPhone": "13800138000",
  "computingPowerTotal": 1000,
  "vipExpireDate": "2026-12-31"
}
```

**账号升级请求示例：**

```json
{
  "parentPhone": "13800138000",
  "maxIndividual": 10
}
```

---

## 4. 首页接口（`/batch/home/*`）

权限前缀：`batch:home`

### 4.1 轮播图（`/batch/home/banner`）

| 方法 | URL | 权限 | 说明 |
|------|-----|------|------|
| GET | `/batch/home/banner/list` | `batch:home:list` | 轮播图列表（TableDataInfo） |
| GET | `/batch/home/banner/{bannerId}` | `batch:home:query` | 详情 |
| POST | `/batch/home/banner` | `batch:home:add` | 新增 |
| PUT | `/batch/home/banner` | `batch:home:edit` | 修改 |
| PUT | `/batch/home/banner/changeStatus` | `batch:home:edit` | 修改状态 |
| DELETE | `/batch/home/banner/{bannerIds}` | `batch:home:remove` | 删除 |

**BatchHomeBanner 字段：** `bannerId`, `title`, `imageUrl`, `linkUrl`, `sortWeight`, `status`, `delFlag`

### 4.2 功能入口（`/batch/home/entry`）

| 方法 | URL | 权限 | 说明 |
|------|-----|------|------|
| GET | `/batch/home/entry/list` | `batch:home:list` | 功能入口列表 |
| GET | `/batch/home/entry/{entryId}` | `batch:home:query` | 详情 |
| POST | `/batch/home/entry` | `batch:home:add` | 新增 |
| PUT | `/batch/home/entry` | `batch:home:edit` | 修改 |
| PUT | `/batch/home/entry/changeStatus` | `batch:home:edit` | 修改状态 |
| DELETE | `/batch/home/entry/{entryIds}` | `batch:home:remove` | 删除 |

**BatchHomeEntry 字段：** `entryId`, `entryName`, `iconUrl`, `targetType`(1 页面 / 2 URL / 3 功能码), `targetValue`, `sortWeight`, `status`

### 4.3 业绩喜报（`/batch/home/news`）

| 方法 | URL | 权限 | 说明 |
|------|-----|------|------|
| GET | `/batch/home/news/list` | `batch:home:list` | 喜报列表 |
| GET | `/batch/home/news/{newsId}` | `batch:home:query` | 详情 |
| POST | `/batch/home/news` | `batch:home:add` | 新增 |
| PUT | `/batch/home/news` | `batch:home:edit` | 修改 |
| PUT | `/batch/home/news/changeStatus` | `batch:home:edit` | 修改状态 |
| DELETE | `/batch/home/news/{newsIds}` | `batch:home:remove` | 删除 |

**BatchHomeNews 字段：** `newsId`, `newsTitle`, `championName`, `salesAmount`, `status`

### 4.4 教程入口（`/batch/home/tutorialEntry`）

| 方法 | URL | 权限 | 说明 |
|------|-----|------|------|
| GET | `/batch/home/tutorialEntry/list` | `batch:home:list` | 教程入口列表 |
| GET | `/batch/home/tutorialEntry/{entryId}` | `batch:home:query` | 详情 |
| POST | `/batch/home/tutorialEntry` | `batch:home:add` | 新增 |
| PUT | `/batch/home/tutorialEntry` | `batch:home:edit` | 修改 |
| PUT | `/batch/home/tutorialEntry/changeStatus` | `batch:home:edit` | 修改状态 |
| DELETE | `/batch/home/tutorialEntry/{entryIds}` | `batch:home:remove` | 删除 |
| GET | `/batch/home/tutorialEntry/documentList` | `batch:home:query` | 关联文档下拉列表 |

**BatchHomeTutorialEntry 字段：** `entryId`, `title`, `coverUrl`, `documentId`, `documentTitle`, `sortWeight`, `status`

---

## 5. 教程接口（`/batch/tutorial`）

权限前缀：`batch:tutorial`

| 方法 | URL | 权限 | 说明 |
|------|-----|------|------|
| GET | `/batch/tutorial/list` | `batch:tutorial:list` | 教程列表 |
| GET | `/batch/tutorial/{tutorialId}` | `batch:tutorial:query` | 教程详情 |
| POST | `/batch/tutorial` | `batch:tutorial:add` | 新增教程 |
| PUT | `/batch/tutorial` | `batch:tutorial:edit` | 修改教程 |
| PUT | `/batch/tutorial/changeStatus` | `batch:tutorial:edit` | 修改状态 |
| DELETE | `/batch/tutorial/{tutorialIds}` | `batch:tutorial:remove` | 删除教程 |
| GET | `/batch/tutorial/category/list` | `batch:tutorial:list` | 分类列表 |
| GET | `/batch/tutorial/category/all` | `batch:tutorial:list` | 所有有效分类 |
| GET | `/batch/tutorial/category/{categoryId}` | `batch:tutorial:query` | 分类详情 |
| POST | `/batch/tutorial/category` | `batch:tutorial:add` | 新增分类 |
| PUT | `/batch/tutorial/category` | `batch:tutorial:edit` | 修改分类 |
| DELETE | `/batch/tutorial/category/{categoryIds}` | `batch:tutorial:remove` | 删除分类 |

**BatchTutorial 字段：** `tutorialId`, `tutorialTitle`, `tutorialType`(1 视频 / 2 图文), `categoryId`, `categoryName`, `coverUrl`, `videoUrl`, `documentContent`, `intro`, `sortWeight`, `viewCount`, `status`

**BatchTutorialCategory 字段：** `categoryId`, `categoryName`, `sortWeight`, `status`

---

## 6. 文档接口（`/batch/document`）

权限前缀：`batch:document`

| 方法 | URL | 权限 | 说明 |
|------|-----|------|------|
| GET | `/batch/document/list` | `batch:document:list` | 文档列表 |
| GET | `/batch/document/{documentId}` | `batch:document:query` | 文档详情 |
| POST | `/batch/document` | `batch:document:add` | 新增文档 |
| PUT | `/batch/document` | `batch:document:edit` | 修改文档 |
| PUT | `/batch/document/changeStatus` | `batch:document:edit` | 修改状态 |
| DELETE | `/batch/document/{documentIds}` | `batch:document:remove` | 删除文档 |

**BatchDocument 字段：** `documentId`, `documentTitle`, `documentType`(1 用户协议 / 2 隐私政策 / 3 新手文档 / 4 帮助文档), `applyPages`, `content`, `sortWeight`, `status`, `isSystem`

---

## 7. 公告接口（`/batch/notice`）

权限前缀：`batch:notice`

| 方法 | URL | 权限 | 说明 |
|------|-----|------|------|
| GET | `/batch/notice/list` | `batch:notice:list` | 公告列表 |
| GET | `/batch/notice/{noticeId}` | `batch:notice:query` | 公告详情 |
| GET | `/batch/notice/preview/{noticeId}` | `batch:notice:query` | 预览公告 |
| POST | `/batch/notice` | `batch:notice:add` | 新增公告 |
| PUT | `/batch/notice` | `batch:notice:edit` | 修改公告 |
| DELETE | `/batch/notice/{noticeIds}` | `batch:notice:remove` | 删除公告 |
| PUT | `/batch/notice/publish/{noticeId}` | `batch:notice:publish` | 发布公告 |
| PUT | `/batch/notice/unpublish/{noticeId}` | `batch:notice:edit` | 下架公告 |

**BatchAppNotice 字段：** `noticeId`, `noticeTitle`, `noticeType`(1 通知 / 2 活动 / 3 重要更新), `coverUrl`, `content`, `publishStatus`(0 已发布 / 1 已下架 / 2 暂存), `publishTime`, `readCount`

---

## 8. 算力消耗日志接口（`/batch/computing/log`）

权限前缀：`batch:customer:query`

| 方法 | URL | 说明 |
|------|-----|------|
| GET | `/batch/computing/log/list` | 算力消耗日志列表（TableDataInfo） |

**BatchComputingPowerLog 字段：** `id`, `phone`, `operationType`(1 生成 / 2 下载), `consumeValue`, `remainValue`, `videoGroupName`, `createTime`

---

## 9. VIP 管理接口（`/batch/vip`）

权限前缀：`batch:vip`

| 方法 | URL | 权限 | 说明 |
|------|-----|------|------|
| GET | `/batch/vip/list` | `batch:vip:list` | VIP 客户列表 |
| PUT | `/batch/vip/{customerId}` | `batch:vip:edit` | 修改单个客户 VIP 有效期 |
| PUT | `/batch/vip/batch` | `batch:vip:edit` | 批量修改 VIP 有效期 |

**BatchVipQuery 字段：** 继承 BatchCustomer，并新增 `customerIds`（批量 ID 数组）与 `vipExpireDate`。

---

## 10. 系统配置接口（`/batch/config`）

权限前缀：`batch:config`

| 方法 | URL | 权限 | 说明 |
|------|-----|------|------|
| GET | `/batch/config/brand` | `batch:config:list` | 查询品牌配置 |
| POST | `/batch/config/brand` | `batch:config:edit` | 保存品牌配置 |
| GET | `/batch/config/global` | `batch:config:list` | 查询全局参数 |
| POST | `/batch/config/global` | `batch:config:edit` | 保存全局参数 |
| POST | `/batch/config/initGlobal` | `batch:config:add` | 初始化全局参数默认值 |
| GET | `/batch/config/version/list` | `batch:config:list` | APP 版本列表 |
| GET | `/batch/config/version/{versionId}` | `batch:config:query` | 版本详情 |
| POST | `/batch/config/version` | `batch:config:add` | 新增版本 |
| PUT | `/batch/config/version` | `batch:config:edit` | 修改版本 |
| DELETE | `/batch/config/version/{versionIds}` | `batch:config:remove` | 删除版本 |
| PUT | `/batch/config/version/changeStatus` | `batch:config:edit` | 修改版本状态 |
| GET | `/batch/config/list` | `batch:config:list` | 扩展全局参数列表 |
| GET | `/batch/config/{configId}` | `batch:config:query` | 扩展参数详情 |
| POST | `/batch/config` | `batch:config:add` | 新增扩展参数 |
| PUT | `/batch/config` | `batch:config:edit` | 修改扩展参数 |
| DELETE | `/batch/config/{configIds}` | `batch:config:remove` | 删除扩展参数 |

**品牌配置返回字段：** `appLogo`, `adminLogo`, `productName`, `slogan`, `primaryColor`, `loginBg`

**全局参数返回字段：** `maxVideos`, `sliceMin`, `sliceMax`, `sliceStep`, `emptyTip`, `parseFailTip`, `emptyPlaceholder`, `customerServiceHours`

默认值：

- `maxVideos = 10`
- `sliceMin = 0.5`
- `sliceMax = 10`
- `sliceStep = 0.1`
- `emptyTip = "当前算力已耗尽，请联系管理员增加算力额度"`
- `parseFailTip = "链接解析失败，请检查链接是否有效"`

---

## 11. 数据统计接口（`/batch/statistics`）

权限前缀：`batch:statistics`

| 方法 | URL | 权限 | 说明 |
|------|-----|------|------|
| GET | `/batch/statistics/overview` | `batch:statistics:query` | 今日概览指标（AjaxResult） |
| GET | `/batch/statistics/account` | `batch:statistics:query` | 账号数据明细列表 |
| GET | `/batch/statistics/computing` | `batch:statistics:query` | 算力消耗明细列表 |
| GET | `/batch/statistics/video` | `batch:statistics:query` | 视频生成明细列表 |
| GET | `/batch/statistics/qrcode` | `batch:statistics:query` | 二维码推广明细列表 |
| GET | `/batch/statistics/news` | `batch:statistics:query` | 业绩喜报明细列表 |
| GET | `/batch/statistics/trend` | `batch:statistics:query` | 趋势数据（AjaxResult） |

**查询参数（BatchStatisticsQuery）：** `startDate`, `endDate`, `customerType`, `branchPhone`, `phone`, `days`，以及分页参数 `pageNum`/`pageSize`。

---

## 12. 通用文件接口

| 方法 | URL | 说明 |
|------|-----|------|
| POST | `/common/upload` | 单文件上传（Multipart：file） |
| POST | `/common/uploads` | 多文件上传（Multipart：files） |
| GET | `/common/download?fileName=xxx&delete=false` | 通用下载 |
| GET | `/common/download/resource?resource=xxx` | 本地资源下载 |

上传成功响应示例：

```json
{
  "code": 200,
  "msg": "操作成功",
  "url": "http://localhost:8080/profile/upload/2026/07/03/xxx.jpg",
  "fileName": "...",
  "newFileName": "...",
  "originalFilename": "..."
}
```

---

## 13. APP 端重点关注清单与下一步对接建议

### 13.1 当前可直接对接的只读接口

- 首页：GET `/batch/home/banner/list`、GET `/batch/home/entry/list`、GET `/batch/home/news/list`、GET `/batch/home/tutorialEntry/list`
- 教程：GET `/batch/tutorial/list`、GET `/batch/tutorial/{id}`、GET `/batch/tutorial/category/all`
- 文档：GET `/batch/document/list`、GET `/batch/document/{id}`
- 公告：GET `/batch/notice/list`、GET `/batch/notice/{id}`
- 配置：GET `/batch/config/brand`、GET `/batch/config/global`
- 我的/账号：GET `/batch/customer/phone/{phone}`（仅查询，受权限限制）
- 算力：GET `/batch/computing/log/list`

### 13.2 需要后端补充的接口

| 需求 | 当前后端状态 | 建议 |
|------|--------------|------|
| APP 手机号+密码登录 | 只有 `/login`（校验 `sys_user`） | 建议新增 `/batch/customer/login`（phone + password）或确保创建 customer 时同步创建 sys_user 并返回 token |
| APP 扫码注册 | 只有 `/register`（写入 `sys_user`） | 建议新增 `/batch/customer/register`（上级手机号 + phone + password） |
| APP 修改自己密码 | 无对应接口 | 建议新增 `/batch/customer/changePassword` |
| 算力检查/扣减 | 只有日志查询 | AI 云创生成、去水印下载前建议新增 `/batch/computing/check` 或 `/batch/computing/consume` |
| 视频解析/去水印 | 无 | AI 去水印需要新增解析与下载接口 |
| AI 视频分割/生成 | 无 | AI 云创需要新增任务提交与结果查询接口 |

---

## 14. 附录：HttpStatus 状态码对照

| 状态码 | 含义 |
|--------|------|
| 200 | 操作成功 |
| 201 | 对象创建成功 |
| 400 | 参数列表错误 |
| 401 | 未授权 / Token 无效或过期 |
| 403 | 访问受限 / 无权限 |
| 404 | 资源未找到 |
| 500 | 系统内部错误 / 业务失败 |
| 601 | 系统警告消息 |
