package com.bu.persist;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by andriybas on 11/4/14.
 */

@Entity
public class UserEntity {

    @Id
    String id;

    String name;

    public UserEntity() {
    }

    public UserEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity)) return false;

        UserEntity userEntity = (UserEntity) o;

        if (id != null ? !id.equals(userEntity.id) : userEntity.id != null) return false;
        if (name != null ? !name.equals(userEntity.name) : userEntity.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
