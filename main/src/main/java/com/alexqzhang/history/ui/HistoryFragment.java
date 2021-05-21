package com.alexqzhang.history.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alexqzhang.history.manage.History;
import com.alexqzhang.history.manage.HistoryAdapter;

import com.nice.seeyou.R;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;

public class HistoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static LinkedList<History> histories = new LinkedList<>();

    static {
        histories.add(new History(0, "test1", R.drawable.bear, new Timestamp((new Date()).getTime())));
        histories.add(new History(0, "test2", R.drawable.sceen, new Timestamp((new Date()).getTime())));
        histories.add(new History(0, "test3", R.drawable.mouse, new Timestamp((new Date()).getTime())));
        histories.add(new History(0, "test4", R.drawable.preview, new Timestamp((new Date()).getTime())));
    }

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private HistoryAdapter historyAdapter;
    private LinearLayoutManager linearLayoutManager;
    private int lastVisibleHistoryItem = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_history, container, false);

        swipeRefreshLayout = fragmentView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        linearLayoutManager = new LinearLayoutManager(getContext());
        historyAdapter = new HistoryAdapter(histories);

        recyclerView = fragmentView.findViewById(R.id.histories);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(historyAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (lastVisibleHistoryItem == historyAdapter.getItemCount() - 1) {
                    Toast.makeText(getContext(), "开始加载！", Toast.LENGTH_LONG).show();

                    fetchHistories();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                lastVisibleHistoryItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });

        return fragmentView;
    }

    private void fetchHistories() {
        LinkedList<History> newHistories = new LinkedList<>();
        newHistories.add(new History(0, "test4", R.drawable.preview, new Timestamp((new Date()).getTime())));
        newHistories.add(new History(0, "test5", R.drawable.preview, new Timestamp((new Date()).getTime())));
        newHistories.add(new History(0, "test6", R.drawable.preview, new Timestamp((new Date()).getTime())));
        newHistories.add(new History(0, "test7", R.drawable.preview, new Timestamp((new Date()).getTime())));
        newHistories.add(new History(0, "test8", R.drawable.preview, new Timestamp((new Date()).getTime())));
        historyAdapter.appendHistories(newHistories);
        historyAdapter.notifyDataSetChanged();
    }

    private void fetchNothing() {
        historyAdapter.appendEndToHistories();
        historyAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }
}