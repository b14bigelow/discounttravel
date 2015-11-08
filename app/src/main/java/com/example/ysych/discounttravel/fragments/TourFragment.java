package com.example.ysych.discounttravel.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ysych.discounttravel.R;
import com.example.ysych.discounttravel.data.HelperFactory;
import com.example.ysych.discounttravel.model.Tour;

import java.sql.SQLException;

/**
 * Created by ysych on 06.11.2015.
 */
public class TourFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tour, container, false);
        Bundle bundle = getArguments();
        int tourSiteId = bundle.getInt(Tour.TOUR_ID);
        try {
            Tour tour = HelperFactory.getHelper().getTourDAO().queryForId(tourSiteId);
            Toast.makeText(getContext(), tour.getCategory(), Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return view;
    }
}
