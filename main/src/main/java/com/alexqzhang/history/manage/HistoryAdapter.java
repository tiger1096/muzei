package com.alexqzhang.history.manage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alexqzhang.history.ui.HistoryBottomHolder;
import com.alexqzhang.history.ui.HistoryViewHolder;

import com.nice.seeyou.R;

import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryAdapter extends RecyclerView.Adapter {

    private final static int NORMAL_ITEM_TYPE = 0;
    private final static int FETCH_LOADING_ITEM_TYPE = 1;

    private List<History> histories = new LinkedList<>();
    private boolean isEnded = false;

    public HistoryAdapter(List<History> histories) {
        this.histories = histories;
    }

    public void appendHistories(List<History> oldHistories) {
        this.histories.addAll(oldHistories);
    }

    public void appendEndToHistories() {
        isEnded = true;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == NORMAL_ITEM_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
            HistoryViewHolder historyViewHolder = new HistoryViewHolder(view);
            return historyViewHolder;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_fetch_item, parent, false);
            HistoryBottomHolder historyViewHolder = new HistoryBottomHolder(view);
            return historyViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HistoryViewHolder) {
            History history = histories.get(position);
            ((HistoryViewHolder) holder).textView.setText(history.getName());
            ((HistoryViewHolder) holder).imageView.setImageResource(history.getResId());
        } else {
            if (isEnded) {
                ((HistoryBottomHolder) holder).textView.setText(R.string.fetch_history_end);
            } else {
                ((HistoryBottomHolder) holder).textView.setText(R.string.fetch_history);
            }
        }
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == histories.size() - 1) {
            return FETCH_LOADING_ITEM_TYPE;
        } else {
            return NORMAL_ITEM_TYPE;
        }
    }
}
