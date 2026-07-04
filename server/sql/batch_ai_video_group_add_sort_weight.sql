-- AI 云创视频组新增排序权重字段，用于 APP 拖拽排序
alter table batch_ai_video_group add column sort_weight int default 0 comment '排序权重';
