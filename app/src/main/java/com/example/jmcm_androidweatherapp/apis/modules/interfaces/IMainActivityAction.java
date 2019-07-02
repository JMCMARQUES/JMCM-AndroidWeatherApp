package com.example.jmcm_androidweatherapp.apis.modules.interfaces;

import java.util.ArrayList;

public interface IMainActivityAction {


    public void onCountryData(ArrayList<String> countries);
    public void setText(String message);
    public void errorMessage(String string);
}
