-- 文档分类字段升级脚本
ALTER TABLE batch_document ADD COLUMN category VARCHAR(100) NULL COMMENT '文档分类';
