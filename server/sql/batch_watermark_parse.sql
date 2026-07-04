-- ----------------------------
-- AI 去水印解析记录表
-- ----------------------------

drop table if exists batch_watermark_parse;
create table batch_watermark_parse (
  parse_id                  bigint(20)      not null auto_increment    comment '解析记录ID',
  phone                     varchar(20)     not null default ''        comment '账号手机号',
  source_link               varchar(1000)   not null default ''        comment '原始分享链接',
  platform                  varchar(50)     default ''                 comment '解析平台，如 douyin/xiaohongshu',
  video_url                 varchar(2000)   default ''                 comment '去水印后视频 URL',
  image_urls                text                                       comment '视频帧图片 URL 列表，JSON 数组',
  video_text                varchar(2000)   default ''                 comment '视频附带文案/标题',
  parse_status              tinyint(1)      not null default 0         comment '解析状态：0 待解析 / 1 解析中 / 2 已完成 / 9 失败',
  fail_reason               varchar(500)    default ''                 comment '失败原因',
  del_flag                  tinyint(1)      not null default 0         comment '删除标志：0 存在 / 2 删除',
  create_by                 varchar(64)     default ''                 comment '创建者',
  create_time               datetime        default current_timestamp  comment '创建时间',
  update_by                 varchar(64)     default ''                 comment '更新者',
  update_time               datetime        default current_timestamp  on update current_timestamp comment '更新时间',
  remark                    varchar(500)    default ''                 comment '备注',
  primary key (parse_id),
  key idx_batch_watermark_parse_phone (phone),
  key idx_batch_watermark_parse_status (parse_status),
  key idx_batch_watermark_parse_del_flag (del_flag),
  key idx_batch_watermark_parse_create_time (create_time)
) engine=innodb auto_increment=1 comment = 'AI 去水印解析记录表';
