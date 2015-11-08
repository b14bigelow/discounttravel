package com.example.ysych.discounttravel.sync;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;

import com.example.ysych.discounttravel.activities.SplashActivity;
import com.example.ysych.discounttravel.data.HelperFactory;
import com.example.ysych.discounttravel.model.Tour;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

import retrofit.RestAdapter;

/**
 * Created by ysych on 05.11.2015.
 */
public class GetToursService extends IntentService {

    public GetToursService() {
        super("GetToursService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(APIContract.DISCOUNT_SERVER_URL)
                .build();
        ARIRetrofit ariRetrofit = restAdapter.create(ARIRetrofit.class);

        List<Tour> tours = ariRetrofit.getTours();
        try {
            Thread.sleep(2000);
            for(Tour tour : tours){
                HelperFactory.getHelper().getTourDAO().create(tour);
            }
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        }
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.sendBroadcast(new Intent(SplashActivity.GET_TOURS_RECEIVER_ACTION));
    }
}