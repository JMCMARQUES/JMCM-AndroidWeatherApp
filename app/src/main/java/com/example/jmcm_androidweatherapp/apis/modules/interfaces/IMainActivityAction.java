package com.example.jmcm_androidweatherapp.apis.modules.interfaces;

import java.util.ArrayList;

public interface IMainActivityAction {


    void onCountryData(ArrayList<String> countries);
    void setText(String message);
    void errorMessage(String string);
}
