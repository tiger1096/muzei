package com.alexqzhang.history.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexqzhang.history.manage.History;
import com.alexqzhang.history.manage.HistoryAdapter;

import net.nurik.roman.muzei.R;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;

public class HistoryFragment extends Fragment {

    private static LinkedList<History> histories = new LinkedList<>();

    static {
        histories.add(new History(0, "test1", R.drawable.bear, new Timestamp((new Date()).getTime())));
        histories.add(new History(0, "test2", R.drawable.sceen, new Timestamp((new Date()).getTime())));
        histories.add(new History(0, "test3", R.drawable.mouse, new Timestamp((new Date()).getTime())));
        histories.add(new History(0, "test4", R.drawable.preview, new Timestamp((new Date()).getTime())));
    }

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView = fragmentView.findViewById(R.id.histories);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new HistoryAdapter(histories));
        return fragmentView;
    }
}