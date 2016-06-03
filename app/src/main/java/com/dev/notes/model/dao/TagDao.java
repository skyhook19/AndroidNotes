package com.dev.notes.model.dao;

import com.dev.notes.model.pojo.Tag;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;


public class TagDao extends BaseDaoImpl<Tag, Long> {
    public TagDao(ConnectionSource connectionSource, Class<Tag> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Tag> getAllTags() {
        try {
            return this.queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Tag getTagById(Long id) {
        try {
            QueryBuilder<Tag, Long> queryBuilder = queryBuilder();
            queryBuilder.where().eq(Tag.ID_FIELD_NAME, id);
            PreparedQuery<Tag> preparedQuery = queryBuilder.prepare();
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
    public int create(Tag data) {
        try {
            return super.create(data);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(Tag data) {
        try {
            return super.delete(data);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Tag data) {
        try {
            return super.update(data);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Tag getTagByName(String name) {
        try {
            QueryBuilder<Tag, Long> queryBuilder = queryBuilder();
            queryBuilder.where().eq(Tag.NAME_FIELD_NAME, name);
            PreparedQuery<Tag> preparedQuery = queryBuilder.prepare();
            return queryForFirst(preparedQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
