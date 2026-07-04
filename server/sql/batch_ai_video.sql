-- ----------------------------
-- 批量剪辑 - AI 云创业务表
-- 模块：AI 云创视频组、分镜头、生成任务
-- 状态约定：status 0 正常/成功/启用，1 处理中，2 失败，其他按字段说明
-- 删除约定：del_flag 0 存在，2 删除
-- ----------------------------

-- AI 云创视频组
DROP TABLE IF EXISTS batch_ai_video_group;
CREATE TABLE batch_ai_video_group (
  group_id        bigint(20)      NOT NULL AUTO_INCREMENT    COMMENT '视频组ID',
  phone           varchar(20)     NOT NULL DEFAULT ''        COMMENT '所属账号手机号',
  group_name      varchar(200)    NOT NULL DEFAULT ''        COMMENT '视频组名称',
  generated_count int(11)         DEFAULT 0                  COMMENT '已生成次数',
  max_limit       int(11)         DEFAULT 10                 COMMENT '最大可生成次数',
  status          tinyint(1)      NOT NULL DEFAULT 0         COMMENT '状态：0 启用 / 1 禁用',
  del_flag        tinyint(1)      NOT NULL DEFAULT 0         COMMENT '删除标志：0 存在 / 2 删除',
  create_time     datetime        DEFAULT CURRENT_TIMESTAMP  COMMENT '创建时间',
  update_time     datetime        DEFAULT CURRENT_TIMESTAMP  ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (group_id),
  KEY idx_batch_ai_video_group_phone (phone),
  KEY idx_batch_ai_video_group_status (status),
  KEY idx_batch_ai_video_group_del_flag (del_flag)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='AI 云创视频组表';

-- AI 云创分镜头
DROP TABLE IF EXISTS batch_ai_video_clip;
CREATE TABLE batch_ai_video_clip (
  clip_id     bigint(20)      NOT NULL AUTO_INCREMENT    COMMENT '分镜头ID',
  group_id    bigint(20)      NOT NULL DEFAULT 0         COMMENT '所属视频组ID',
  video_url   varchar(500)    DEFAULT ''                 COMMENT '视频/图片素材 URL',
  text_content varchar(500)   DEFAULT ''                 COMMENT '口播文案',
  duration    double          DEFAULT 2.0                COMMENT '时长（秒）',
  sort_order  int(11)         DEFAULT 0                  COMMENT '排序',
  create_time datetime        DEFAULT CURRENT_TIMESTAMP  COMMENT '创建时间',
  update_time datetime        DEFAULT CURRENT_TIMESTAMP  ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (clip_id),
  KEY idx_batch_ai_video_clip_group_id (group_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='AI 云创分镜头表';
