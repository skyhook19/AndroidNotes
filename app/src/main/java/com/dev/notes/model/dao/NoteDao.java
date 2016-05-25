package com.dev.notes.model.dao;

import com.dev.notes.model.pojo.Note;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class NoteDao extends BaseDaoImpl<Note, Long> {

    public NoteDao(ConnectionSource connectionSource, Class<Note> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Note> getAllNotes() {
        try {
            return this.queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Note getNoteById(Long id) {
        try {
            QueryBuilder<Note, Long> queryBuilder = queryBuilder();
            queryBuilder.where().eq(Note.ID_FIELD_NAME, id);
            PreparedQuery<Note> preparedQuery = queryBuilder.prepare();
            return queryForFirst(preparedQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteById(Long id) {
        try {
            return super.deleteById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int create(Note data) {
        try {
            return super.create(data);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(Note data) {
        try {
            return super.delete(data);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Note data) {
        try {
            return super.update(data);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
