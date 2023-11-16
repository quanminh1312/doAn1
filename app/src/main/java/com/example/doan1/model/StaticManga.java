package com.example.doan1.model;

import android.widget.ListView;

import com.example.doan1.model.Manga.Manga;

public class StaticManga {
    static Manga manga;
    public static Manga getManga() {
        return manga;
    }
    public static void setManga(Manga manga) {
        StaticManga.manga = manga;
    }
}
