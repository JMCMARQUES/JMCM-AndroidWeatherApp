package com.example.jmcm_androidweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    private WeatherAPIRequest weatherAPIRequest;

    @BindView(R.id.spinner)
    public Spinner spinner;

    @BindView(R.id.textView)
    public TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        spinnerLoad();
        weatherAPIRequest = new WeatherAPIRequest();

    }


    public void spinnerLoad() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cityList, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
    }


    @OnClick(R.id.button)
    public void getData() {

        String city = spinner.getSelectedItem().toString();

        weatherAPIRequest.myGetRequest(city);

        textView.setText(city);

    }


}
