package com.example.parag.myapplication.network;

import android.content.Context;
import android.os.AsyncTask;

import com.example.parag.myapplication.Interfaces.ResponseStatusListener;
import com.example.parag.myapplication.network.jsonparsers.JsonParser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by parag on 9/7/15public NetworkConnector() {
    }.
 */
public class NetworkConnector extends AsyncTask<String, Void, String> {

    private ResponseStatusListener listener ;
    private Context context;
    private String response = null;

    public NetworkConnector(ResponseStatusListener listener) {
         this.listener = listener ;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onRequestStarted();
    }

    @Override
    protected String doInBackground(String... params) {
        sendGETRequest(params[0]);
        return null;
    }

    private String sendGETRequest(String url) {

        DefaultHttpClient httpClient=new DefaultHttpClient();
        HttpEntity httpEntity = null;
        HttpResponse httpResponse =null;
        HttpGet httpGet = new HttpGet(url);

        try {
            httpResponse = httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        httpEntity = httpResponse.getEntity();
        try {
            response = EntityUtils.toString(httpEntity);
            System.out.println("response >> "+response);
            return  response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onPostExecute(String aVoid) {
        super.onPostExecute(aVoid);
        ArrayList<JSONArray> jsonArrays =  new JsonParser().parseJson(response);
        listener.onRequestSuccessful(jsonArrays);
    }
}
