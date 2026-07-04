-- 为 batch_customer 增加 APP 登录密码字段
-- 使用 BCrypt 加密存储
ALTER TABLE batch_customer ADD COLUMN password varchar(100) DEFAULT '' COMMENT '登录密码（BCrypt 加密）' AFTER phone;
