package com.alexqzhang.discover.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alexqzhang.discover.manage.DiscoverPic;
import com.alexqzhang.discover.manage.DiscoverPicAdapter;

import net.nurik.roman.muzei.R;

import java.util.LinkedList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class DiscoverFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static LinkedList<DiscoverPic> pictures = new LinkedList<>();

    static {
        pictures.add(new DiscoverPic(R.drawable.bear, ""));
        pictures.add(new DiscoverPic(R.drawable.sceen, ""));
        pictures.add(new DiscoverPic(R.drawable.mouse, ""));
        pictures.add(new DiscoverPic(R.drawable.preview, ""));
        pictures.add(new DiscoverPic(R.drawable.bear, ""));
        pictures.add(new DiscoverPic(R.drawable.sceen, ""));
        pictures.add(new DiscoverPic(R.drawable.mouse, ""));
        pictures.add(new DiscoverPic(R.drawable.preview, ""));
        pictures.add(new DiscoverPic(R.drawable.bear, ""));
        pictures.add(new DiscoverPic(R.drawable.sceen, ""));
        pictures.add(new DiscoverPic(R.drawable.mouse, ""));
        pictures.add(new DiscoverPic(R.drawable.preview, ""));
        pictures.add(new DiscoverPic(R.drawable.bear, ""));
        pictures.add(new DiscoverPic(R.drawable.sceen, ""));
        pictures.add(new DiscoverPic(R.drawable.mouse, ""));
        pictures.add(new DiscoverPic(R.drawable.preview, ""));
        pictures.add(new DiscoverPic(R.drawable.bear, ""));
        pictures.add(new DiscoverPic(R.drawable.bear, ""));
        pictures.add(new DiscoverPic(R.drawable.bear, ""));
        pictures.add(new DiscoverPic(R.drawable.bear, ""));
    }

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private DiscoverPicAdapter pictureAdapter;
    private GridLayoutManager gridLayoutManager;
    private int lastVisibleHistoryItem = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_discover, container, false);

        swipeRefreshLayout = fragmentView.findViewById(R.id.swipe_refresh_discover);
        swipeRefreshLayout.setOnRefreshListener(this);

        gridLayoutManager = new GridLayoutManager(getContext(), 3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == pictureAdapter.getItemCount() - 1) {
                    return 3;
                }
                return 1;
            }
        });
        pictureAdapter = new DiscoverPicAdapter(pictures);

        recyclerView = fragmentView.findViewById(R.id.discover_pics);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(pictureAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (lastVisibleHistoryItem == pictureAdapter.getItemCount() - 1) {
                    Toast.makeText(getContext(), "开始加载！", Toast.LENGTH_LONG).show();

                    fetchHistories();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                lastVisibleHistoryItem = gridLayoutManager.findLastVisibleItemPosition();
            }
        });

        return fragmentView;
    }

    private void fetchHistories() {
        LinkedList<DiscoverPic> newPictures = new LinkedList<>();
        newPictures.add(new DiscoverPic(R.drawable.bear, ""));
        newPictures.add(new DiscoverPic(R.drawable.sceen, ""));
        newPictures.add(new DiscoverPic(R.drawable.mouse, ""));
        newPictures.add(new DiscoverPic(R.drawable.preview, ""));
        newPictures.add(new DiscoverPic(R.drawable.bear, ""));
        newPictures.add(new DiscoverPic(R.drawable.sceen, ""));
        pictureAdapter.appendHistories(newPictures);
        pictureAdapter.notifyDataSetChanged();
    }

    private void fetchNothing() {
        pictureAdapter.appendEndToHistories();
        pictureAdapter.notifyDataSetChanged();
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