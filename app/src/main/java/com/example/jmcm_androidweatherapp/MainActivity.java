package com.example.jmcm_androidweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jmcm_androidweatherapp.apis.Country.CountryAPIRequest;
import com.example.jmcm_androidweatherapp.apis.Country.CountryCityData;
import com.example.jmcm_androidweatherapp.apis.Country.country;
import com.example.jmcm_androidweatherapp.apis.weatherData.WeatherAPIRequest;
import com.example.jmcm_androidweatherapp.apis.weatherData.WeatherData;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    private WeatherAPIRequest weatherAPIRequest;
    private CountryAPIRequest countryAPIRequest;
    private static final String ON_SUBSCRIBE_MESSAGE="New thread created; this created thread is being observed on the main thread; a subscription was made, by the new thread, to a new observer<WeatherData>";
    private String key = "5e41726cfce9965c4d22634201bd1e83";

    @BindView(R.id.spinner)
    public Spinner spinner;

    @BindView(R.id.spinnerCountry)
    public Spinner spinnerCountry;

    @BindView(R.id.textView)
    public TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        weatherAPIRequest = new WeatherAPIRequest(key);
        countryAPIRequest = new CountryAPIRequest();
        spinnerLoad();
        getCountryData();
    }


    public void spinnerCountryLoad(ArrayList<String> arrayList) {
        ArrayAdapter<String> adapterCountry = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                arrayList);
        spinnerCountry.setAdapter(adapterCountry);
    }

    public void spinnerLoad() {
        ArrayAdapter<CharSequence> adapterCity = ArrayAdapter.createFromResource(this,
                R.array.cityList,
                android.R.layout.simple_spinner_item);
        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapterCity);
    }


    public void getCountryData() {
        countryAPIRequest.retrieveData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CountryCityData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println(ON_SUBSCRIBE_MESSAGE);
                    }

                    @Override
                    public void onNext(CountryCityData countryCityData) {
                        if (countryCityData != null) {

                            ArrayList<String> countries = new ArrayList<>();

                            for (country e : countryCityData.result) {
                                String newCountry = e.getName();
                                countries.add(newCountry);
                            }
                            spinnerCountryLoad(countries);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("ERROR!");
                        e.printStackTrace();
                        e.getMessage();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @OnClick(R.id.buttonCountry)
    public void getDataCountry() {
        String city = spinnerCountry.getSelectedItem().toString();

        weatherAPIRequest.retrieveData(city)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println(ON_SUBSCRIBE_MESSAGE);
                    }

                    @Override
                    public void onNext(WeatherData weatherData) {

                        messageBuild(weatherData, city);

                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("GET DATA COUNTRY ERROR!");
                        e.printStackTrace();
                        e.getMessage();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


    @OnClick(R.id.buttonCity)
    public void getDataCity() {
        String city = spinner.getSelectedItem().toString();

        weatherAPIRequest.retrieveData(city)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("New thread created; this created thread is being observed on the main thread; a subscription was made, by the new thread, to a new observer<WeatherData>");
                    }

                    @Override
                    public void onNext(WeatherData weatherData) {
                        messageBuild(weatherData, city);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("GET DATA CITY ERROR!");
                        e.printStackTrace();
                        System.out.println(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


    private void messageBuild(WeatherData weatherData, String city) {
        if (weatherData != null) {

            double tempInDegreeC = weatherData.main.temp - 273.15;
            double minTempInDegreeC = weatherData.main.temp_min - 273.15;
            double maxTempInDegreeC = weatherData.main.temp_max - 273.15;

            DecimalFormat temperature = new DecimalFormat("#.00");

            String message = weatherData.sys.country + "\n" + city + ": " +
                    "\n - Temperature: " + temperature.format(tempInDegreeC) +
                    "ºC\n - Humidity: " + weatherData.main.humidity +
                    "%\n - Minimum Temperature: " + temperature.format(minTempInDegreeC) +
                    "ºC\n - Maximum Temperature: " + temperature.format(maxTempInDegreeC) +
                    "kPa\n - Pressure: " + weatherData.main.pressure + "hPa";

            System.out.println(message);
            textView.setText(message);
        } else {
            textView.setText("No data available for the given location");
        }
    }
}
