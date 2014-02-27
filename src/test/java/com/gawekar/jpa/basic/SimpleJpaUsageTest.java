package com.gawekar.jpa.basic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

import junit.framework.TestCase;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.gawekar.jpa.model.Person;

public class SimpleJpaUsageTest {
    
    private static EntityManagerFactory factory;
    
    @BeforeClass
    public static void initDb() {
        factory = Persistence.createEntityManagerFactory("personPersistenceUnit");
    }
    
    @AfterClass
    public static void close() {
        factory.close();
    }
    
    /**
     * Transaction is needed.
     * Note here transaction is not started. But 'flush()' is performed.
     */
    @Test(expected = TransactionRequiredException.class)
    public void needsTransaction() {
        EntityManager entityManager = factory.createEntityManager();
        try {
            String personName = "Gautam";
            Person person = new Person();
            person.setFirstName(personName);
            //entityManager.getTransaction().begin();
            entityManager.persist(person);
            entityManager.flush();
            //entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }
    
    @Test(expected = NoResultException.class)
    public void needsTransaction1() {
        EntityManager entityManager = factory.createEntityManager();
        try {
            String personName = "needsTransaction1";
            Person person = new Person();
            person.setFirstName(personName);
            //entityManager.getTransaction().begin();
            entityManager.persist(person);//can imagine as if this call completely ignored!!
            //entityManager.getTransaction().commit();
            
            Query query = entityManager.createQuery("Select p from Person p where p.firstName=:firstName");
            query.setParameter("firstName", personName);
            Person retrieved1 = (Person) query.getSingleResult();
            TestCase.assertSame(person, retrieved1);
        } finally {
            entityManager.close();
        }
    }
    
    @Test
    public void persist() {
        EntityManager entityManager = factory.createEntityManager();
        Person retrieved1 = null;
        try {
            String personName = "happyPath";
            Person person = new Person();
            person.setFirstName(personName);
            entityManager.getTransaction().begin();
            entityManager.persist(person);
            entityManager.getTransaction().commit();
            Query query = entityManager.createQuery("Select p from Person p where p.firstName=:firstName");
            query.setParameter("firstName", personName);
            retrieved1 = (Person) query.getSingleResult();
            TestCase.assertEquals(person, retrieved1);
        } finally {
            entityManager.close();
        }
        if (retrieved1 != null) {
            remove(retrieved1.getId());
        }else {
            TestCase.fail("failed to add entity");
        }
        
    }
    
    @Test(expected = RuntimeException.class)
    public void rollback() {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();;
        try {
            String personName = "happyPath";
            Person person = new Person();
            person.setFirstName(personName);
            transaction.begin();
            entityManager.persist(person);
            throw new RuntimeException("some exeception");
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }else {
                TestCase.fail("Transaction should be active");
            }
            entityManager.close();
        }
    }
    
    //@Test
    public void remove(int id) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            Person person = entityManager.find(Person.class, id);
            transaction.begin();
            entityManager.remove(person);
            transaction.commit();
        }finally {
            if (transaction.isActive()) {
                transaction.rollback();
                TestCase.fail("Transaction should not be active");
            }
            entityManager.close();
        }
    }
    
    @Test
    public void notusingTransactionMakesNoDifference() {
        String personName = "notusingTransactionMakesNoDifference";
        {
            EntityManager entityManager = factory.createEntityManager();
            Person person = new Person();
            person.setFirstName(personName);
            entityManager.persist(person);
            entityManager.close();
        }
        
        {
            EntityManager entityManager = factory.createEntityManager();
            Query query = entityManager.createQuery("Select p from Person p where p.firstName=:firstName");
            query.setParameter("firstName", personName);
            try {
                query.getSingleResult();
                TestCase.fail("Should have thrown NoResultException");
            } catch (NoResultException e) {
                TestCase.assertTrue(true);
            }catch (Exception e) {
                TestCase.fail("Should have thrown NoResultException");
            }
            entityManager.close();
        }
    }
    
    @Test
    public void needToUseTransaction() {
        {
            EntityManager entityManager = factory.createEntityManager();
            String personName = "Gautam";
            Person person = new Person();
            person.setFirstName(personName);
            entityManager.getTransaction().begin();
            entityManager.persist(person);
            entityManager.getTransaction().commit();
            entityManager.close();
        }
        
        {
            EntityManager entityManager = factory.createEntityManager();
            Person dbPerson = entityManager.find(Person.class, 1);
            TestCase.assertNotNull(dbPerson);
            dbPerson.setFirstName("gautam changed");
            entityManager.getTransaction().begin();
            entityManager.merge(dbPerson);
            entityManager.getTransaction().commit();
            entityManager.close();
        }
        
        {
            EntityManager entityManager = factory.createEntityManager();
            Person dbPerson = entityManager.find(Person.class, 1);
            TestCase.assertEquals("gautam changed", dbPerson.getFirstName());
            entityManager.close();
        }
    }
    
    @Test
    public void updatePersonWhileAttachedToPersistenceContext() {
        String personName = "updatePersonWhileAttachedToPersistenceContext";
        int id = -1;
        {
            EntityManager entityManager = factory.createEntityManager();
            
            Person person = new Person();
            person.setFirstName(personName);
            entityManager.getTransaction().begin();
            entityManager.persist(person);
            entityManager.getTransaction().commit();
            entityManager.close();
            id = person.getId();
        }
        
        {
            //Note that we are operating directly on the Object & not using merge.
            EntityManager entityManager = factory.createEntityManager();
            Person dbPerson = entityManager.find(Person.class, id);
            TestCase.assertNotNull(dbPerson);
            entityManager.getTransaction().begin();
            dbPerson.setFirstName(personName + "  Changed");
            entityManager.getTransaction().commit();
            entityManager.close();
        }
        
        {
            //Note here we have operated directly on the object.
            EntityManager entityManager = factory.createEntityManager();
            Person dbPerson = entityManager.find(Person.class, id);
            entityManager.close();
            TestCase.assertNotNull(dbPerson);
            TestCase.assertEquals(personName + "  Changed", dbPerson.getFirstName());
        }
    }
    
    @Test
    public void typedQuery() {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.close();
        
    }
}
