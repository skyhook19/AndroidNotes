package com.dev.notes.model.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "placeActions")
public class PlaceAction {

    @DatabaseField(generatedId = true)
    private Long id;

}
