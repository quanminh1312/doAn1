package com.example.doan1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan1.adapter.MangaAdapter;
import com.example.doan1.model.Manga.Manga;
import com.example.doan1.model.StaticManga;
import com.example.doan1.model.Tag.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User_Page extends AppCompatActivity {
    TextView textView;
    ListView listView;
    Bus bus = new Bus(this);
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    MangaAdapter adapter;
    List<Manga> MangaWatched = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        getMangaByUserName();
        create();
        setEvent();
    }
    private void getMangaByUserName()
    {
        String username = StaticManga.getUsername();
        List<String> manga =  databaseHelper.getMangaNamesByUsername(username);
        GetMangaByUser(manga);
    }
    private void create()
    {
        textView = (TextView) findViewById(R.id.returnlink);
        listView = (ListView) findViewById(R.id.list_view);
    }
    private void setEvent()
    {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                setResult(MainActivity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                StaticManga.setManga(MangaWatched.get(i));
                Intent intent = new Intent(User_Page.this,ChapterList.class);
                startActivity(intent);
            }
        });
    }

    private void GetMangaByUser(List<String> manga)
    {
        for (int i=0; i< manga.size();i++)
        {
            getMangaById(manga.get(i));
        }
    }
    private void getMangaById(String mangaId)
    {
        bus.getMangaById(mangaId, new MyCallBack<Manga>() {
            @Override
            public void onSuccess(Manga result) {
                GetMangaCover(result);
            }
            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
    private void GetMangaCover(Manga manga)
    {
        bus.getCover(manga.getCover(), new MyCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                manga.setUrlCover(result);
                MangaWatched.add(manga);
                updateListView();
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
        }
    private void updateListView()
    {
        adapter = new MangaAdapter(User_Page.this,R.layout.mainpage,MangaWatched);
        listView.setAdapter(adapter);
    }
}