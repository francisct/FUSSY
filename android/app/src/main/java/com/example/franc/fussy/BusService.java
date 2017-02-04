package com.example.franc.fussy;

import com.example.franc.fussy.model.Bus;
import com.example.franc.fussy.model.User;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by franc on 2/4/2017.
 */

public interface BusService {

    @POST("/fussybus/updateBusPosition")
    Call updateBusPosition(@Path("user") User user);

    @POST("/fussybus/getBusPosition")
    Call<Bus> getBusPosition(@Path("bus") int bus);
}
