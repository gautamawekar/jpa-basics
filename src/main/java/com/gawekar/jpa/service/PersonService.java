package com.gawekar.jpa.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.gawekar.jpa.model.Person;

public class PersonService {
    protected EntityManager em;

    public PersonService(EntityManager em) {
        this.em = em;
    }

    public Person createPerson(String name, long salary) {
        Person emp = new Person();
        emp.setFirstName(name);
        emp.setSalary(salary);
        em.persist(emp);
        return emp;
    }

    public void removePerson(int id) {
        Person emp = findPerson(id);
        if (emp != null) {
            em.remove(emp);
        }
    }

    public Person raisePersonSalary(int id, long raise) {
        Person emp = em.find(Person.class, id);
        if (emp != null) {
            emp.setSalary(emp.getSalary() + raise);
        }
        return emp;
    }

    public Person findPerson(int id) {
        return em.find(Person.class, id);
    }

    public List<Person> findAllPersons() {
        TypedQuery<Person> query = em.createQuery(
                  "SELECT e FROM Person e", Person.class);
        return query.getResultList();
    }
}
