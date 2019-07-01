package com.example.jmcm_androidweatherapp.apis.modules;

import com.example.jmcm_androidweatherapp.apis.modules.interfaces.IMainActivityAction;
import com.example.jmcm_androidweatherapp.apis.modules.interfaces.IMainInteractor;
import com.example.jmcm_androidweatherapp.apis.modules.interfaces.IMainPresenter;

import java.util.ArrayList;

public class MainPresenter implements IMainPresenter {

    private IMainActivityAction mView;
    private IMainInteractor mInteractor;

    public MainPresenter(IMainActivityAction mainActivityAction) {
        this.mView = mainActivityAction;
        mInteractor = new MainInteractor();
    }


    @Override
    public void loadCountrySpinner() {

        mInteractor.getCountryData(new IMainInteractor.IOnLoadingCountryAPI() {
            @Override
            public void onError(String e) {
            }

            @Override
            public void onSuccess(ArrayList<String> countries) {
                mView.onCountryData(countries);
            }
        });
    }

    @Override
    public void loadWeatherData(String cityOrCountry) {

        mInteractor.getWeatherData(cityOrCountry, new IMainInteractor.IOnLoadngWeatherAPI() {
            @Override
            public void onError(String e) {
            }

            @Override
            public void onSuccess(String weatherData) {
                mView.setText(weatherData);
            }
        });
    }

}
