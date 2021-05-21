package com.alexqzhang.history.ui;

import android.view.View;
import android.widget.TextView;

import com.nice.seeyou.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryBottomHolder extends RecyclerView.ViewHolder {
    public TextView textView;

    public HistoryBottomHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.history_tail);
    }
}
