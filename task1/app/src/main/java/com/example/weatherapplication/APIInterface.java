package com.example.weatherapplication;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface  {
    public String apikey="6dd144367162ab13fb56cac4972ea682";
    public interface WeatherForecastInterface{
        @GET("forecast")
        Call<WeatherForecast> getFutureWeatherData(@Query("q")String city,@Query("appid")String appid,@Query("units")String unit) ;
    }
    public interface CurrentWeatherInterface{
        @GET("weather")
        Call<CurrentWeather> getWeatherData(@Query("q")String city,@Query("appid")String appid,@Query("units")String unit) ;

        @GET("weather")
        Call<CurrentWeather> getWeatherDataLatLon(@Query("lat")String lat,@Query("lon")String lon,@Query("appid")String appid,@Query("units")String unit) ;
    }

}
