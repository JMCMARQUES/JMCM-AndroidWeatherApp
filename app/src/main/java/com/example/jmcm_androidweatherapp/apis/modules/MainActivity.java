package com.example.jmcm_androidweatherapp.apis.modules;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jmcm_androidweatherapp.R;
import com.example.jmcm_androidweatherapp.apis.modules.interfaces.IMainActivityAction;
import com.example.jmcm_androidweatherapp.apis.modules.interfaces.IMainPresenter;
import com.example.jmcm_androidweatherapp.apis.weatherData.FailToGetDataFromAPIDialogFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity implements IMainActivityAction {

    private IMainPresenter mainPresent;
    private Dialog mProgressDialog;

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
        showProgress(getResources().getString(R.string.loading_list));
        mainPresent = new MainPresenter(this);
        mainPresent.loadCountrySpinner();
    }



    @OnClick(R.id.buttonCountry)
    public void getDataCountry() {
        showProgress(getResources().getString(R.string.loading_weather_data));
        String country = spinnerCountry.getSelectedItem().toString();
        mainPresent.loadWeatherData(country);
    }


    @OnClick(R.id.buttonCity)
    public void getDataCity() {
        showProgress(getResources().getString(R.string.loading_weather_data));
        String city = spinner.getSelectedItem().toString();
        mainPresent.loadWeatherData(city);
    }

    @Override
    public void setText(String message) {
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
    public void errorMessage(String string) {

        if (string.equals(getResources().getString(R.string.httpNotFound))) {
            FailToGetDataFromAPIDialogFragment dialogFragment = new FailToGetDataFromAPIDialogFragment();
            dialogFragment.show(getSupportFragmentManager(), "");
            textView.setText("-");
        } else {
            Toast.makeText(this, string, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void showProgress(String string) {
        mProgressDialog = buildLoadingDialog(string);
        mProgressDialog.show();

    }

    @Override
    public void hideProgress() {
        mProgressDialog.dismiss();
    }

    private Dialog buildLoadingDialog(String s) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        dialog.setContentView(R.layout.loading);
        dialog.setCancelable(false);

        Window window = dialog.getWindow();
        if (window != null)
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView loadingTextView = dialog.findViewById(R.id.loading_textView);
        loadingTextView.setText(s);

        return dialog;
    }


}
