package com.example.ysych.discounttravel.sync;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.example.ysych.discounttravel.activities.SplashActivity;
import com.example.ysych.discounttravel.data.HelperFactory;
import com.example.ysych.discounttravel.model.Tour;

import java.sql.SQLException;
import java.util.List;

import retrofit.RestAdapter;

/**
 * Created by ysych on 05.11.2015.
 */
public class GetToursService extends IntentService {

    public static long timeout;

    public long getTimeout() {
        return timeout;
    }

    public GetToursService() {
        super("GetToursService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(APIContract.DISCOUNT_SERVER_URL)
                .build();
        ARIRetrofit ariRetrofit = restAdapter.create(ARIRetrofit.class);

        timeout = System.currentTimeMillis();
        List<Tour> tours = ariRetrofit.getTours();
        timeout = System.currentTimeMillis() - timeout;

        if(!tours.isEmpty()){
            try {
                HelperFactory.getHelper().getTourDAO().delete(HelperFactory.getHelper().getTourDAO().getAllTours());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            for(Tour tour : tours){
                HelperFactory.getHelper().getTourDAO().create(tour);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.sendBroadcast(new Intent(SplashActivity.GET_TOURS_RECEIVER_ACTION));
    }
}