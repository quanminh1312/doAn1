package com.example.doan1.model.Chapter;
import com.example.doan1.Mangamodel;
import com.example.doan1.model.Relationship;

import java.util.List;

public class Chapter {
    private String id;
    private String type;

    public String getType() {
        return type;
    }

    public Chapter(String id, String name) {
        this.id = id;
        this.name = name;
    }
    public Chapter()
    {

    }
    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
