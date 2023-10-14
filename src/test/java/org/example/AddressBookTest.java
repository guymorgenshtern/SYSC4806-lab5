package org.example;

import com.thoughtworks.qdox.model.expression.Add;
import jakarta.persistence.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AddressBookTest {

    private AddressBook addressBook;

    /**
     * setup, creates a new address book before each test
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        addressBook = new AddressBook();
        System.out.println("\n\n");
    }

    /**
     * tests adding to address book by asserting the newly added BuddyInfo exists in hashmap of buddies
     */
    @Test
    public void addBuddy() {
        BuddyInfo testBud = new BuddyInfo("testBud", "112-123-3311", "hello drive");
        addressBook.addBuddy(testBud);

        assertTrue(addressBook.getBuddies().containsValue(testBud));
    }

    /**
     * tests removing a BuddyInfo by asserting removed BuddyInfo is no longer in hashmap of buddies
     */
    @Test
    public void removeBuddy() {
        BuddyInfo testBud = new BuddyInfo("testBud", "112-123-3311", "hello drive");
        addressBook.addBuddy(testBud);

        addressBook.removeBuddy(testBud);

        assertFalse(addressBook.getBuddies().containsValue(testBud));
    }

    /**
     * tests getBuddy by adding a buddy, retrieving associated buddyInfo obj and asserting wquality
     */
    @Test
    public void getBuddy() {
        BuddyInfo testBud = new BuddyInfo("testBud", "112-123-3311", "hello drive");
        addressBook.addBuddy(testBud);

        BuddyInfo buddyInBook = addressBook.getBuddy(testBud.getName());
        assertEquals(buddyInBook, testBud);
    }

    @Test
    public void persistenceTest() {

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

        System.out.println("persisting buddies first");
        tx.begin();

        //persist and commit buddies
        em.persist(buddyOne);
        em.persist(buddyTwo);

        tx.commit();

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

    @Test
    public void persistenceTestUsingCascading() {
        System.out.println("--------CASCADING TEST--------");

        // Creating objects representing some products
        BuddyInfo buddyOne = new BuddyInfo();
        buddyOne.setName("Guy");
        buddyOne.setAddress("1 cascadeTest drive");
        buddyOne.setPhoneNumber("1111111");

        BuddyInfo buddyTwo = new BuddyInfo();
        buddyTwo.setName("Frank");
        buddyTwo.setAddress("2 cascadeTest drive");
        buddyTwo.setPhoneNumber("222222");

        // Connecting to the database through EntityManagerFactory
        // connection details loaded from persistence.xml
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab2ORM");

        //creating entity manager
        EntityManager em = emf.createEntityManager();

        // Creating a new transaction
        EntityTransaction tx = em.getTransaction();

        //persisting buddies is omitted here

        // buddies are added to address book
        addressBook.addBuddy(buddyOne);
        addressBook.addBuddy(buddyTwo);

        tx.begin();

        //persist addressBook
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