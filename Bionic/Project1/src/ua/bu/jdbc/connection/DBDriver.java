package ua.bu.jdbc.connection;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by andriybas on 10/11/14.
 */
public interface DBDriver {

    public String getClassName();

    public Connection connect(final String url, final String user, final String password) throws SQLException;

    public void loadDataSource(final String url, final String user, final String password);

    public DataSource getDataSource();

    public Connection getConnection() throws SQLException;
}
