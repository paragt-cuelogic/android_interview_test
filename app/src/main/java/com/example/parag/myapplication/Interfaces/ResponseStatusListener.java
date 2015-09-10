package com.example.parag.myapplication.Interfaces;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by parag on 9/7/15.
 */
public interface ResponseStatusListener {

    public void onRequestSuccessful(ArrayList<JSONArray> arrayList);

    public void onRequestFailure();

}
