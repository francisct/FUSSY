package com.example.franc.fussy;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.example.franc.fussy.model.Bus;
import com.example.franc.fussy.model.User;

/**
 * Created by franc on 2/5/2017.
 */

public class BusLocationService extends IntentService {

    private static String tagName = "BusLocationService";

    public BusLocationService() {
        super("BusLocationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Start your location
        if (serviceCallbacks != null) {
            Bus bus = serviceCallbacks.getPresumableBus();
            if (bus.id != 0) {
                serviceCallbacks.findBusPosition(bus.id);
                YourWakefulReceiver.completeWakefulIntent(intent);
            }
        }


    }

    // Binder given to clients
    private final IBinder binder = new LocalBinder();
    // Registered callbacks
    private BusLocationServiceCallbacks serviceCallbacks;


    // Class used for the client Binder.
    public class LocalBinder extends Binder {
        BusLocationService getService() {
            // Return this instance of MyService so clients can call public methods
            return BusLocationService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void setCallbacks(BusLocationServiceCallbacks callbacks) {
        serviceCallbacks = callbacks;
    }

}
