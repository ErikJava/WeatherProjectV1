package com.example.weatherproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button beginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the "Begin" button
        beginButton = findViewById(R.id.beginButton);

        // Set a click listener for the "Begin" button
        beginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace the current fragment with WeatherSearchFragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new WeatherSearchFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        // Check if there is no saved instance state (i.e., first launch)
        if (savedInstanceState == null) {
            // Initial fragment transaction (WelcomeFragment)
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new WelcomeFragment())
                    .commit();
        }
    }
}
