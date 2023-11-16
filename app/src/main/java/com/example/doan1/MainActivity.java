package com.example.doan1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan1.adapter.MangaAdapter;
import com.example.doan1.model.Manga.Manga;
import com.example.doan1.model.Relationship;
import com.example.doan1.model.Tag.Tag;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    Button button, button2;
    static final int REQUEST_CODE = 1;
    private Bus bus = new Bus(this);
    private int Page = 0;
    Gson gson = new Gson();
    private List<Tag> MangaTag = new ArrayList<>();
    private List<Manga> MangaByPage = new ArrayList<>();
    private MangaAdapter adapter;
    private ArrayAdapter<String> nameAdapter;
    private List<String> MangaName = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        create();
        setEvent();
    }
    private void GetMangaByPage(int page) {
        bus.getManga_list(page, null, null, null, null, null, null, null, null, new MyCallBack<List<Manga>>() {
            @Override
            public void onSuccess(List<Manga> result) {
                MangaByPage = result;
                GetMangaCover(MangaByPage);



                MangaName.clear();
                for (int i = 0;i<MangaByPage.size();i++)
                {
                    Manga manga = MangaByPage.get(i);
                    MangaName.add(manga.getName());
                }
                nameAdapter.notifyDataSetChanged();




                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
    private void GetMangaCover(List<Manga> mangaList) {
        for (int i=0; i<mangaList.size();i++)
        {
            Manga tam = mangaList.get(i);
            bus.getCover(tam.getCover(), new MyCallBack<String>() {
                @Override
                public void onSuccess(String result) {
                    tam.setUrlCover(result);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }
    }
    private void create()
    {
        bus.getTag(new MyCallBack<List<Tag>>() {
            @Override
            public void onSuccess(List<Tag> result) {
                MangaTag = result;

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
        GetMangaByPage(Page);


        listView = (ListView) findViewById(R.id.mangapage);
        button = (Button)  findViewById(R.id.button);
        button2 = (Button)  findViewById(R.id.button2);
        adapter = new MangaAdapter(MainActivity.this,R.layout.mainpage,MangaByPage);
        nameAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,MangaName);
        listView.setAdapter(nameAdapter);
    }
    private void setEvent()
    {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Page = Math.max(0,(Page-10));
                GetMangaByPage(Page);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Page = Math.min((Page+10),100);
                GetMangaByPage(Page);
            }
        });
    }
}