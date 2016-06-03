package com.dev.notes.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.dev.notes.model.dao.NoteDao;
import com.dev.notes.model.dao.NoteTagsDao;
import com.dev.notes.model.dao.TagDao;
import com.dev.notes.model.pojo.Note;
import com.dev.notes.model.pojo.NoteTags;
import com.dev.notes.model.pojo.Tag;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "notesapp.db";
    private static final int DATABASE_VERSION = 11;

    private NoteDao noteDao;
    private TagDao tagDao;
    private NoteTagsDao noteTagsDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Note.class);
            TableUtils.createTable(connectionSource, Tag.class);
            TableUtils.createTable(connectionSource, NoteTags.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            //Так делают ленивые, гораздо предпочтительнее не удаляя БД аккуратно вносить изменения
            TableUtils.dropTable(connectionSource, Note.class, true);
            TableUtils.dropTable(connectionSource, Tag.class, true);
            TableUtils.dropTable(connectionSource, NoteTags.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public NoteDao getNoteDao() {
        if (noteDao == null) {
            try {
                noteDao = new NoteDao(getConnectionSource(), Note.class);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return noteDao;
    }

    public TagDao getTagDao() {
        if (tagDao == null) {
            try {
                tagDao = new TagDao(getConnectionSource(), Tag.class);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return tagDao;
    }

    public NoteTagsDao getNoteTagsDao() {
        if (noteTagsDao == null) {
            try {
                noteTagsDao = new NoteTagsDao(getConnectionSource(), NoteTags.class);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return noteTagsDao;
    }

    //выполняется при закрытии приложения
    @Override
    public void close() {
        super.close();
        noteDao = null;
        tagDao = null;
        noteTagsDao = null;
    }
}
