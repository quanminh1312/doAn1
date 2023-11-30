package com.example.doan1.model.Author;
import com.example.doan1.Mangamodel;
import com.example.doan1.model.Relationship;
import java.util.Map;
import java.util.List;
public class Author implements Mangamodel{
    private Map<String, String> id;
    private Map<String, String> type;
    private AuthorAttributes authorAttributes;
    private List<Relationship> listRelationships;
    public Map<String, String> getId() {
        return id;
    }

    public void setId(Map<String, String> id) {
        this.id = id;
    }

    public Map<String, String> getType() {
        return type;
    }

    public void setType(Map<String, String> type) {
        this.type = type;
    }

    public AuthorAttributes getAttributes() {
        return authorAttributes;
    }

    public void setAttributes(AuthorAttributes attributes) {
        this.authorAttributes = attributes;
    }
    public List<Relationship> getRelationships() {
        return listRelationships;
    }
    @Override
    public void setRelationships(List<Relationship> relationships) {
        this.listRelationships = relationships;
    }
}
