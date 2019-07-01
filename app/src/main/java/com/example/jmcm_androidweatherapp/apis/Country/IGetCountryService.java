package com.example.jmcm_androidweatherapp.apis.Country;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IGetCountryService {

    @GET("countries")
    public Observable<CountryCityData> getResult();

}
