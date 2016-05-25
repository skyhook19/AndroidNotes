package com.dev.notes.model.pojo;

import com.j256.ormlite.field.DatabaseField;


public class NoteTags {

    public final static String NOTE_ID_FIELD_NAME = "note_id";
    public final static String TAG_ID_FIELD_NAME = "tag_id";

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField(foreign = true, columnName = NOTE_ID_FIELD_NAME)
    private Note note;

    @DatabaseField(foreign = true, columnName = TAG_ID_FIELD_NAME)
    private Tag tag;

    public NoteTags() {}

    public NoteTags(Note note, Tag tag) {
        this.note = note;
        this.tag = tag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
