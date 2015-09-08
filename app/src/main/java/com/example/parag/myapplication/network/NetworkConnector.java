package com.example.parag.myapplication.network;

import android.content.Context;
import android.os.AsyncTask;

import com.example.parag.myapplication.network.jsonparsers.JsonParser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by parag on 9/7/15.
 */
public class NetworkConnector extends AsyncTask<String, Void, String> {

    private Context context;
    private String response = null;

    public NetworkConnector(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
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
        new JsonParser().parseJson(response);

    }
}
