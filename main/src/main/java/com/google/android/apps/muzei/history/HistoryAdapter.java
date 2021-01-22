package com.google.android.apps.muzei.history;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MaterialViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public static class MaterialViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public MaterialViewHolder(View v) {
            super(v);
//            imageView = (ImageView)v.findViewById(R.id.material_icon);
        }
    }

}
