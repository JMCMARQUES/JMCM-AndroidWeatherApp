package com.example.jmcm_androidweatherapp.apis.modules.interfaces;

import java.util.ArrayList;

public interface IMainInteractor {

    public void getCountryData(IOnLoadingCountryAPI listener);

    public void getWeatherData(String city, IOnLoadngWeatherAPI weatherAPI);

    interface IOnLoadingCountryAPI {
        void onError(Throwable e);
        void onSuccess(ArrayList<String> countries);

        void onComplete();
    }

    interface IOnLoadngWeatherAPI {
        void onError(Throwable e);
        void onSuccess(String weatherData);

        void onComplete();
    }

}
