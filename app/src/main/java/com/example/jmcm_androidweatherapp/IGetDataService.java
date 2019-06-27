package com.example.jmcm_androidweatherapp;

import com.example.jmcm_androidweatherapp.JsonClasses.WeatherData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IGetDataService {

    @GET("weather")
    public Observable<WeatherData> getCity(
            @Query("q") String city,
            @Query("APPID") String appid);

}
