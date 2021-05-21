package com.google.android.apps.muzei;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexqzhang.service.WallpaperService;
import com.google.android.apps.muzei.history.HistoryAdapter;

import com.nice.seeyou.R;
import com.nice.seeyou.databinding.HistoryFragmentBinding;

public class HistoryFragment extends Fragment {
    private static final String TAG = "HistoryFragment";

    private HistoryFragmentBinding binding;

    public HistoryFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.O) {
            getActivity().startForegroundService(new Intent(getActivity(), WallpaperService.class));
        } else {
            getActivity().startService(new Intent(getActivity(), WallpaperService.class));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.history_fragment, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding = HistoryFragmentBinding.bind(view);

//        Another style：
//        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
//        mToolbar.inflateMenu(R.menu.history_pick);
        binding.toolbar.inflateMenu(R.menu.history_pick);

        RecyclerView recyclerView = view.findViewById(R.id.history_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        recyclerView.setAdapter(new HistoryAdapter());
    }
}
