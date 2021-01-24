package com.google.android.apps.muzei.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.nurik.roman.muzei.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MaterialViewHolder> {
    public HistoryAdapter() {
    }

    @NonNull
    @Override
    public HistoryAdapter.MaterialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MaterialViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MaterialViewHolder holder, int position) {
        switch (position){
            case 0:
                holder.imageView.setImageResource(R.drawable.bear);
                break;
            case 1:
                holder.imageView.setImageResource(R.drawable.mouse);
                break;
            case 2:
                holder.imageView.setImageResource(R.drawable.sceen);
                break;
            default:
                holder.imageView.setImageResource(R.drawable.sceen);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public static class MaterialViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public MaterialViewHolder(View v) {
            super(v);
            imageView = (ImageView) v.findViewById(R.id.artwork);
            ImageView icon = (ImageView) v.findViewById(R.id.icon);
            icon.setImageResource(R.drawable.gallery_ic_launcher);

            TextView title = (TextView) v.findViewById(R.id.title);
            title.setText("Hello World!");

        }
    }

}
