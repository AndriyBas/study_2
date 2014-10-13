package ua.bu.jdbc.entities;

import java.io.Serializable;

/**
 * Created by andriybas on 10/13/14.
 */
public class Tags implements Serializable {

    private static final long serialVersionUID = 42L;

    String name;
    long creatorId;
    long id;

    public Tags() {
    }

    public Tags(String name, long creatorId, long id) {
        this.name = name;
        this.creatorId = creatorId;
        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tags tags = (Tags) o;

        if (creatorId != tags.creatorId) return false;
        if (id != tags.id) return false;
        if (!name.equals(tags.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (int) (creatorId ^ (creatorId >>> 32));
        result = 31 * result + (int) (id ^ (id >>> 32));
        return result;
    }
}
