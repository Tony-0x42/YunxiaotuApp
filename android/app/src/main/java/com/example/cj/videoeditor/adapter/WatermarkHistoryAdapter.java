package com.example.cj.videoeditor.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cj.videoeditor.R;
import com.example.cj.videoeditor.network.dto.WatermarkParseDto;

import java.util.List;

public class WatermarkHistoryAdapter extends RecyclerView.Adapter<WatermarkHistoryAdapter.VH> {

    private final List<WatermarkParseDto> data;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(WatermarkParseDto item);
    }

    public WatermarkHistoryAdapter(List<WatermarkParseDto> data, OnItemClickListener listener) {
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_watermark_history, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        WatermarkParseDto item = data.get(position);

        String sourceLink = item.getSourceLink() != null ? item.getSourceLink() : "";
        holder.tvTitle.setText(sourceLink);

        String platform = item.getPlatform() != null ? item.getPlatform() : "";
        holder.tvPlatform.setText(platform);

        String createTime = item.getCreateTime() != null ? item.getCreateTime() : "";
        holder.tvTime.setText(createTime);

        String previewUrl = item.getVideoUrl();
        if (previewUrl == null || previewUrl.isEmpty()) {
            List<String> images = item.getImageList();
            if (!images.isEmpty()) {
                previewUrl = images.get(0);
            }
        }

        Glide.with(holder.ivThumb.getContext())
                .load(previewUrl)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .into(holder.ivThumb);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(item);
        });
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    static class VH extends RecyclerView.ViewHolder {
        ImageView ivThumb;
        TextView tvTitle;
        TextView tvPlatform;
        TextView tvTime;

        VH(@NonNull View itemView) {
            super(itemView);
            ivThumb = itemView.findViewById(R.id.iv_thumb);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvPlatform = itemView.findViewById(R.id.tv_platform);
            tvTime = itemView.findViewById(R.id.tv_time);
        }
    }
}
