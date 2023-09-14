package com.example.weatherproject;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WeatherSearchFragment extends Fragment {

    private EditText cityEditText;
    private Button searchButton;
    private RequestQueue requestQueue;

    public WeatherSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_search, container, false);
        cityEditText = view.findViewById(R.id.cityEditText);
        searchButton = view.findViewById(R.id.searchButton);
        requestQueue = Volley.newRequestQueue(requireContext());

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle search button click
                String city = cityEditText.getText().toString();

                // Call the method to fetch weather data
                fetchWeatherData(city);
            }
        });

        return view;
    }

    private void fetchWeatherData(String cityName) {
        String apiKey = "9a2f9ac50129b5dad7ebb7aa0fb4d4ce"; // Replace with your Geocoding API key
        String apiUrl = "https://api.openweathermap.org/geo/1.0/direct?q=" + cityName + "&limit=5&appid=" + apiKey;

        // Request to obtain latitude and longitude coordinates for the city
        JsonArrayRequest geocodingJsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                apiUrl,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            if (response != null && response.length() > 0) {
                                // Assuming you want to use the first result
                                JSONObject locationObject = response.getJSONObject(0);
                                double latitude = locationObject.getDouble("lat");
                                double longitude = locationObject.getDouble("lon");

                                // Now that you have the coordinates, call fetchWeatherDataWithCoordinates
                                fetchWeatherDataWithCoordinates(latitude, longitude);
                            } else {
                                // Handle the case where no results were found for the city
                                Toast.makeText(requireContext(), "No results found for the city", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Handle JSON parsing error
                            Toast.makeText(requireContext(), "Error parsing response", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle geocoding API call failure
                        Toast.makeText(requireContext(), "Error fetching city coordinates: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(geocodingJsonArrayRequest);
    }


    private void fetchWeatherDataWithCoordinates(double latitude, double longitude) {
        String apiKey = "9a2f9ac50129b5dad7ebb7aa0fb4d4ce"; // Replace with your Weather API key
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + apiKey;

        // Request to fetch weather data using coordinates
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                apiUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Parse the JSON response and extract weather data
                            JSONObject mainObject = response.getJSONObject("main");
                            double temperatureKelvin = mainObject.getDouble("temp");
                            double temperatureCelsius = temperatureKelvin - 273.15;

                            // Extract the "last update" information
                            long lastUpdateTimestamp = response.getLong("dt");
                            String lastUpdate = formatLastUpdate(lastUpdateTimestamp);

                            // Extract cloudiness and wind information
                            String cloudiness = response.getJSONArray("weather").getJSONObject(0).getString("description");
                            String wind = response.getJSONObject("wind").getString("speed");

                            // Pass the weather data to the WeatherDetailsFragment
                            WeatherDetailsFragment weatherDetailsFragment = new WeatherDetailsFragment();
                            Bundle bundle = new Bundle();
                            bundle.putDouble("temperatureCelsius", temperatureCelsius);
                            bundle.putString("lastUpdate", lastUpdate); // Pass last update information
                            bundle.putString("cloudiness", cloudiness);
                            bundle.putString("wind", wind);
                            weatherDetailsFragment.setArguments(bundle);

                            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragmentContainer, weatherDetailsFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle API call failure
                        Toast.makeText(requireContext(), "Error fetching weather data", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    // Helper method to format the "last update" timestamp into a readable date and time
    private String formatLastUpdate(long timestamp) {
        Date date = new Date(timestamp * 1000); // Convert timestamp to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(date);
    }
}



