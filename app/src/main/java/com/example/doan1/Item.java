package com.example.doan1;

public class Item {
    private String id;
    private int imageResId;
    private String name;
    private String date;
    private String genre;
    private int likes;

    public Item(String id,int imageResId, String name, String date, String genre,int likes){
        this.id = id;
        this.imageResId = imageResId;
        this.name = name;
        this.date = date;
        this.genre = genre;
        this.likes = likes;
    }
    public String getId(){
        return id;
    }
    public int getImageResId() {
        return imageResId;
    }
    public String getName() {
        return name;
    }
    public String getDate() {
        return date;
    }
    public String getGenre() {
        return genre;
    }
    public int getLikes(){
        return likes;
    }
}
