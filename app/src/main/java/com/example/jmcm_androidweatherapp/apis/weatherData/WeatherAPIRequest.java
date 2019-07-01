package com.example.jmcm_androidweatherapp.apis.weatherData;

import com.example.jmcm_androidweatherapp.apis.weatherData.IGetDataService;
import com.example.jmcm_androidweatherapp.apis.weatherData.WeatherData;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherAPIRequest {

    private IGetDataService service;
    private String appId;

    /**
     * creating a contructer with retrofit and service inicialization to use always the same service as manny times as needed
     *
     * @param appId
     */
    public WeatherAPIRequest(String appId) {
        this.appId = appId;

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();

        service = retrofit.create(IGetDataService.class);
    }


    public Observable<WeatherData> retrieveData(String city) {

        return service.getCity(city, appId);
    }
}
