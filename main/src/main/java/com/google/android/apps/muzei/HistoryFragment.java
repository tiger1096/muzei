package com.google.android.apps.muzei;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import net.nurik.roman.muzei.R;
import net.nurik.roman.muzei.databinding.HistoryFragmentBinding;

public class HistoryFragment extends Fragment {
    private static final String TAG = "HistoryFragment";

    private HistoryFragmentBinding binding;

    public HistoryFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.choose_provider_fragment, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding = HistoryFragmentBinding.bind(view);

//        Another style：
//        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
//        mToolbar.inflateMenu(R.menu.history_pick);
        binding.toolbar.inflateMenu(R.menu.history_pick);

    }
}
