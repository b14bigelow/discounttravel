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

/**
 * Created by ysych on 05.11.2015.
 */
public class GetToursService extends IntentService {

    public GetToursService() {
        super("GetToursService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        HttpURLConnection httpURLConnection = null;
        JsonReader reader = null;

        try{
                Uri builtUri = Uri.parse(APIContract.DISCOUNT_API_URL).buildUpon().build();
                URL url = new URL(builtUri.toString());
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();

                reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
                Gson gson = new GsonBuilder().create();

                reader.beginArray();
                Tour tour;
                while (reader.hasNext()){
                    tour = gson.fromJson(reader, Tour.class);
                    HelperFactory.getHelper().getTourDAO().create(tour);
                }
                Thread.sleep(2000);
                LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
                localBroadcastManager.sendBroadcast(new Intent(SplashActivity.GET_TOURS_RECEIVER_ACTION));
        }
        catch (IOException | SQLException | InterruptedException e){
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
