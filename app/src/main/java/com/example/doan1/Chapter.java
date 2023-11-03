package com.example.doan1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Chapter extends AppCompatActivity {
    Intent intent;
    Bus buss = new Bus(this);
    private MutableLiveData<List<String>> chapter = buss.getImagechapter();
    String chapterId;
    Button button;
    ListView listView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chapter);
        intent = getIntent();
        chapterId = intent.getStringExtra("chapter");
        chapterId = chapterId.substring(1,chapterId.length()-1);
        button = (Button) findViewById(R.id.trove_2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                setResult(ChapterList.RESULT_CANCELED, returnIntent);
                finish();
            }
        });
        buss.getchapterimage(chapterId);
        listView = (ListView) findViewById(R.id.list);
        chapter.observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                if (strings != null)
                {
                    try {
                        ArrayList<String> arrayList = new ArrayList<>(strings);
                        String temp = strings.get(0);
                        arrayList.remove(0);
                        ImageAdapter adapter = new ImageAdapter(Chapter.this,R.layout.chapter_listview,arrayList,temp);
                        listView.setAdapter(adapter);
                    }
                    catch (Exception e){}
                }
            }
        });
    }
}
