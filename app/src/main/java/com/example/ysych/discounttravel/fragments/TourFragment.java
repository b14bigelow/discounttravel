package com.example.ysych.discounttravel.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ysych.discounttravel.R;
import com.example.ysych.discounttravel.data.HelperFactory;
import com.example.ysych.discounttravel.model.Tour;

import java.sql.SQLException;

/**
 * Created by ysych on 06.11.2015.
 */
public class TourFragment extends Fragment {

    Tour tour;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tour, container, false);

        TextView tourDetailTitle = (TextView) view.findViewById(R.id.tour_detail_title);
        TextView tourDetailText = (TextView) view.findViewById(R.id.tour_detail_text);
        ImageView tourDetailPhoto = (ImageView) view.findViewById(R.id.tour_detail_photo);

        Bundle bundle = getArguments();
        int tourSiteId = bundle.getInt(Tour.TOUR_ID);
        try {
            tour = HelperFactory.getHelper().getTourDAO().queryForId(tourSiteId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tourDetailTitle.setText(tour.getTitle());
        tourDetailText.setText(Html.fromHtml(tour.getIntrotext()));
        tourDetailPhoto.setBackgroundResource(R.drawable.relax);
        return view;
    }
}
