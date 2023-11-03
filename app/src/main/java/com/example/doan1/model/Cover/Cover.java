package com.example.doan1.model.Cover;
import com.example.doan1.model.Mangamodel;
import com.example.doan1.model.Relationship;

import java.util.List;

public class Cover implements Mangamodel {
    private String id;
    private String type;
    private CoverAttributes coverAttributes;
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

    public CoverAttributes getAttributes() {
        return coverAttributes;
    }

    public void setAttributes(CoverAttributes attributes) {
        this.coverAttributes = attributes;
    }
    public List<Relationship> getRelationships() {
        return listRelationships;
    }
    @Override
    public void setRelationships(List<Relationship> relationships) {
        this.listRelationships = relationships;
    }
}
