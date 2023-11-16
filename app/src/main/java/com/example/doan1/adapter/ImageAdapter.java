package com.example.doan1.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.doan1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends ArrayAdapter<String> {

    Activity context;
    int resource;
    ArrayList<String> imageList;
    String temp;

    public ImageAdapter(Activity context,
                        int resource,
                        ArrayList<String> imageList,
                        String temp) {
        super(context, resource,imageList);
        this.context = context;
        this.resource = resource;
        this.imageList = imageList;
        this.temp = temp;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View customview = inflater.inflate(this.resource,null);

        ImageView imageView = customview.findViewById(R.id.imageView3);
        String url = "https://uploads.mangadex.org/data/" + temp +"/" + imageList.get(position);
        Picasso.get().load(url).into(imageView);

        return customview;
    }
}