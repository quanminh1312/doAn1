package com.example.doan1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan1.model.Cover.Cover;
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
    static final int REQUEST_CODE = 1;
    private Bus bus = new Bus(this);
    private List<Tag> tagList = new ArrayList<>();
    private Manga manga = new Manga();
    private Map<String,List<Map<String,String>>> listMangaChapter = new HashMap<>();
    TextView textView;
    Button button, button2,button3;

    Gson gson = new Gson();
    // MulTableLiveData field
    private LiveData<Manga> mangaLiveData = bus.getMangaLiveData();
    private LiveData<List<Tag>> listLiveDataTag = bus.getlistMutableLiveDataTag();
    private MutableLiveData<Map<String,List<Map<String,String>>>> listMangaChapterLiveData = bus.getChapterListLiveData();
    private LiveData<Cover> coverLiveData = bus.getCoverMutableLiveData();
    private void create()
    {
        bus.getTag();
        bus.getMangaRandom();
        textView = (TextView) findViewById(R.id.textView);
        button = (Button)  findViewById(R.id.button);
        button2 = (Button)  findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
    }
    private void setEvent()
    {
        // live data fetch data
        mangaLiveData.observe(this, new Observer<Manga>() {
            @Override
            public void onChanged(Manga mangaa) {
                if (mangaa != null)
                {
                    manga = mangaa;
                    textView.setText(manga.getAttributes().getTitle().get("en"));
                    List<Relationship> relationshipList = manga.getRelationships();
                    if (relationshipList !=null && relationshipList.size()>=0)
                    {
                        for (int i=0; i< relationshipList.size(); i++)
                        {
                            if (relationshipList.get(i).getType().equals("cover_art"))
                            {
                                bus.getCover(relationshipList.get(i).getId());
                                break;
                            }
                        }
                    }
                }
            }
        });
        listLiveDataTag.observe(this, new Observer<List<Tag>>() {
            @Override
            public void onChanged(List<Tag> tagListt) {
                if (tagListt != null)
                {
                    tagList = tagListt;
                }
            }
        });
        coverLiveData.observe(this, new Observer<Cover>() {
            @Override
            public void onChanged(Cover cover) {
                if (cover != null) {
                    ImageView imageView = (ImageView) findViewById(R.id.imageView);
                    String url = "https://uploads.mangadex.org/covers/";
                    url += manga.getId() + "/" + cover.getAttributes().getFileName() + ".512.jpg";
                    Picasso.get().load(url).into(imageView);
                }
            }
        });
        listMangaChapterLiveData.observe(this, new Observer<Map<String, List<Map<String, String>>>>() {
            @Override
            public void onChanged(Map<String, List<Map<String, String>>> stringListMap) {
                if (stringListMap != null)
                {
                    Toast.makeText(getApplication(), "co",Toast.LENGTH_LONG).show();
                    listMangaChapter = stringListMap;
                }
            }
        });

        //test api fetch
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (manga != null)
                {
                    bus.getMangaChapter(manga.getId());
                }
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listMangaChapter != null)
                {

                    List<String> keys = new ArrayList<>(listMangaChapter.keySet());
                    Collections.reverse(keys);
                    Map<String,List<Map<String,String>>> reversedHashMap = new HashMap<>();
                    for (String key : keys) {
                        reversedHashMap.put(key, listMangaChapter.get(key));
                    }
                    Intent intent = new Intent(MainActivity.this,ChapterList.class);
                    String json = gson.toJson(reversedHashMap);
                    intent.putExtra("chapterlist",json);
                    startActivityForResult(intent,REQUEST_CODE);
                }
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        create();
        setEvent();
    }
}