package com.example.parag.myapplication.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.parag.myapplication.R;
import com.example.parag.myapplication.network.NetworkConnector;

public class MainActivity extends Activity {

    private final String BASE_URL = "http://192.168.10.104/imageData.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: Add condition to check internet connectivity.
        if(true) {
            new NetworkConnector(MainActivity.this).execute(BASE_URL);
        } else {
            // TODO: Add the "Network not available." in the strings.xml file.
            Toast.makeText(MainActivity.this, "Network not available.", Toast.LENGTH_SHORT).show();
        }
    }
}
