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
            JSONObject jsonRootObject = new JSONObject(response);
            Iterator<String> iterator = jsonRootObject.keys();
            while(iterator.hasNext()) {
                JSONArray jsonArray = jsonRootObject.getJSONArray(iterator.next());
                jsonArrays.add(jsonArray);
                for (int i=0;i<jsonArray.length();i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    // TODO: Remove the hardcoded strings.

                    String name = jsonObject.optString("name").toString();
                    String image="http://192.168.10.104/"+jsonObject.optString("imgURL").toString();

                    System.out.println("name"+name);
                    System.out.println("Imageurl"+image);
                }
            }
            return jsonArrays;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
