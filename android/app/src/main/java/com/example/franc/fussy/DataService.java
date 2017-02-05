package com.example.franc.fussy;

import com.example.franc.fussy.model.Bus;
import com.example.franc.fussy.model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by franc on 2/4/2017.
 */

public interface DataService {

    @GET("/fussybus/updateBusPosition")
    Call<ResponseBody> updateBusPosition(@Query("user") int user,
                                         @Query("bus") int bus,
                                         @Query("lat") double lat,
                                         @Query("lon") double lon);

    @GET("/fussybus/getBusPosition")
    Call<Bus> getBusPosition(@Query("bus") int bus);
}
