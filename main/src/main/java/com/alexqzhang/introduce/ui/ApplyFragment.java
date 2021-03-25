package com.alexqzhang.introduce.ui;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.nurik.roman.muzei.R;
import net.nurik.roman.muzei.databinding.FragmentApplyBinding;
import net.nurik.roman.muzei.databinding.IntroFragmentBinding;

public class ApplyFragment extends Fragment {

    private FragmentApplyBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_apply, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding = FragmentApplyBinding.bind(view);
    }
}