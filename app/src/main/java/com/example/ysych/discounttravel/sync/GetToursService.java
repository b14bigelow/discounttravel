package com.example.ysych.discounttravel.sync;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

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
            List<Tour> tours = HelperFactory.getHelper().getTourDAO().getAllRoles();

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
                    if (tours != null) {
                        tours.add(tour);
                    }
                }
                if(!tours.isEmpty()){
                    for(Tour tour1 : tours){
                        Toast.makeText(getApplicationContext(), tour1.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                reader.close();

        }
        catch (IOException e){
            Log.e(getClass().getSimpleName(), e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(getClass().getSimpleName(), e.getMessage(), e);
                }
            }
        }

    }
}
