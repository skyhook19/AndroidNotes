package com.dev.notes.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Note {

    public enum Type {RED, GREEN, YELLOW}

    private String content;
    private String title;
    private Date date;
    private Type type;
    private List<Tag> tags = new ArrayList<Tag>();

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
