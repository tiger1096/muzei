package com.alexqzhang.discover.manage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexqzhang.discover.ui.DiscoverPicHolder;
import com.alexqzhang.history.ui.HistoryBottomHolder;

import com.nice.seeyou.R;

import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DiscoverPicAdapter extends RecyclerView.Adapter {

    private final static int NORMAL_ITEM_TYPE = 0;
    private final static int FETCH_LOADING_ITEM_TYPE = 1;

    private List<DiscoverPic> pictures = new LinkedList<>();
    private boolean isEnded = false;

    public DiscoverPicAdapter(List<DiscoverPic> histories) {
        this.pictures = histories;
    }

    public void appendHistories(List<DiscoverPic> morePictures) {
        this.pictures.addAll(morePictures);
    }

    public void appendEndToHistories() {
        isEnded = true;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == NORMAL_ITEM_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discover_pic_item, parent, false);
            DiscoverPicHolder discoverPicHolder = new DiscoverPicHolder(view);
            return discoverPicHolder;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_fetch_item, parent, false);
            HistoryBottomHolder historyViewHolder = new HistoryBottomHolder(view);
            return historyViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DiscoverPicHolder) {
            DiscoverPic pic = pictures.get(position);
            ((DiscoverPicHolder) holder).imageView.setImageResource(pic.getPicRes());
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
        return pictures.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == pictures.size() - 1) {
            return FETCH_LOADING_ITEM_TYPE;
        } else {
            return NORMAL_ITEM_TYPE;
        }
    }
}
