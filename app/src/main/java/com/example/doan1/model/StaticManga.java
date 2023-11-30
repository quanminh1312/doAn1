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
    static Boolean login = false;
    static String username;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        StaticManga.username = username;
    }

    static List<Tag> tag;

    public static List<Tag> getTag() {
        return tag;
    }

    public static void setTag(List<Tag> tag) {
        StaticManga.tag = tag;
    }

    public static Boolean getLogin() {
        return login;
    }

    public static void setLogin(Boolean login) {
        StaticManga.login = login;
    }

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
