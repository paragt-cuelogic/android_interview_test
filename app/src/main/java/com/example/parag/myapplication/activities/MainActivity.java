package com.example.parag.myapplication.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.parag.myapplication.Interfaces.ResponseStatusListener;
import com.example.parag.myapplication.R;
import com.example.parag.myapplication.network.NetworkConnector;
import com.example.parag.myapplication.utils.Utils;

import org.json.JSONArray;

import java.util.ArrayList;

public class MainActivity extends Activity implements ResponseStatusListener{

    private final String BASE_URL = "http://192.168.10.104/imageData.php";
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: Add condition to check internet connectivity.
        if(true) {
            new NetworkConnector().execute(BASE_URL);
        } else {
            // TODO: Add the "Network not available." in the strings.xml file.
            Toast.makeText(MainActivity.this, "Network not available.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestSuccessful(ArrayList arrayList) {
        ArrayList<JSONArray> arrays = arrayList;
        Utils.printSysout(TAG, "arrays count >> "+arrays.size());
    }

    @Override
    public void onRequestFailure() {
        Utils.printSysout(TAG, "onRequestFailure");
    }
}
