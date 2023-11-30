package com.example.doan1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.example.doan1.adapter.MangaAdapter;
import com.example.doan1.model.Manga.Manga;
import com.example.doan1.model.StaticManga;
import com.example.doan1.model.Tag.Tag;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    private ArrayList<User_Page> userList;
    ListView listView;
    Button button, button2;
    TextView textView;
    private Bus bus = new Bus(this);
    private int Page = 0;
    private List<Tag> MangaTag = new ArrayList<>();
    private List<Manga> MangaByPage = new ArrayList<>();
    private MangaAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userList = new ArrayList<>();
        create();
        setEvent();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (StaticManga.getLogin())
            getMenuInflater().inflate(R.menu.user_menu,menu);
        else getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        // Handle menu item clicks
        if (id == R.id.Login_item) {
            // Handle the "Đăng Nhập" item click
            Intent intent_log = new Intent(MainActivity.this, Login_Page.class);
            startActivity(intent_log);
            return true;
        } else if (id == R.id.register_item) {
            // Handle the "Đăng ký" item click
            Intent intent_reg = new Intent(MainActivity.this, Register_Page.class);
            startActivity(intent_reg);
            return true;
        } else if (id == R.id.tag_item) {
            // Handle the "Thể loại" item click
            Intent intent_tag = new Intent(MainActivity.this, Tag_Page.class);
            startActivity(intent_tag);
            return true;
        } else if (id == R.id.rank_item) {
            // Handle the "Xếp hạng" item click
            Intent intent_rank = new Intent(MainActivity.this, Rank_Page.class);
            startActivity(intent_rank);
            return true;
        }
        else if (id == R.id.Logout_item)
        {
            StaticManga.setLogin(false);
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.history_item && StaticManga.getLogin())
        {
            Intent intent = new Intent(MainActivity.this, User_Page.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void updateListView()
    {
        adapter = new MangaAdapter(MainActivity.this,R.layout.mainpage,MangaByPage);
        listView.setAdapter(adapter);
    }
    private void GetMangaByPage(int page) {
        bus.getMangaListByPage(page, new MyCallBack<List<Manga>>() {
            @Override
            public void onSuccess(List<Manga> result) {
                MangaByPage = new ArrayList<>(result);
                GetMangaCover(MangaByPage);
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
    private void create()
    {
        listView = (ListView) findViewById(R.id.list_view);
        button = (Button)  findViewById(R.id.button);
        button2 = (Button)  findViewById(R.id.button2);
        textView = (TextView) findViewById(R.id.textView2);
        textView.setText(String.valueOf(Page / 10 + 1));
        bus.getTag(new MyCallBack<List<Tag>>() {
            @Override
            public void onSuccess(List<Tag> result) {
                MangaTag = result;
                StaticManga.setTags(MangaTag);

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
        GetMangaByPage(Page);
    }
    private void setEvent()
    {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Page = Math.max(0,(Page-10));
                textView.setText(String.valueOf(Page / 10 + 1));
                GetMangaByPage(Page);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Page = Math.min((Page+10),1000);
                textView.setText(String.valueOf(Page / 10 + 1));
                GetMangaByPage(Page);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                StaticManga.setManga(MangaByPage.get(i));
                Intent intent = new Intent(MainActivity.this,ChapterList.class);
                startActivity(intent);
            }
        });
    }
}