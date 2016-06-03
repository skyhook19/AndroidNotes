package com.dev.notes.model.dao;

import com.dev.notes.model.pojo.NoteTags;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;


public class NoteTagsDao extends BaseDaoImpl<NoteTags, Long> {

    public NoteTagsDao(ConnectionSource connectionSource, Class<NoteTags> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<NoteTags> getAllNoteTags() {
        try {
            return this.queryForAll();
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
    public int create(NoteTags data) {
        try {
            return super.create(data);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(NoteTags data) {
        try {
            return super.delete(data);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(NoteTags data) {
        try {
            return super.update(data);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
