package ua.bu.jdbc;

import ua.bu.jdbc.connection.DBDriver;
import ua.bu.jdbc.connection.DBDriverFactory;
import ua.bu.jdbc.connection.DBType;
import ua.bu.jdbc.connection.Database;
import ua.bu.jdbc.dao.TagDao;
import ua.bu.jdbc.dao.impl.TagDaoImpl;
import ua.bu.jdbc.entities.Tags;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by andriybas on 10/14/14.
 */
public class TestDao {

    public static void main(String[] args) {
        try {
            DBDriver driver = DBDriverFactory.createDriver(DBType.MySql);
            Database db = new Database("ua.bu.db_mysql", driver);
            db.loadDataSource();
            TagDao tagDao = new TagDaoImpl(db);

            List<Tags> all = tagDao.findAll();

            List<Tags> a1 = tagDao.findByName("Java");
            List<Tags> a2 = tagDao.findById(1);
            List<Tags> a3 = tagDao.findByCreatorId(0);

            System.out.println("cool");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
