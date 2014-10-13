package ua.bu.jdbc.connection;

import javax.sql.DataSource;

/**
 * Created by andriybas on 10/11/14.
 */
public abstract class AbstractDBDriver implements DBDriver {

    public void loadDriver() throws ClassNotFoundException {
        Class.forName(getClassName());
    }

}