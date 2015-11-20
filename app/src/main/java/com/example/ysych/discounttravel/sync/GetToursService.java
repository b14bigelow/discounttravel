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

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ysych on 05.11.2015.
 */
public class GetToursService extends IntentService {

    public final static String TOURS_VERSION = "version";
    LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(GetToursService.this);
    SharedPreferences sharedPreferences;
    ARIRetrofit ariRetrofit;
    int currentVersion;

    public GetToursService() {
        super("GetToursService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(APIContract.DISCOUNT_SERVER_URL)
                .build();
        ariRetrofit = restAdapter.create(ARIRetrofit.class);
        sharedPreferences = getSharedPreferences(TOURS_VERSION, MODE_PRIVATE);
        currentVersion = sharedPreferences.getInt(TOURS_VERSION, 0);
        ariRetrofit.getCategories(1, new Callback<List<Country>>() {
            @Override
            public void success(List<Country> countries, Response response) {
                try {
                    List<Country> countryList = HelperFactory.getHelper().getCountryDAO().queryForAll();
                    HelperFactory.getHelper().getCountryDAO().delete(countryList);
                    for (Country category : countries) {
                        HelperFactory.getHelper().getCountryDAO().createOrUpdate(category);
                    }
                    getTours();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if(currentVersion == 0){
                    localBroadcastManager.sendBroadcast(new Intent(SplashActivity.GET_TOURS_RECEIVER_ACTION_FINISH_APP));
                }
                else {
                    localBroadcastManager.sendBroadcast(new Intent(SplashActivity.GET_TOURS_RECEIVER_ACTION_ASK_USER));
                }
            }
        });
    }
    void getTours(){
        ariRetrofit.getTours(currentVersion, new Callback<Tours>() {
            @Override
            public void success(Tours tours, Response response) {
                try {
                    for (Tour tour : tours.getTours()) {
                        if(tour.getState() == 1){
                            HelperFactory.getHelper().getTourDAO().createOrUpdate(tour);
                        }
                        else if(tour.getState() == 0) {
                            HelperFactory.getHelper().getTourDAO().deleteById(tour.getId());
                        }
                    }
                    sharedPreferences.edit().putInt(TOURS_VERSION, tours.getVersion()).apply();
                    localBroadcastManager.sendBroadcast(new Intent(SplashActivity.GET_TOURS_RECEIVER_ACTION_START_APP));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                localBroadcastManager.sendBroadcast(new Intent(SplashActivity.GET_TOURS_RECEIVER_ACTION_ASK_USER));
            }
        });
    }
}