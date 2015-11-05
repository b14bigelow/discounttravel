package com.example.ysych.discounttravel.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ysych.discounttravel.R;

/**
 * Created by ysych on 05.11.2015.
 */
public class FragmentCountry extends Fragment{

    public static final String ARG_PLANET_NUMBER = "country";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_country, container, false);
        return view;
    }
}
