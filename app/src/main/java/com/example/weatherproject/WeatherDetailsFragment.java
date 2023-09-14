package com.example.weatherproject;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class WeatherDetailsFragment extends Fragment {

    private TextView temperatureTextView;
    private TextView cloudinessTextView;
    private TextView windTextView;
    private TextView lastUpdateTextView; // Add this TextView

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_details, container, false);
        temperatureTextView = view.findViewById(R.id.temperatureTextView);
        cloudinessTextView = view.findViewById(R.id.cloudsTextView);
        windTextView = view.findViewById(R.id.windTextView);
        lastUpdateTextView = view.findViewById(R.id.lastUpdateTextView); // Find the TextView by ID

        // Retrieve the passed weather data and display it
        Bundle bundle = getArguments();
        if (bundle != null) {
            double temperatureCelsius = bundle.getDouble("temperatureCelsius");
            String cloudiness = bundle.getString("cloudiness");
            String wind = bundle.getString("wind");
            String lastUpdate = bundle.getString("lastUpdate"); // Get last update information

            // Display the weather data in the TextViews
            temperatureTextView.setText("Temperature in Celsius: " + temperatureCelsius + "°C");
            cloudinessTextView.setText("Cloudiness: " + cloudiness);
            windTextView.setText("Wind: " + wind);
            lastUpdateTextView.setText("Last Update: " + lastUpdate); // Display last update
        } else {
            // Handle the case where weather data is missing
            temperatureTextView.setText("Temperature data not available");
            cloudinessTextView.setText("Cloudiness data not available");
            windTextView.setText("Wind data not available");
            lastUpdateTextView.setText("Last Update data not available");
        }

        return view;
    }
}
