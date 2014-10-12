package ua.bu.jdbc.connection;

/**
 * Created by andriybas on 10/11/14.
 */
public enum DBType {
    MySql("MySql"),
    Oracle("Oracle");

    public String getDbName() {
        return dbName;
    }

    private final String dbName;

    DBType(String dbName) {
        this.dbName = dbName;
    }

}
