package com.example.doan1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

public class Tag_page_manga extends AppCompatActivity {
    TextView textView;
    ListView listView;
    Button button, button2;
    TextView textView2;
    private int Page = 0;
    Bus bus = new Bus(this);
    MangaAdapter adapter;
    List<Manga> MangaByTag;
    List<Tag> tag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_page);
        tag = StaticManga.getTag();
        create();
        setEvent();
    }
    private void create()
    {
        textView = (TextView) findViewById(R.id.return_link2);
        textView2 = (TextView) findViewById(R.id.textView2);
        listView = (ListView) findViewById(R.id.list);
        button = (Button)  findViewById(R.id.button);
        button2 = (Button)  findViewById(R.id.button2);
        GetMangaByTag();
    }
    private void setEvent()
    {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Page = Math.max(0,(Page-10));
                textView2.setText(String.valueOf(Page / 10 + 1));
                GetMangaByTag();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Page = Math.min((Page+10),1000);
                textView2.setText(String.valueOf(Page / 10 + 1));
                GetMangaByTag();
            }
        });
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
                StaticManga.setManga(MangaByTag.get(i));
                Intent intent = new Intent(Tag_page_manga.this,ChapterList.class);
                startActivity(intent);
            }
        });
    }
    private void updateListView()
    {
        adapter = new MangaAdapter(Tag_page_manga.this,R.layout.mainpage,MangaByTag);
        listView.setAdapter(adapter);
    }
    private void GetMangaByTag() {
        String tags = new String();
        tags = tag.get(0).getId();
        bus.getMangaTagInclude(Page,tags, new MyCallBack<List<Manga>>() {
            @Override
            public void onSuccess(List<Manga> result) {
                MangaByTag = new ArrayList<>(result);
                GetMangaCover(MangaByTag);
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
