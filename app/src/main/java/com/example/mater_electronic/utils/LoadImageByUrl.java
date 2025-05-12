package com.example.mater_electronic.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mater_electronic.R;

public class LoadImageByUrl {
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.processing)
                .into(view);
    }
}
