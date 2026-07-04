ALTER TABLE batch_customer ADD COLUMN computing_power_remain decimal(18,2) DEFAULT 0.00 COMMENT '剩余算力' AFTER computing_power_used;
