package com.example.doan1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan1.adapter.MangaAdapter;
import com.example.doan1.model.Manga.Manga;
import com.example.doan1.model.StaticManga;
import com.example.doan1.model.Tag.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rank_Page extends AppCompatActivity {
    TextView textView;
    ListView listView;
    Bus bus = new Bus(this);
    MangaAdapter adapter;
    Map<String, Object> order = new HashMap<>();
    List<Manga> MangaByRank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_page);
        order.put("order[rating]", "desc");
        order.put("order[followedCount]","desc");
        create();
        setEvent();
    }
    private void create()
    {
        textView = (TextView) findViewById(R.id.return_link2);
        listView = (ListView) findViewById(R.id.list);
        GetMangaByRank(order);
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
                StaticManga.setManga(MangaByRank.get(i));
                Intent intent = new Intent(Rank_Page.this,ChapterList.class);
                startActivity(intent);
            }
        });
    }
    private void updateListView()
    {
        adapter = new MangaAdapter(Rank_Page.this,R.layout.mainpage,MangaByRank);
        listView.setAdapter(adapter);
    }
    private void GetMangaByRank(Map<String,Object> order) {
        bus.getMangaListRank(order, new MyCallBack<List<Manga>>() {
            @Override
            public void onSuccess(List<Manga> result) {
                MangaByRank = new ArrayList<>(result);
                GetMangaCover(MangaByRank);
                updateListView();
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
                    updateListView();
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }
    }
}