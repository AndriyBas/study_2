package ua.bu.jdbc.dao.impl;

import ua.bu.jdbc.connection.Database;
import ua.bu.jdbc.dao.TagDao;
import ua.bu.jdbc.entities.Tags;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andriybas on 10/14/14.
 */
public class TagDaoImpl implements TagDao {

    final Database db;

    final static String TABLE_NAME = "tags";
    final static String SQL_SELECT = "SELECT id, name, creatorId FROM " + TABLE_NAME;

    final static String COLUMN_ID = "id";
    final static String COLUMN_NAME = "name";
    final static String COLUMN_CREATOR_ID = "creatorId";


    public TagDaoImpl(final Database db) {
        this.db = db;
    }


    private List<Tags> fetchMultiResult(ResultSet resultSet) throws SQLException {
        List<Tags> tagsList = new ArrayList<>();

        while (resultSet.next()) {
            tagsList.add(populateTag(resultSet));
        }

        return tagsList;
    }

    private Tags populateTag(ResultSet resultSet) throws SQLException {
        Tags tags = new Tags();

        tags.setId(resultSet.getLong(COLUMN_ID));
        tags.setName(resultSet.getString(COLUMN_NAME));
        tags.setCreatorId(resultSet.getLong(COLUMN_CREATOR_ID));

        return tags;
    }

    @Override
    public List<Tags> findAll() throws SQLException {
        return findBySelect(SQL_SELECT, null);
    }

    @Override
    public List<Tags> findByUserId(long userId) throws SQLException {
        return findBySelect(SQL_SELECT + " WHERE creatorId = ? ;", new Object[]{userId});
    }

    @Override
    public List<Tags> findByName(String name) throws SQLException {
        return findBySelect(SQL_SELECT + " WHERE name = ? ;", new Object[]{name});
    }

    @Override
    public List<Tags> findById(long id) throws SQLException {
        return findBySelect(SQL_SELECT + " WHERE id = ? ;", new Object[]{id});
    }

    @Override
    public List<Tags> findBySelect(String sql, Object[] sqlParams) throws SQLException {

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        try {
            conn = db.getDSConnection();
            stmt = conn.prepareStatement(sql);

            if (sqlParams != null)
                for (int i = 0; i < sqlParams.length; i++) {
                    stmt.setObject(i + 1, sqlParams[i]);
                }

            resultSet = stmt.executeQuery();

            return fetchMultiResult(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
