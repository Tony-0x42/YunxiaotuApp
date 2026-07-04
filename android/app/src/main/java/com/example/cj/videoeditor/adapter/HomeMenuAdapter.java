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
import com.example.cj.videoeditor.bean.HomeMenu;
import java.util.List;

public class HomeMenuAdapter extends RecyclerView.Adapter<HomeMenuAdapter.VH> {

    private final List<HomeMenu> data;
    private final OnMenuClickListener listener;

    public interface OnMenuClickListener {
        void onMenuClick(HomeMenu menu, int position);
    }

    public HomeMenuAdapter(List<HomeMenu> data, OnMenuClickListener listener) {
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_menu, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        HomeMenu item = data.get(position);
        if (item.getIconUrl() != null && !item.getIconUrl().isEmpty()) {
            Glide.with(holder.ivIcon.getContext())
                    .load(item.getIconUrl())
                    .placeholder(item.getIconRes() != 0 ? item.getIconRes() : R.drawable.ic_home)
                    .error(item.getIconRes() != 0 ? item.getIconRes() : R.drawable.ic_home)
                    .into(holder.ivIcon);
        } else if (item.getIconRes() != 0) {
            holder.ivIcon.setImageResource(item.getIconRes());
        } else {
            holder.ivIcon.setImageResource(R.drawable.ic_home);
        }
        holder.tvName.setText(item.getName());
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onMenuClick(item, holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvName;

        VH(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.iv_icon);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }
}
