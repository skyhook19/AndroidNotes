package com.dev.notes.model.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;


@DatabaseTable(tableName = "tags")
public class Tag implements Serializable {

    public final static String ID_FIELD_NAME = "id";
    public final static String NAME_FIELD_NAME = "name";

    @DatabaseField(generatedId = true, columnName = ID_FIELD_NAME)
    private Long id;

    @DatabaseField(columnName = NAME_FIELD_NAME, unique = true)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
