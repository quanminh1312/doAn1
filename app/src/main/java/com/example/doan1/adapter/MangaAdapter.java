package com.example.doan1.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.doan1.R;
import com.example.doan1.model.Manga.Manga;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MangaAdapter extends ArrayAdapter<Manga> {
    Activity context;
    int resource;
    List<Manga> data;
    public MangaAdapter(Activity context, int resource, List<Manga> data) {
        super(context, resource,data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }
    @Override
    public View getView(int position, View contextView, ViewGroup parent)
    {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View customview = inflater.inflate(this.resource,null);
        Manga manga = data.get(position);
        ImageView imgHinh = (ImageView) customview.findViewById(R.id.imageView);
        TextView ten = (TextView) customview.findViewById(R.id.textView);
        String url = "https://uploads.mangadex.org/covers/" + manga.getId() +"/" + manga.getUrlCover() +".512.jpg";
        Picasso.get().load(url).into(imgHinh);
        ten.setText(manga.getName());
        return customview;
    }
}