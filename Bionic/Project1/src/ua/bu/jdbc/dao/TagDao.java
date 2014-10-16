package ua.bu.jdbc.dao;

import ua.bu.jdbc.entities.Tags;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by andriybas on 10/11/14.
 */
public interface TagDao {

    public List<Tags>  findAll() throws SQLException;

    public List<Tags> findByCreatorId(long userId) throws SQLException;

    public List<Tags>  findByName(String name) throws SQLException;

    public List<Tags>  findById(long id) throws SQLException;

    public List<Tags> findBySelect(String sql, Object[] sqlParams) throws SQLException;

}
