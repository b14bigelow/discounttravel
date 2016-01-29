package com.androidapp.ysych.discounttravel.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.android.ysych.discounttravel.R;
import com.androidapp.ysych.discounttravel.data.HelperFactory;
import com.androidapp.ysych.discounttravel.model.Tour;

import java.sql.SQLException;

/**
 * Created by ysych on 07.12.2015.
 */
public class SystemPageFragment extends Fragment {

    Tour page;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_system_page, container, false);
        Bundle bundle = getArguments();
        int pageSiteId = bundle.getInt(Tour.TOUR_ID);

        try {
            page = HelperFactory.getHelper().getTourDAO().queryForId(pageSiteId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        WebView pageDetailText = (WebView) view.findViewById(R.id.page_detail_text);
        pageDetailText.getSettings().setJavaScriptEnabled(true);
        pageDetailText.loadDataWithBaseURL(null, page.getIntrotext(), "text/html", "UTF-8", null);

        return view;
    }
}