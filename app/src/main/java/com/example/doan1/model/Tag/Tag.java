package com.example.doan1.model.Tag;
import com.example.doan1.model.Relationship;
import java.util.List;
import java.util.ArrayList;
public class Tag {
    private String id;
    private String name;
    public Tag(){};
    public Tag(Tag other) {
        this.id = other.id;
        this.name = other.name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}