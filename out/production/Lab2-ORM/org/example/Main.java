package org.example;

import jakarta.persistence.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {


        AddressBook addressBook = new AddressBook();

        // Creating objects representing some products
        BuddyInfo buddyOne = new BuddyInfo();
        buddyOne.setName("Guy");
        buddyOne.setAddress("1 test drive");
        buddyOne.setPhoneNumber("1111111");

        BuddyInfo buddyTwo = new BuddyInfo();
        buddyTwo.setName("Frank");
        buddyTwo.setAddress("2 test drive");
        buddyTwo.setPhoneNumber("222222");

        // Connecting to the database through EntityManagerFactory
        // connection details loaded from persistence.xml
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab2ORM");

        //creating entity manager
        EntityManager em = emf.createEntityManager();

        // Creating a new transaction
        EntityTransaction tx = em.getTransaction();

//        System.out.println("persisting buddies first");
//        tx.begin();
//
//        //persist and commit buddies
//        em.persist(buddyOne);
//        em.persist(buddyTwo);
//
//        tx.commit();

        addressBook.addBuddy(buddyOne);
        addressBook.addBuddy(buddyTwo);

        tx.begin();

        em.persist(addressBook);

        tx.commit();

        // Querying the contents of the database using JPQL query
        Query q = em.createQuery("SELECT a FROM AddressBook a");

        @SuppressWarnings("unchecked")
        List<AddressBook> results = q.getResultList();
        System.out.println("Result list for query  " + q + " returned " + q.getResultList());


        for (AddressBook a : results) {
            System.out.println("Persisted address book: " + a);
            for (BuddyInfo b : a.getBuddies().values()) {
                System.out.println(b.toString());
            }
        }

        // Closing connection
        em.close();

        emf.close();
    }
}