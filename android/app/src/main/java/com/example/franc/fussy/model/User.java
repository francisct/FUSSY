package com.example.franc.fussy.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by franc on 2/4/2017.
 */

public class User {
    @SerializedName("id")
    public int id;
    @SerializedName("lat")
    public double lat;
    @SerializedName("lon")
    public double lon;
    public int bus;

    public User(){

    }
}
