package com.example.parag.myapplication.Interfaces;

import java.util.ArrayList;

/**
 * Created by parag on 9/7/15.
 */
public interface ResponseStatusListener {

    public void onRequestSuccessful(ArrayList arrayList);

    public void onRequestFailure();

}
