package com.example.jmcm_androidweatherapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherAPIRequest {


    public String myGetRequest(String city) {

        String readLine = null;
        String toResponse = null;
        String[] toResponseArray = new String[]{};

        try {
            URL getRequestURL = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID=5e41726cfce9965c4d22634201bd1e83");
            HttpURLConnection connection = (HttpURLConnection) getRequestURL.openConnection();

            connection.setRequestMethod("GET");

            //connection.setReadTimeout(3000);
            System.out.println("printing something: " + connection);
            //connection.connect();
            //System.out.println("printing something: ");


            int responseCode = connection.getResponseCode();
            System.out.println("entered if statement"  + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuffer response = new StringBuffer();


                while ((readLine = in.readLine()) != null) {
                    response.append(readLine);
                }
                in.close();
                connection.disconnect();

                toResponse = response.toString();
                toResponseArray = toResponse.split(",");

                System.out.println("JSON String Result" + toResponse);

            } else {
                System.out.println("GET didn't work");
            }


        } catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e.getMessage());
            e.printStackTrace();

        } catch (IOException e) {
            System.out.println("failed to make connection: " + e.getMessage());
            e.printStackTrace();
            e.getMessage();
            e.getCause();
        }


        return toResponseArray[3];

    }

}
