package com.alexqzhang.history.manage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexqzhang.history.ui.HistoryViewHolder;

import net.nurik.roman.muzei.R;

import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder> {
    private List<History> histories = new LinkedList<>();

    public HistoryAdapter(List<History> histories) {
        this.histories = histories;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        HistoryViewHolder historyViewHolder = new HistoryViewHolder(view);
        return historyViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        History history = histories.get(position);
        holder.textView.setText(history.getName());
        holder.imageView.setImageResource(history.getResId());
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }
}
