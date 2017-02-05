package com.example.franc.fussy.model;

import com.example.franc.fussy.helpers.LatLonOperations;

/**
 * Created by franc on 2/5/2017.
 */

public class KineticObject {

    public double lat;
    public double lon;

    public double newTime;
    public double oldTime;
    public double oldLat;
    public double oldLon;

    public void updatePos(double lat, double lon){
        oldTime = newTime;
        newTime = System.currentTimeMillis();
        oldLat = lat;
        oldLon = lon;
        this.lat =lat;
        this.lon = lon;
    }

    public double getSpeed(){
        double distance = LatLonOperations.distanceInMetersBetween(lat, lon, oldLat, oldLon);
        //given in m/ms
        double speed = distance / (newTime - oldTime);
        //conversion to km/h
        speed = speed * 3600;

        return speed;
    }
}
