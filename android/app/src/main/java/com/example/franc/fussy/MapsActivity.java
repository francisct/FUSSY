package com.example.franc.fussy;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.example.franc.fussy.model.Bus;
import com.example.franc.fussy.model.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, BusLocationServiceCallbacks  {
    GoogleMap mMap;
    boolean bound = false;
    BusLocationService busLocationService;
    private LocationManager locationManager;
    static int MY_PERMISSION_ACCESS_COURSE_LOCATION;
    User user;
    DataService dataService;
    Bus bus = new Bus(0);
    Marker busMarker;
    AlarmManager alarmMgr;
    PendingIntent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = new User();
        setContentView(R.layout.activity_main);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Acquire a reference to the system Location Manager
         locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

// Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                sendNewLocation(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },MY_PERMISSION_ACCESS_COURSE_LOCATION );
        }

// Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fussybus.azurewebsites.net/api/values/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        dataService = retrofit.create(DataService.class);

        createUser();


       alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, YourWakefulReceiver.class);
        boolean flag = (PendingIntent.getBroadcast(this, 0,
                intent, PendingIntent.FLAG_NO_CREATE)==null);
/*Register alarm if not registered already*/
        if(flag){
            alarmIntent = PendingIntent.getBroadcast(this, 0,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);

/* Setting alarm for every one hour from the current time.*/
            //int intervalTimeMillis = 120; // 1 hour
            alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() +
                            1000, alarmIntent);
        }

    }

    private void setNewAlarm(){
        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() +
                        1000, alarmIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // bind to Service
        Intent intent = new Intent(this, BusLocationService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from service
        if (bound) {
            busLocationService.setCallbacks(null); // unregister
            unbindService(serviceConnection);
            bound = false;
        }
    }

    /** Callbacks for service binding, passed to bindService() */
    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // cast the IBinder and get MyService instance
            BusLocationService.LocalBinder binder = (BusLocationService.LocalBinder) service;
            busLocationService = binder.getService();
            bound = true;
            busLocationService.setCallbacks(MapsActivity.this); // register
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            bound = false;
        }
    };



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }

    private void sendNewLocation(Location location){
        if (user.getSpeed() > 20 && user.bus == 0){
            //it means the user got in a bus that did not have any people on it. the bus was not previously tracked
            //alert user to enter his bus no;
        }
        if (user.id != 0 && user.bus != 0) {
            user.lat = location.getLatitude();
            user.lon = location.getLongitude();
            Call<ResponseBody> call = dataService.updateBusPosition(user.id, user.bus, user.lat, user.lon);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
    }

    public void busButtonOnClick(View v){
        TextView text = (TextView) findViewById(R.id.editText2);
        bus.id = Integer.parseInt(text.getText().toString());

        findBusPosition(bus.id);
    }

    @Override
    public Bus getPresumableBus(){
        return bus;
    }

    @Override
    public User getUser(){
        return user;
    }

    @Override
    public void findBusPosition(int busNo){
        Call<double[]> call= dataService.getBusPosition(busNo);
        call.enqueue(new Callback<double[]>() {
            @Override
            public void onResponse(Call<double[]> call, Response<double[]> response) {
                double[] latlon = response.body();
                if (latlon.length == 2) {
                    double lat; double lon;
                    if (latlon[0] > 0){
                        lat = latlon[0];
                           lon =   latlon[1];
                    }
                    else{
                        lat = latlon[1];
                        lon =   latlon[0];
                    }
                    bus.updatePos(lat, lon);
                    displayBus(bus);
                    setNewAlarm();
                    if (bus.userHasEnteredBus(user))
                        user.bus = bus.id;
                }
            }

            @Override
            public void onFailure(Call<double[]> call, Throwable t) {

            }
        });
    }


    public void createUser(){


        Call<String> call = dataService.createUser();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                user.id = Integer.parseInt(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void displayBus(Bus bus) {
        LatLng latLng = new LatLng(bus.lat, bus.lon);

        if (busMarker == null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
            busMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("You are here!"));
        }
        else {
            busMarker.getId();
            animateMarkerAndCamera(busMarker, latLng, false);
        }
    }

    public void animateMarkerAndCamera(final Marker marker, final LatLng toPosition,
                              final boolean hideMarker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = mMap.getProjection();
        Point startPoint = proj.toScreenLocation(marker.getPosition());
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 500;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lng)));

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
    }
}
