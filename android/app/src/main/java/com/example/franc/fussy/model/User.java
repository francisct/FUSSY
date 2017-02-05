package com.example.franc.fussy.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by franc on 2/4/2017.
 */

public class User extends KineticObject{
    @SerializedName("id")
    public int id;
    public int bus;

    public User(){
        id = 0;
    }
}
