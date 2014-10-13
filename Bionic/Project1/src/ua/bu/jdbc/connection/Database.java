package ua.bu.jdbc.connection;

import ua.bu.jdbc.entities.Tags;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by andriybas on 10/11/14.
 */
public class Database {

    private final String url;
    private final String user;
    private final String password;
    private final DBDriver driver;

    private Connection connection;

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public DBDriver getDriver() {
        return driver;
    }

    public Connection getConnection() {
        return connection;
    }

    public Connection getDSConnection() throws SQLException {
        return driver.getConnection();
    }

    public void loadDataSource() {
        driver.loadDataSource(url, user, password);
    }

    public Database(String propertiesName, DBDriver driver) {
        ResourceBundle bundle = ResourceBundle.getBundle(propertiesName);

        this.url = bundle.getString("url");
        this.user = bundle.getString("user");
        this.password = bundle.getString("password");

        this.driver = driver;
    }

    public void connect() throws SQLException {
        this.connection = getDriver().connect(url, user, password);
    }

}
