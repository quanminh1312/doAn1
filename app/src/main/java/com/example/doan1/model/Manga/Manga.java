package com.example.doan1.model.Manga;
import com.example.doan1.model.Mangamodel;
import com.example.doan1.model.Relationship;
import com.example.doan1.model.Tag.Tag;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Manga implements Mangamodel, Serializable {
    private String id;
    private String type;
    private String name;
    private String urlCover;
    private String Cover;

    public String getCover() {
        return Cover;
    }

    public void setCover(String cover) {
        Cover = cover;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlCover() {
        return urlCover;
    }

    public void setUrlCover(String urlCover) {
        this.urlCover = urlCover;
    }

    private MangaAttributes mangaAttributes;
    private List<Relationship> listRelationships;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MangaAttributes getAttributes() {
        return mangaAttributes;
    }

    public void setAttributes(MangaAttributes attributes) {
        this.mangaAttributes = attributes;
    }
    public List<Relationship> getRelationships() {
        return listRelationships;
    }

    @Override
    public void setRelationships(List<Relationship> relationships) {
        this.listRelationships = relationships;
    }
}

