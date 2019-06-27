package com.example.jmcm_androidweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jmcm_androidweatherapp.JsonClasses.WeatherData;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    private WeatherAPIRequest weatherAPIRequest;
    private String key = "5e41726cfce9965c4d22634201bd1e83";

    @BindView(R.id.spinner)
    public Spinner spinner;

    @BindView(R.id.textView)
    public TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        weatherAPIRequest = new WeatherAPIRequest(key);
        spinnerLoad();
    }


    public void spinnerLoad() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cityList, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
    }


    @OnClick(R.id.button)
    public void getData() {
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

                            textView.setText(message);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        System.out.println(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
