package com.alexqzhang.history.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.nurik.roman.muzei.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryViewHolder extends RecyclerView.ViewHolder {

    public TextView textView;
    public ImageView imageView;

    public HistoryViewHolder(@NonNull View itemView) {
        super(itemView);

        textView = itemView.findViewById(R.id.title);
        imageView = itemView.findViewById(R.id.artwork);
    }
}
