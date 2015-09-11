package com.example.parag.myapplication.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.parag.myapplication.Interfaces.ResponseStatusListener;
import com.example.parag.myapplication.R;
import com.example.parag.myapplication.adapters.CustomAdapter;
import com.example.parag.myapplication.network.NetworkAvailable;
import com.example.parag.myapplication.network.NetworkConnector;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.json.JSONArray;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private final String BASE_URL = "http://192.168.10.104/imageData.php";
    private final String TAG = "MainActivity";
    private ListView listview ;
    private LayoutInflater inflater;
    private ImageView selectedImageView;
    private ProgressDialog progressDialog;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUIViews();

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("loading");
        inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        initImageLoader(MainActivity.this);



        // Check for network connections
        if (  new NetworkAvailable(MainActivity.this).isNetworkAvailable())  {

            new NetworkConnector(new ResponseStatusListener() {
                @Override
                public void onRequestSuccessful(ArrayList<JSONArray> arrayList) {
                    if(arrayList != null) {
                        listview.setAdapter(new CustomAdapter(arrayList, inflater, MainActivity.this, selectedImageView));
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onRequestFailure() {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, R.string.unable_to_process_request, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onRequestStarted() {
                    progressDialog.show();
                }
            }).execute(BASE_URL);
        } else {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_LONG).show();
        }

    }

    private void initUIViews() {
        selectedImageView = (ImageView) findViewById(R.id.image);
        listview = (ListView) findViewById(R.id.listview);
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
