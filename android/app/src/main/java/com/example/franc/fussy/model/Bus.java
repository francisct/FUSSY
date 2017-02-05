package com.example.franc.fussy.model;

import com.example.franc.fussy.helpers.LatLonOperations;

/**
 * Created by franc on 2/4/2017.
 */

public class Bus extends KineticObject {
    public int id;


    static double distanceInMeterForUserToBeInBus = 20.0;

    public Bus(int id){
        this.id = id;
    }

    public Bus(int id, double lat, double lon){
        this.lat =lat;
        this.lon = lon;
    }



    public boolean userHasEnteredBus(User user){
        return  (LatLonOperations.distanceInMetersBetween(lat, lon, user.lat, user.lon) < distanceInMeterForUserToBeInBus);
    }




}
