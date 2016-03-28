package ua.kpi.infosecurity.model;

import io.realm.RealmObject;

/**
 * Created on 3/28/16.
 */
public class User extends RealmObject {

    private String name;
    private String email;
    private String uuid;
    private String password;
    private int secret;
    private int allowedAuthFailures;

    public User() {
    }

    public User(String name, String uuid, String password, int secret) {
        this.name = name;
        this.uuid = uuid;
        this.password = password;
        this.secret = secret;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSecret() {
        return secret;
    }

    public void setSecret(int secret) {
        this.secret = secret;
    }

    public boolean isAdmin() {
        return "admin".equals(getName());
    }

    public int getAllowedAuthFailures() {
        return allowedAuthFailures;
    }

    public void setAllowedAuthFailures(int allowedAuthFailures) {
        this.allowedAuthFailures = allowedAuthFailures;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", uuid='" + uuid + '\'' +
                ", password='" + password + '\'' +
                ", secret=" + secret +
                '}';
    }
}
