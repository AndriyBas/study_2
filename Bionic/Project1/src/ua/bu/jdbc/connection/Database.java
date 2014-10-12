package ua.bu.jdbc.connection;

import java.sql.Connection;
import java.util.ResourceBundle;

/**
 * Created by andriybas on 10/11/14.
 */
public class Database {

    private final String url;
    private final String user;
    private final String password;
    private final DBDriver driver;


    private Connection conn;


    public Database(String propertiesName, DBDriver driver) {
        ResourceBundle bundle = ResourceBundle.getBundle(propertiesName);

        this.url = bundle.getString("url");
        this.user = bundle.getString("user");
        this.password = bundle.getString("password");

        this.driver = driver;
    }

}
