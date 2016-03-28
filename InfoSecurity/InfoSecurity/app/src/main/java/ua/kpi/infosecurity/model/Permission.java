package ua.kpi.infosecurity.model;

import io.realm.RealmObject;

/**
 * Created on 3/28/16.
 */
public class Permission extends RealmObject {

    public static final String READ = "r";
    public static final String WRITE = "w";
    public static final String READ_WRITE = "rw";

    private String userId;
    private String fileId;
    private String level;

    public Permission() {
    }

    public Permission(String userId, String fileId, String level) {
        this.userId = userId;
        this.fileId = fileId;
        this.level = level;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "userId='" + userId + '\'' +
                ", fileId='" + fileId + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
