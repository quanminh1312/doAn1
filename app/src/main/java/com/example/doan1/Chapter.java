package com.example.doan1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.doan1.adapter.ImageAdapter;
import com.example.doan1.model.StaticManga;

import java.util.ArrayList;
import java.util.List;

public class Chapter extends AppCompatActivity {

    Bus bus = new Bus(this);
    String chapterId = new String();
    Button button;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chapter);
        chapterId = StaticManga.getChapter().getId();
        button = (Button) findViewById(R.id.trove_2);
        listView = (ListView) findViewById(R.id.list);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                setResult(ChapterList.RESULT_CANCELED, returnIntent);
                finish();
            }
        });
        bus.getChapterImage(chapterId, new MyCallBack<List<String>>() {
            @Override
            public void onSuccess(List<String> result) {
                if (result != null)
                {
                    try {
                        ArrayList<String> arrayList = new ArrayList<>(result);
                        String temp = result.get(0);
                        arrayList.remove(0);
                        ImageAdapter adapter = new ImageAdapter(Chapter.this,R.layout.chapter_listview,arrayList,temp);
                        listView.setAdapter(adapter);
                    }
                    catch (Exception e){}
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
