package com.example.jmcm_androidweatherapp.apis.modules;

import android.content.res.Resources;

import com.example.jmcm_androidweatherapp.R;
import com.example.jmcm_androidweatherapp.apis.modules.interfaces.IMainActivityAction;
import com.example.jmcm_androidweatherapp.apis.modules.interfaces.IMainInteractor;
import com.example.jmcm_androidweatherapp.apis.modules.interfaces.IMainPresenter;
import com.orhanobut.logger.Logger;

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
            public void onError(Throwable e) {
                e.printStackTrace();
                mView.hideProgress();
                mView.errorMessage(e.getMessage());
            }

            @Override
            public void onSuccess(ArrayList<String> countries) {
                mView.onCountryData(countries);
                mView.hideProgress();
            }

            @Override
            public void onComplete() {
                mView.showToast(((MainActivity) mView).getString(R.string.dataRetrieved));
            }
        });
    }

    @Override
    public void loadWeatherData(String cityOrCountry) {

        mInteractor.getWeatherData(cityOrCountry, new IMainInteractor.IOnLoadngWeatherAPI() {
            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                mView.hideProgress();
                mView.errorMessage(e.getMessage());
            }

            @Override
            public void onSuccess(String weatherData) {
                mView.setText(weatherData);
                mView.hideProgress();
            }

            @Override
            public void onComplete() {
                mView.showToast(((MainActivity) mView).getString(R.string.dataRetrieved));
            }
        });
    }

}
