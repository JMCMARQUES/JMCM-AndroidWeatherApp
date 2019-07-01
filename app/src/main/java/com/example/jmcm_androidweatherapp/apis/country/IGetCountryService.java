package com.example.jmcm_androidweatherapp.apis.country;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface IGetCountryService {

    @GET("countries")
    public Observable<CountryCityData> getResult();

}
