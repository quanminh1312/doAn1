package com.example.doan1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan1.model.StaticManga;
import com.example.doan1.model.Tag.Tag;

import java.util.ArrayList;
import java.util.List;

public class Tag_Page extends AppCompatActivity {

    TextView textView;
    ListView listView;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_page);
        create();
        setEvent();
    }
    private void create()
    {
        textView = (TextView) findViewById(R.id.return_link2);
        listView = (ListView) findViewById(R.id.list);
        setadapter();
    }
    private void setadapter()
    {
        List<Tag> tags = StaticManga.getTags();
        List<String> tag = new ArrayList<>();
        for (int i =0; i<tags.size();i++)
        {
            tag.add(tags.get(i).getName());
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,tag);
        listView.setAdapter(adapter);
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
                List<Tag> tags = StaticManga.getTags();
                List<Tag> tag = new ArrayList<>();
                tag.add(tags.get(i));
                StaticManga.setTag(tag);
                Intent intent = new Intent(Tag_Page.this,Tag_page_manga.class);
                startActivity(intent);
            }
        });
    }
}