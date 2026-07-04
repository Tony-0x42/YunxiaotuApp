package com.example.cj.videoeditor.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cj.videoeditor.R;
import com.example.cj.videoeditor.adapter.SimpleImageAdapter;
import com.example.cj.videoeditor.network.dto.WatermarkParseDto;

public class WatermarkPreviewDialog extends Dialog {

    private final WatermarkParseDto data;

    public WatermarkPreviewDialog(@NonNull Context context, WatermarkParseDto data) {
        super(context);
        this.data = data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_watermark_preview);

        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tvPlatform = findViewById(R.id.tv_platform);
        TextView tvTime = findViewById(R.id.tv_time);
        TextView tvText = findViewById(R.id.tv_text);
        VideoView videoView = findViewById(R.id.video_view);
        RecyclerView recyclerImages = findViewById(R.id.recycler_images);
        View tvVideoPlaceholder = findViewById(R.id.tv_video_placeholder);

        tvTitle.setText(data.getSourceLink() != null ? data.getSourceLink() : "");
        tvPlatform.setText(data.getPlatform() != null ? data.getPlatform() : "");
        tvTime.setText(data.getCreateTime() != null ? data.getCreateTime() : "");
        tvText.setText(data.getVideoText() != null ? data.getVideoText() : "");

        if (data.getVideoUrl() != null && !data.getVideoUrl().isEmpty()) {
            tvVideoPlaceholder.setVisibility(View.GONE);
            videoView.setVisibility(View.VISIBLE);
            videoView.setVideoPath(data.getVideoUrl());
            MediaController controller = new MediaController(getContext());
            controller.setAnchorView(videoView);
            videoView.setMediaController(controller);
            videoView.setOnPreparedListener(mp -> mp.setLooping(true));
            videoView.start();
        } else if (!data.getImageList().isEmpty()) {
            tvVideoPlaceholder.setVisibility(View.GONE);
            recyclerImages.setVisibility(View.VISIBLE);
            recyclerImages.setLayoutManager(new GridLayoutManager(getContext(), 3));
            recyclerImages.setAdapter(new SimpleImageAdapter(data.getImageList()));
        }

        findViewById(R.id.btn_confirm).setOnClickListener(v -> dismiss());
    }

    @Override
    protected void onStop() {
        super.onStop();
        VideoView videoView = findViewById(R.id.video_view);
        if (videoView != null) {
            videoView.stopPlayback();
        }
    }
}
