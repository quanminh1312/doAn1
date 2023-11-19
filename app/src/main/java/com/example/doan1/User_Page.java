package com.example.doan1;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class User_Page extends AppCompatActivity {
    private String name;
    private String profileImage;

    public User_Page(int id, String name, int age, String profileImage) {
        this.name = name;
        this.profileImage = profileImage;
    }

    // Getters

    public String getName() {
        return name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        setSupportActionBar(findViewById(R.id.usertoolbar));
        getItem();
        getEvent();
    }
    private void getItem(){
        //Get comic
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("1", R.drawable.manga1,"Scream Queen","2020","Horror",50));
        ItemAdapter adapter = new ItemAdapter(this,items);
        ListView listview = findViewById(R.id.listview_user);
        listview.setAdapter(adapter);
    }
    private void getEvent(){

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
         //Handle menu item clicks
         if (id == R.id.tag_item) {
            // Handle the "Tìm Kiếm" item click
            Intent intent_tag = new Intent(User_Page.this, Tag_Page.class);
            startActivity(intent_tag);
            return true;
        } else if (id == R.id.rank_item) {
            // Handle the "Xếp hạng" item click
            Intent intent_rank = new Intent(User_Page.this, Rank_Page.class);
            startActivity(intent_rank);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
