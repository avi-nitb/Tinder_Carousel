package com.tindercarousel.network;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TinderApi {

    @GET("?randomapi")
    Call<JsonObject> getData();
}
