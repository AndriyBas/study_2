package ua.bu.jdbc;

import ua.bu.jdbc.Logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by andriybas on 10/9/14.
 */
public class Example {

    public static void main(String[] argv) {

        Connection connection = null;

        try {
            connection = Logic.getConnection();

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            System.out.println("Done !");
        } else {
            System.out.println("Failed to make connection!");
        }
    }

}
