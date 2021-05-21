package com.alexqzhang.discover.ui;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alexqzhang.util.ScreenUtils;

import com.nice.seeyou.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DiscoverPicHolder extends RecyclerView.ViewHolder {

    public LinearLayout linearLayout;
    public ImageView imageView;

    public DiscoverPicHolder(@NonNull View itemView) {
        super(itemView);
        int screenWidth = ScreenUtils.getScreenWidth();

        linearLayout = itemView.findViewById(R.id.picture_drawer);
        ViewGroup.LayoutParams linearLayoutParams = linearLayout.getLayoutParams();
        linearLayoutParams.width = screenWidth / 3;
        linearLayoutParams.height = linearLayoutParams.width * 16 / 9;
        linearLayout.setLayoutParams(linearLayoutParams);

        imageView = itemView.findViewById(R.id.picture);
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.width = screenWidth / 3 - 10;
        layoutParams.height = layoutParams.width * 16 / 9;
        imageView.setLayoutParams(layoutParams);
    }
}
