package com.example.jmcm_androidweatherapp.apis.modules;

import android.content.res.Resources;

import com.example.jmcm_androidweatherapp.R;
import com.example.jmcm_androidweatherapp.apis.country.CountryAPIRequest;
import com.example.jmcm_androidweatherapp.apis.country.CountryCityData;
import com.example.jmcm_androidweatherapp.apis.country.country;
import com.example.jmcm_androidweatherapp.apis.modules.interfaces.IMainInteractor;
import com.example.jmcm_androidweatherapp.apis.weatherData.WeatherAPIRequest;
import com.example.jmcm_androidweatherapp.apis.weatherData.WeatherData;
import com.orhanobut.logger.Logger;

import java.text.DecimalFormat;
import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainInteractor implements IMainInteractor {

    private WeatherAPIRequest weatherAPIRequest;
    private CountryAPIRequest countryAPIRequest;
    private ArrayList<String> countriesList = new ArrayList<>();
    private static final String KEY = "5e41726cfce9965c4d22634201bd1e83";


    public MainInteractor() {
        countryAPIRequest = new CountryAPIRequest();
        weatherAPIRequest = new WeatherAPIRequest(KEY);
    }

    @Override
    public void getCountryData(IOnLoadingCountryAPI listener) {

        countryAPIRequest.retrieveData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CountryCityData>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CountryCityData countryCityData) {
                        if (countryCityData != null) {

                            for (country e : countryCityData.result) {
                                String newCountry = e.name;
                                countriesList.add(newCountry);
                            }
                            Logger.d("OnNexr: " + countriesList);
                            listener.onSuccess(countriesList);
                        } else {
                            Throwable e = new Throwable(Resources.getSystem().getString(R.string.failToRetrieveData));
                            listener.onError(e);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        listener.onComplete();
                    }
                });
    }


    @Override
    public void getWeatherData(String cityOrCountry, IOnLoadngWeatherAPI listener) {


        weatherAPIRequest.retrieveData(cityOrCountry)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherData>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WeatherData weatherData) {
                        if (weatherData != null) {
                            listener.onSuccess(messageBuild(weatherData, cityOrCountry));
                        } else {
                            Throwable e = new Throwable(Resources.getSystem().getString(R.string.failToRetrieveData));
                            listener.onError(e);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        listener.onComplete();
                    }
                });

    }

    private String messageBuild(WeatherData weatherData, String cityOrCountry) {
        if (weatherData != null) {

            double tempInDegreeC = weatherData.main.temp - 273.15;
            double minTempInDegreeC = weatherData.main.temp_min - 273.15;
            double maxTempInDegreeC = weatherData.main.temp_max - 273.15;

            DecimalFormat temperature = new DecimalFormat("#.00");

            String message = weatherData.sys.country + "\n" + cityOrCountry + ": " +
                    "\n - Temperature: " + temperature.format(tempInDegreeC) +
                    "ºC\n - Humidity: " + weatherData.main.humidity +
                    "%\n - Minimum Temperature: " + temperature.format(minTempInDegreeC) +
                    "ºC\n - Maximum Temperature: " + temperature.format(maxTempInDegreeC) +
                    "kPa\n - Pressure: " + weatherData.main.pressure + "hPa";

            Logger.d(message);

            return message;
        }
        return null;
    }

}
