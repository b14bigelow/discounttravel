package com.androidapp.ysych.discounttravel.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Tours {

    @SerializedName("version")
    @Expose
    private int version;
    @SerializedName("tours")
    @Expose
    private List<Tour> tours = new ArrayList<>();
    
    public int getVersion() {
        return version;
    }

    public List<Tour> getTours() {
        return tours;
    }
}
