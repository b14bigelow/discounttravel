package com.example.ysych.discounttravel.sync;

import com.example.ysych.discounttravel.model.Tour;

import java.util.List;

import retrofit.http.GET;

/**
 * Created by nata on 11/7/15.
 */
public interface ARIRetrofit {
    @GET(APIContract.API_URL)
    List<Tour> getTours();
}
