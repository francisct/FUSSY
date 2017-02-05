package com.example.franc.fussy;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.example.franc.fussy.model.Bus;
import com.example.franc.fussy.model.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback  {
    GoogleMap mMap;

    User user;
    DataService dataService;
    int busNo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = new User();
        user.bus = 2;
        setContentView(R.layout.activity_main);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fussybus.azurewebsites.net/api/values/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        dataService = retrofit.create(DataService.class);

        createUser();

    }



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

    public void sendNewLocation(Location location){
        if (user.id != 0) {
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
        busNo = Integer.parseInt(text.getText().toString());

        Call<double[]> call= dataService.getBusPosition(busNo);
        call.enqueue(new Callback<double[]>() {
            @Override
            public void onResponse(Call<double[]> call, Response<double[]> response) {
                double[] latlon = response.body();
                Bus bus = new Bus(latlon[0], latlon[1]);
               displayBus(bus);
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
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
        mMap.addMarker(new MarkerOptions().position(latLng).title("You are here!"));
    }
}
