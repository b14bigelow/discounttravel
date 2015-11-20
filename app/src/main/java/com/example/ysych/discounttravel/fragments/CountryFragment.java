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
import com.example.ysych.discounttravel.adapters.RecyclerViewAdapter;
import com.example.ysych.discounttravel.data.HelperFactory;
import com.example.ysych.discounttravel.model.Tour;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by ysych on 05.11.2015.
 */
public class CountryFragment extends Fragment{

    public final static String COUNTRY_CODE = "country";

    List<Tour> tours;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_country, container, false);

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());

        Bundle bundle = getArguments();
        long countryCode = bundle.getLong(COUNTRY_CODE);

        rv.setLayoutManager(llm);
        try {
            if(countryCode == R.id.all_tours){
                QueryBuilder<Tour, Integer> queryBuilder = HelperFactory.getHelper().getTourDAO().queryBuilder();
                queryBuilder.setWhere(queryBuilder.where().not().eq(Tour.CAT_ID, 2));
                tours = HelperFactory.getHelper().getTourDAO().query(queryBuilder.prepare());
            }
            else {
                tours = HelperFactory.getHelper().getTourDAO().queryForEq(Tour.CAT_ID, countryCode);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(tours, getContext());
        rv.setAdapter(adapter);
        return view;
    }
}
