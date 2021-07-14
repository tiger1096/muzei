package com.google.android.apps.muzei;

import android.app.WallpaperManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nice.render.AlexWallpaperService;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nice.seeyou.R;
import com.nice.seeyou.databinding.IntroFragmentBinding;

public class WelcomeFragment extends Fragment {

    private IntroFragmentBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.intro_fragment, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding = IntroFragmentBinding.bind(view);
        binding.activateMuzei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("alex", "WelcomeFragment onClick");

                try {
                    Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
                    intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                            new ComponentName(requireContext(), AlexWallpaperService.class));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
//                    Log.e("alex", "WelcomeFragment ActivityNotFoundException");
                }
            }
        });
    }
}
