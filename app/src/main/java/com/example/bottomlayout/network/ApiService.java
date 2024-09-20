package com.example.bottomlayout.network;

import com.example.bottomlayout.model.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("forecast.json")
    Call<WeatherResponse> getWeather(
            @Query("key") String apiKey,
            @Query("q") String city,
            @Query("days") int days,
            @Query("aqi") String includeAqi,
            @Query("alerts") String includeAlerts
    );
}

