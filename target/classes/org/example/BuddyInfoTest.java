package org.example;

import org.junit.Before;
import org.junit.Test;

import jakarta.persistence.*;

import java.util.List;

import static org.junit.Assert.*;

public class BuddyInfoTest {

    private BuddyInfo testingBuddy;
    private final String BUDDY_NAME = "test";
    private final String BUDDY_NUM = "1";
    private final String BUDDY_ADDRESS = "avenue road";

    /**
     * setup for each test, creates a testing buddy
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        testingBuddy = new BuddyInfo(BUDDY_NAME, BUDDY_NUM, BUDDY_ADDRESS);
    }


    /**
     * tests getName by checking equality of name with constant defined above
     */
    @Test
    public void getName() {
        assertEquals(BUDDY_NAME, testingBuddy.getName());
    }

    /**
     * tests getPhoneNumber by checking equality of phone number with constant defined above
     */
    @Test
    public void getPhoneNumber() {
        assertEquals(BUDDY_NUM, testingBuddy.getPhoneNumber());
    }

    /**
     * tests getAddress by checking equality of address with constant defined above
     */
    @Test
    public void getAddress() {
        assertEquals(BUDDY_ADDRESS, testingBuddy.getAddress());
    }

    @Test
    public void persistenceTest() {
        // Creating objects representing some products
        BuddyInfo buddyOne = new BuddyInfo();
        buddyOne.setName("Guy");
        buddyOne.setAddress("1 test drive");
        buddyOne.setPhoneNumber("1");

        BuddyInfo buddyTwo = new BuddyInfo();
        buddyOne.setName("Frank");
        buddyOne.setAddress("2 test drive");
        buddyOne.setPhoneNumber("2");

        // Connecting to the database through EntityManagerFactory
        // connection details loaded from persistence.xml
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab2ORM");

        //creating entity manager
        EntityManager em = emf.createEntityManager();

        // Creating a new transaction
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        //persist and commit buddies
        em.persist(buddyOne);
        em.persist(buddyTwo);

        tx.commit();

        // Querying the contents of the database using JPQL query
        Query q = em.createQuery("SELECT b FROM BuddyInfo b");

        @SuppressWarnings("unchecked")
        List<BuddyInfo> results = q.getResultList();

        System.out.println("List of products\n----------------");

        for (BuddyInfo b : results) {

            System.out.println(b.getName() + " (id=" + b.getName() + ")");
        }

        // Closing connection
        em.close();

        emf.close();
    }
}
