package com.example.doan1.model.Chapter;
import com.example.doan1.Mangamodel;
import com.example.doan1.model.Relationship;

import java.util.List;

public class Chapter implements Mangamodel{
    private String id;
    private String type;
    private ChapterAttributes chapterAttributes;
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
    public ChapterAttributes getAttributes() {
        return chapterAttributes;
    }

    public void setAttributes(ChapterAttributes attributes) {
        this.chapterAttributes = attributes;
    }
    public List<Relationship> getRelationships() {
        return listRelationships;
    }
    @Override
    public void setRelationships(List<Relationship> relationships) {
        this.listRelationships = relationships;
    }
}
