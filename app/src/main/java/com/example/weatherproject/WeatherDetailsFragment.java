package com.example.weatherproject;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class WeatherDetailsFragment extends Fragment {

    private TextView temperatureTextView; // Define the TextView variable

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_details, container, false);
        temperatureTextView = view.findViewById(R.id.temperatureTextView); // Find the TextView by ID

        // Retrieve the passed temperature value and display it
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("temperatureCelsius")) {
            double temperatureCelsius = bundle.getDouble("temperatureCelsius");

            // Display the temperature in the TextView
            temperatureTextView.setText("Temperature in Celsius: " + temperatureCelsius + "°C");
        } else {
            // Handle the case where temperature data is missing
            temperatureTextView.setText("Temperature data not available");
        }

        return view;
    }
}