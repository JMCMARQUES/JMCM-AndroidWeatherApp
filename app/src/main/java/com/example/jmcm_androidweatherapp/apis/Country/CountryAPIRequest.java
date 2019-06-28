package com.example.jmcm_androidweatherapp.apis.Country;


import java.util.ArrayList;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CountryAPIRequest {

    private IGetCountryService service;
    private ArrayList country;

    public CountryAPIRequest() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.printful.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();
        service = retrofit.create(IGetCountryService.class);
    }

    public Observable<CountryCityData> retrieveData() {
        return service.getResult();
    }

}
