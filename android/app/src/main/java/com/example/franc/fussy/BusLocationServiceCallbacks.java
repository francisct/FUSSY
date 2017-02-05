package com.example.franc.fussy;

import android.location.Location;

import com.example.franc.fussy.model.Bus;
import com.example.franc.fussy.model.User;

/**
 * Created by franc on 2/5/2017.
 */

public interface BusLocationServiceCallbacks {
    public void findBusPosition(int busNo);
    public Bus getPresumableBus();
    public User getUser();
}
