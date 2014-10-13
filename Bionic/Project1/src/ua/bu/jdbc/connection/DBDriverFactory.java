package ua.bu.jdbc.connection;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by andriybas on 10/11/14.
 */
public final class DBDriverFactory {

    public DBDriverFactory() {
    }

    public static DBDriver createDriver(DBType dbTypes) throws ClassNotFoundException {
        DBDriver driver;
        switch (dbTypes) {
            case MySql:
                driver = new DBDriverMysql();
                break;
            case Oracle:
                throw new ClassNotFoundException("Oracle DB is not implemented yet");
            default:
                driver = new DBDriverMysql();
                break;
        }
        return driver;
    }
}
