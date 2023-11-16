package com.example.doan1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChapterList extends AppCompatActivity {
    String json;
    List<String> chapterListAdap = new ArrayList<>();
    List<String> chapterlistid = new ArrayList<>();
    Gson gson = new Gson();
    ListView listView;
    Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chapterlist);
        listView = (ListView) findViewById(R.id.listview1);
        button = (Button) findViewById(R.id.trove_1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                setResult(MainActivity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });
        if (json != null)
        {
            Type type = new TypeToken<Map<String, List<Map<String, String>>>>(){}.getType();
            Map<String, List<Map<String, String>>> chapterList = gson.fromJson(json, type);
            try {
                for (Map.Entry<String, List<Map<String, String>>> entry : chapterList.entrySet()) {
                    String key = entry.getKey();
                    List<Map<String, String>> value = entry.getValue();
                    for (Map<String, String> chapter: value) {
                        for (Map.Entry<String,String> id: chapter.entrySet()) {
                            chapterListAdap.add("chapter " + id.getKey().substring(1,id.getKey().length() - 1));
                            chapterlistid.add(id.getValue());
                        }
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,chapterListAdap);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        try {

                            Intent intent = new Intent(ChapterList.this,Chapter.class);
                            startActivity(intent);
                        }
                        catch (Exception e){}
                    }
                });
            }
            catch (Exception e){}
        }
    }
}
