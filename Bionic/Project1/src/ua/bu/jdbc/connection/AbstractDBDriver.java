package ua.bu.jdbc.connection;

/**
 * Created by andriybas on 10/11/14.
 */
public abstract class AbstractDBDriver implements DBDriver {


    public abstract String getClassName();

    public void loadDriver() {

    }

}
