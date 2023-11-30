package com.example.doan1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doan1.adapter.MangaAdapter;
import com.example.doan1.model.Manga.Manga;
import com.example.doan1.model.Manga.MangaAttributes;
import com.example.doan1.model.StaticManga;
import com.example.doan1.model.Tag.Tag;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ChapterList extends AppCompatActivity {

    ListView listView;
    Button button;
    TextView tv1,tv2;
    ImageView img;
    List<String> chapterListAdap = new ArrayList<>();
    Manga manga;
    List<com.example.doan1.model.Chapter.Chapter> chapters = new ArrayList<>();
    Bus bus = new Bus(this);
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chapterlist);
        manga =StaticManga.getManga();
        checklogin();
        create();
        event();
        doSomeThing();
    }
    private void checklogin()
    {
        if  (StaticManga.getLogin())
        {
            String username = StaticManga.getUsername();
            databaseHelper.addReadingRecord(username,manga.getId());
        }
    }
    private void create()
    {
        listView = (ListView) findViewById(R.id.listview1);
        button = (Button) findViewById(R.id.trove_1);
        img = (ImageView) findViewById(R.id.imageView2);
        tv1 = (TextView) findViewById(R.id.textView5);
        tv2 = (TextView) findViewById(R.id.textView8);
    }
    private void event()
    {
        button.setOnClickListener(new View.OnClickListener() {
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
                StaticManga.setChapter(chapters.get(i));
                Intent intent = new Intent(ChapterList.this, Chapter.class);
                startActivity(intent);
            }
        });
    }
    private void doSomeThing()
    {
        callMangaChapter();
        String url = "https://uploads.mangadex.org/covers/" + manga.getId() +"/" + manga.getUrlCover() +".512.jpg";
        Picasso.get().load(url).into(img);
        tv1.setText(manga.getName());
        try {
            List<Tag> tags = manga.getAttributes().getTags();
            String temp = new String("  ");
            for (int i=0; i<tags.size()-1; i++)
            {
                temp = temp + tags.get(i).getName()+" ,";
            }
            temp = temp + tags.get(tags.size()-1).getName();
            tv2.setText(temp);
        }catch (Exception e){}
    }
    private void callMangaChapter()
    {
        bus.getMangaChapter(manga.getId(), new MyCallBack<Map<String, List<com.example.doan1.model.Chapter.Chapter>>>() {
            @Override
            public void onSuccess(Map<String, List<com.example.doan1.model.Chapter.Chapter>> result) {
                if (result != null)
                {
                    for (Map.Entry<String, List<com.example.doan1.model.Chapter.Chapter>> entry : result.entrySet()) {
                        List<com.example.doan1.model.Chapter.Chapter> temp = entry.getValue();
                        // Duyệt qua từng chapter trong List
                        for (com.example.doan1.model.Chapter.Chapter chapterTemp : temp) {
                            chapters.add(chapterTemp);
                        }
                        //sort lại chapter

                        //Duyệt qua từng chapter trong List
                        for (com.example.doan1.model.Chapter.Chapter chapterTemp : chapters) {
                            chapterListAdap.add(chapterTemp.getName());
                        }

                    }
                    updateListView();
                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }
    private void updateListView()
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,chapterListAdap);
        listView.setAdapter(adapter);
    }
}
