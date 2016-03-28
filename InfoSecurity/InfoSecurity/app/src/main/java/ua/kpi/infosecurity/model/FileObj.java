package ua.kpi.infosecurity.model;

import io.realm.RealmObject;

/**
 * Created on 3/28/16.
 */
public class FileObj extends RealmObject {

    public static final String TYPE_FILE = "file";
    public static final String TYPE_FOLDER = "folder";

    private String uuid;
    private String name;
    private String type;

    public FileObj() {
    }

    public FileObj(String uuid, String name, String type) {
        this.uuid = uuid;
        this.name = name;
        this.type = type;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isFolder() {
        return TYPE_FOLDER.equals(type);
    }

    public boolean isFile() {
        return TYPE_FILE.equals(type);
    }

    @Override
    public String toString() {
        return "FileObj{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
