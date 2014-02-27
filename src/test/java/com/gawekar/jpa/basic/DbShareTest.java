package com.gawekar.jpa.basic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.BeforeClass;
import org.junit.Test;

import com.gawekar.jpa.model.Person;

public class DbShareTest {
    private static EntityManagerFactory factory;
    @BeforeClass
    public static void initDb() {
        factory = Persistence.createEntityManagerFactory("personPersistenceUnit");
    }
    
    @Test
    public void persist() {
        EntityManager entityManager = factory.createEntityManager();
        try {
            String personName = "happyPath";
            Person person = new Person();
            person.setFirstName(personName);
           
//            Query query = entityManager.createQuery("Select p from Person p where p.firstName=:firstName");
//            query.setParameter("firstName", personName);
//            Person retrieved1 = (Person) query.getSingleResult();
//            TestCase.assertSame(person, retrieved1);
        } finally {
            entityManager.close();
        }
    }
}
