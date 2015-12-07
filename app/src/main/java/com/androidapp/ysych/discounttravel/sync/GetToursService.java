package com.androidapp.ysych.discounttravel.sync;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.androidapp.ysych.discounttravel.activities.SplashActivity;
import com.androidapp.ysych.discounttravel.data.HelperFactory;
import com.androidapp.ysych.discounttravel.model.Country;
import com.androidapp.ysych.discounttravel.model.Tour;
import com.androidapp.ysych.discounttravel.model.Tours;
import com.androidapp.ysych.discounttravel.model.Version;

import java.sql.SQLException;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class GetToursService extends IntentService {

    LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(GetToursService.this);
    ARIRetrofit ariRetrofit;
    Version currentVersion;

    public GetToursService() {
        super("GetToursService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        try {
            if(HelperFactory.getHelper().getVersionDAO().queryForAll().size() == 0){
                currentVersion = new Version();
                currentVersion.setVersion(0);
            }
            else {
                currentVersion = HelperFactory.getHelper().getVersionDAO().queryForAll().get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(APIContract.DISCOUNT_SERVER_URL)
                .build();
        ariRetrofit = restAdapter.create(ARIRetrofit.class);
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
                if(currentVersion.getVersion() == 0){
                    localBroadcastManager.sendBroadcast(new Intent(SplashActivity.ACTION_FINISH_APP));
                }
                else {
                    localBroadcastManager.sendBroadcast(new Intent(SplashActivity.ACTION_ASK_USER));
                }
            }
        });
    }
    void getTours(){
        ariRetrofit.getTours(currentVersion.getVersion(), new Callback<Tours>() {
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
                    currentVersion.setVersion(tours.getVersion());
                    HelperFactory.getHelper().getVersionDAO().createOrUpdate(currentVersion);
                    localBroadcastManager.sendBroadcast(new Intent(SplashActivity.ACTION_START_APP));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if(currentVersion.getVersion() == 0){
                    localBroadcastManager.sendBroadcast(new Intent(SplashActivity.ACTION_FINISH_APP));
                }
                else {
                    localBroadcastManager.sendBroadcast(new Intent(SplashActivity.ACTION_ASK_USER));
                }
            }
        });
    }
}