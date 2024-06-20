package com.example.weatherapplication;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface  {
    @GET("weather")
    Call<WeatherJson> getWeatherData(@Query("q")String city,@Query("appid")String appid,@Query("units")String unit) ;
}
