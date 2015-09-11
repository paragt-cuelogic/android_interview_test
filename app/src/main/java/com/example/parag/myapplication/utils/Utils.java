package com.example.parag.myapplication.utils;

import android.util.Log;

/**
 * Created by parag on 9/8/15.
 */
public class Utils {

    public void printSysout(String key, String value) {
        System.out.println(key+" "+value);
    }

    public void printLogs(String key, String value) {
        Log.d(key,value);
    }
}
