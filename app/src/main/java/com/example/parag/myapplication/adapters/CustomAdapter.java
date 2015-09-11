package com.example.parag.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.parag.myapplication.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by parag on 9/8/15.
 */
public class CustomAdapter extends BaseAdapter {

    private final String TAG_IMG_URL = "imgURL";
    private final String BASE_URL = "http://192.168.10.104/";
    private ArrayList<JSONArray> data;
    private LayoutInflater inflater;
    private Context context;
    private LinearLayout mainLinearLayout;
    private ImageView selectedImageView;
    private DisplayImageOptions options;

    public CustomAdapter(ArrayList<JSONArray> arrayList, LayoutInflater inflater,
                         Context context, ImageView selectedImageView) {

        this.data = arrayList;
        this.inflater = inflater;
        this.context = context;
        this.selectedImageView = selectedImageView;

        // Init DisplayOptions.
        initDisplayOptions();
    }

    private void initDisplayOptions() {
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.layout_listviewitem, null);
            mainLinearLayout = (LinearLayout) convertView.findViewById(R.id.mainLinear);
        }

        final LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        for (int j = 0; j < data.get(position).length(); j++) {
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(100,100));
            imageView.setPadding(10, 10, 10, 10);
            imageView.setId(j);

            final int counter = j;

            try {
                ImageLoader.getInstance().displayImage(BASE_URL + data.get(position).getJSONObject(j).getString(TAG_IMG_URL),
                        imageView, options, null);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            ImageLoader.getInstance().displayImage(BASE_URL + data.get(position).getJSONObject(counter).getString(TAG_IMG_URL),
                                    selectedImageView, options, null);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                });
                mainLinearLayout.addView(imageView);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return convertView;
    }
}