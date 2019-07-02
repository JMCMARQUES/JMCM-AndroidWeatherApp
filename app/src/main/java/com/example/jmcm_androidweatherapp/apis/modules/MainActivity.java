package com.example.jmcm_androidweatherapp.apis.modules;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jmcm_androidweatherapp.R;
import com.example.jmcm_androidweatherapp.apis.modules.interfaces.IMainActivityAction;
import com.example.jmcm_androidweatherapp.apis.modules.interfaces.IMainPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class MainActivity extends AppCompatActivity implements IMainActivityAction {

    private IMainPresenter mainPresent;

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

        mainPresent = new MainPresenter(this);
        mainPresent.loadCountrySpinner();
    }


    @OnClick(R.id.buttonCountry)
    public void getDataCountry() {
        String country = spinnerCountry.getSelectedItem().toString();
        mainPresent.loadWeatherData(country);
    }


    @OnClick(R.id.buttonCity)
    public void getDataCity() {
        String city = spinner.getSelectedItem().toString();
        mainPresent.loadWeatherData(city);
    }

    @Override
    public void setText(String message){
        textView.setText(message);
    }


    @Override
    public void onCountryData(ArrayList<String> countries) {
        //first spinner load
        ArrayAdapter<String> adapterCountry = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                countries);
        spinnerCountry.setAdapter(adapterCountry);
        System.out.println(countries);

        //second spinner load
        ArrayAdapter<CharSequence> adapterCity = ArrayAdapter.createFromResource(this,
                R.array.cityList,
                android.R.layout.simple_spinner_item);
        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapterCity);
    }

    @Override
    public void errorMessage(String string){
        Toast.makeText(this,string,Toast.LENGTH_LONG).show();
    }

}
