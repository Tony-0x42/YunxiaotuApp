-- ----------------------------
-- APP 内容页依赖的扩展表
-- 模块：品牌专区、联系方式
-- ----------------------------

-- 品牌专区表
 drop table if exists batch_brand;
create table batch_brand (
  brand_id                  bigint(20)      not null auto_increment    comment '品牌ID',
  brand_name                varchar(100)    not null default ''        comment '品牌名称',
  logo_url                  varchar(500)    default ''                 comment '品牌LOGO URL',
  intro                     varchar(500)    default ''                 comment '品牌简介',
  detail                    text                                       comment '品牌详细介绍',
  media_urls                text                                       comment '品牌宣传图/视频，逗号分隔',
  sort_weight               int(11)         default 0                  comment '排序权重',
  status                    tinyint(1)      not null default 0         comment '状态：0 启用 / 1 禁用',
  del_flag                  tinyint(1)      not null default 0         comment '删除标志：0 存在 / 2 删除',
  create_by                 varchar(64)     default ''                 comment '创建者',
  create_time               datetime        default current_timestamp  comment '创建时间',
  update_by                 varchar(64)     default ''                 comment '更新者',
  update_time               datetime        default current_timestamp  on update current_timestamp comment '更新时间',
  remark                    varchar(500)    default ''                 comment '备注',
  primary key (brand_id),
  key idx_batch_brand_status (status),
  key idx_batch_brand_del_flag (del_flag),
  key idx_batch_brand_sort_weight (sort_weight)
) engine=innodb auto_increment=1 comment = '品牌专区表';

-- APP 客服联系表
 drop table if exists batch_contact;
create table batch_contact (
  contact_id                bigint(20)      not null auto_increment    comment '联系ID',
  contact_name              varchar(100)    not null default ''        comment '联系人/名称',
  region                    varchar(100)    default ''                 comment '区域/说明',
  phone                     varchar(20)     not null default ''        comment '电话',
  contact_type              tinyint(1)      not null default 3         comment '类型：1 在线客服 / 2 总部热线 / 3 区域联系',
  sort_weight               int(11)         default 0                  comment '排序权重',
  status                    tinyint(1)      not null default 0         comment '状态：0 启用 / 1 禁用',
  del_flag                  tinyint(1)      not null default 0         comment '删除标志：0 存在 / 2 删除',
  create_by                 varchar(64)     default ''                 comment '创建者',
  create_time               datetime        default current_timestamp  comment '创建时间',
  update_by                 varchar(64)     default ''                 comment '更新者',
  update_time               datetime        default current_timestamp  on update current_timestamp comment '更新时间',
  remark                    varchar(500)    default ''                 comment '备注',
  primary key (contact_id),
  key idx_batch_contact_type (contact_type),
  key idx_batch_contact_status (status),
  key idx_batch_contact_del_flag (del_flag),
  key idx_batch_contact_sort_weight (sort_weight)
) engine=innodb auto_increment=1 comment = 'APP 客服联系表';
