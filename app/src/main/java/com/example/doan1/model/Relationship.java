package com.example.doan1.model;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Relationship implements Serializable {
    private String id;
    private String type;
    private String related;
    private Map<String, Object> attributes;
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

    public String getRelated() {
        return related;
    }

    public void setRelated(String related) {
        this.related = related;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
}
