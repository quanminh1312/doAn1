package com.example.doan1.model;

import android.widget.ListView;

import com.example.doan1.Chapter;
import com.example.doan1.model.Chapter.ChapterAttributes;
import com.example.doan1.model.Manga.Manga;
import com.example.doan1.model.Tag.Tag;

import java.util.List;

public class StaticManga {
    static Manga manga;
    static com.example.doan1.model.Chapter.Chapter chapter;
    static List<Tag> tags;
    public static List<Tag> getTags() {
        return tags;
    }

    public static void setTags(List<Tag> tags) {
        StaticManga.tags = tags;
    }

    public static com.example.doan1.model.Chapter.Chapter getChapter() {
        return chapter;
    }

    public static void setChapter(com.example.doan1.model.Chapter.Chapter chapter) {
        StaticManga.chapter = chapter;
    }

    public static Manga getManga() {
        return manga;
    }
    public static void setManga(Manga manga) {
        StaticManga.manga = manga;
    }
}
