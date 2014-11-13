package com.bu.persist;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.UUID;

/**
 * Created by andriybas on 11/4/14.
 */
public class Test {
    public static void main(String[] args) {

        // persist to the database

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NewPersistenceUnit");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        em.persist(new UserEntity(UUID.randomUUID().toString(), "Vova " + String.valueOf(System.currentTimeMillis())));

        tx.commit();
    }
}
