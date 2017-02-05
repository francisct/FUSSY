package com.example.franc.fussy;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by franc on 2/5/2017.
 */

public class YourWakefulReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, BusLocationService.class);
        startWakefulService(context, service);
    }

}
