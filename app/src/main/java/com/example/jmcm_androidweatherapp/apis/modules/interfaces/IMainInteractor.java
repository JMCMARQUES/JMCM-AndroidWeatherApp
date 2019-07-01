package com.example.jmcm_androidweatherapp.apis.modules.interfaces;

import java.util.ArrayList;

public interface IMainInteractor {

    public void getCountryData(IOnLoadingCountryAPI listener);

    public void getWeatherData(String city, IOnLoadngWeatherAPI weatherAPI);

    interface IOnLoadingCountryAPI {
        void onError(String e);
        void onSuccess(ArrayList<String> countries);
    }

    interface IOnLoadngWeatherAPI {
        void onError(String e);
        void onSuccess(String weatherData);
    }

}