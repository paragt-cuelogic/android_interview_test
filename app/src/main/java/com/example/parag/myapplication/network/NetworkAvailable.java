package com.example.parag.myapplication.network;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Parag on 9/11/15.
 */
public class NetworkAvailable {

    private Context context;

    public NetworkAvailable(Context context) {
        this.context = context;

    }

    public boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);

        if (connectivityManager.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connectivityManager.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connectivityManager.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            return true;
        } else {
            return  false;
        }
    }
}
