package com.example.ysych.discounttravel.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ysych.discounttravel.R;
import com.example.ysych.discounttravel.adapters.RVAdapter;
import com.example.ysych.discounttravel.data.HelperFactory;
import com.example.ysych.discounttravel.model.Tour;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by ysych on 05.11.2015.
 */
public class FragmentCountry extends Fragment{

    List<Tour> tours;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_country, container, false);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        try {
            tours = HelperFactory.getHelper().getTourDAO().getAllTours();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        RVAdapter adapter = new RVAdapter(tours, getContext());
        rv.setAdapter(adapter);
        return view;
    }
}
