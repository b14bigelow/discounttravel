package com.example.ysych.discounttravel.sync;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;

import com.example.ysych.discounttravel.activities.SplashActivity;
import com.example.ysych.discounttravel.data.HelperFactory;
import com.example.ysych.discounttravel.model.Country;
import com.example.ysych.discounttravel.model.Tour;
import com.example.ysych.discounttravel.model.Tours;

import java.sql.SQLException;
import java.util.List;

import retrofit.RestAdapter;

/**
 * Created by ysych on 05.11.2015.
 */
public class GetToursService extends IntentService {

    public final static String TOURS_VERSION = "version";

    public GetToursService() {
        super("GetToursService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(APIContract.DISCOUNT_SERVER_URL)
                .build();
        ARIRetrofit ariRetrofit = restAdapter.create(ARIRetrofit.class);
        SharedPreferences sharedPreferences = getSharedPreferences(TOURS_VERSION, MODE_PRIVATE);
        int currentVersion = sharedPreferences.getInt(TOURS_VERSION, 0);

        Tours tours = ariRetrofit.getTours(currentVersion);
        List<Country> categories = ariRetrofit.getCategories(1);
        try {
            for(Tour tour : tours.getTours()){
                HelperFactory.getHelper().getTourDAO().createOrUpdate(tour);
            }
            for (Country category : categories){
                HelperFactory.getHelper().getCountryDAO().createOrUpdate(category);
            }
            sharedPreferences.edit().putInt(TOURS_VERSION, tours.getVersion()).apply();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.sendBroadcast(new Intent(SplashActivity.GET_TOURS_RECEIVER_ACTION));
    }
}