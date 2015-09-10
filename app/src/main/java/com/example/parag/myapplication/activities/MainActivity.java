package com.example.parag.myapplication.activities;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.parag.myapplication.Interfaces.ResponseStatusListener;
import com.example.parag.myapplication.R;
import com.example.parag.myapplication.adapters.CustomAdapter;
import com.example.parag.myapplication.network.jsonparsers.JsonParser;
import com.example.parag.myapplication.utils.Utils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity {

    private final String BASE_URL = "http://192.168.10.104/imageData.php";
    private final String TAG = "MainActivity";
    private ListView listview ;
    private LayoutInflater inflater;
    private ImageView selectedImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectedImageView = (ImageView) findViewById(R.id.image);

        listview = (ListView) findViewById(R.id.listview);
        inflater= (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initImageLoader(MainActivity.this);

        // TODO: Add condition to check internet connectivity.
        if(true) {
            new NetworkConnector().execute(BASE_URL);
        } else {
            // TODO: Add the "Network not available." in the strings.xml file.
        }
    }

    public class NetworkConnector extends AsyncTask<String, Void, String> {

        private String response = null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            return sendGETRequest(params[0]);
        }

        private String sendGETRequest(String url) {

            DefaultHttpClient httpClient=new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse =null;
            HttpGet httpGet = new HttpGet(url);

            try {
                httpResponse = httpClient.execute(httpGet);
                httpEntity = httpResponse.getEntity();
                response = EntityUtils.toString(httpEntity);
                return  response;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ArrayList<JSONArray> jsonArrays = new JsonParser().parseJson(response);
            if(jsonArrays != null) {
                listview.setAdapter(new CustomAdapter(jsonArrays, inflater, MainActivity.this , selectedImageView));
            }
        }
    }

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }
}
