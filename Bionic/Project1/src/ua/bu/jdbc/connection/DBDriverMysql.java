package ua.bu.jdbc.connection;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by andriybas on 10/11/14.
 */
public class DBDriverMysql extends AbstractDBDriver implements DBDriver {

    private static final String DRIVER_CLASS = "org.gjt.mm.mysql.Driver";

    MysqlDataSource ds;

    @Override
    public String getClassName() {
        return DRIVER_CLASS;
    }

    @Override
    public Connection connect(final String url, final String user, final String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public void loadDataSource(final String url, final String user, final String password) {
        ds = new MysqlDataSource();
        ds.setURL(url);
        ds.setUser(user);
        ds.setPassword(password);
    }

    @Override
    public DataSource getDataSource() {
        return ds;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (ds == null) {
            throw new IllegalStateException("DataSource is not initialised");
        }
        return ds.getConnection();
    }

}
