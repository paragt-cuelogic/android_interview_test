package com.example.parag.myapplication.network.jsonparsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by parag on 9/7/15.
 */
public class JsonParser {

    private ArrayList<JSONArray> jsonArrays = new ArrayList<JSONArray>();

    public ArrayList parseJson( String response) {
        try {
            if (response != null) {
                JSONObject jsonRootObject = new JSONObject(response);
                Iterator<String> iterator = jsonRootObject.keys();
                while(iterator.hasNext()) {
                    JSONArray jsonArray = jsonRootObject.getJSONArray(iterator.next());
                    jsonArrays.add(jsonArray);
                }
            }
            return jsonArrays;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
