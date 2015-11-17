package com.example.ysych.discounttravel.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ysych.discounttravel.R;
import com.example.ysych.discounttravel.sync.APIContract;

/**
 * Created by nata on 11/17/15.
 */
public class SlideFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slider, container, false);
        ImageView slideImage = (ImageView) view.findViewById(R.id.slide_image);
        Bundle bundle = getArguments();
        String thumbnailImage = (bundle.getString("image")).replace(".jpg", "_M.jpg");
        Glide.with(this)
                .load(APIContract.DISCOUNT_SERVER_URL + "/" + thumbnailImage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(slideImage);
        return view;
    }
}
