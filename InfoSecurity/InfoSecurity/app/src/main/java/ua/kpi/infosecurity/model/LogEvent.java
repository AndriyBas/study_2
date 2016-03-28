package ua.kpi.infosecurity.model;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created on 3/28/16.
 */
public class LogEvent extends RealmObject {

    public static final String TYPE_ERROR = "error";
    public static final String TYPE_WARNING = "warning";
    public static final String TYPE_LOG = "warning";

    private Date createdAt;
    private String text;
    private String type;

    public LogEvent() {
    }

    public LogEvent(Date createdAt, String text) {
        this.createdAt = createdAt;
        this.text = text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isCritical() {
        return TYPE_ERROR.equals(type);
    }

    @Override
    public String toString() {
        return "LogEvent{" +
                "createdAt=" + createdAt +
                ", text='" + text + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}

