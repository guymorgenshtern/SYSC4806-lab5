package org.example;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
public class AddressBook implements Serializable {

    private long id;
    @Autowired
    private Map<String, BuddyInfo> buddies;

    /**
     * constructor
     */
    public AddressBook() {
        this.buddies = new HashMap<>();
    }

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * add a buddy to the address book given a BuddyInfo Object
     * @param info - BuddyInfo
     */
    @Autowired
    public void addBuddy(BuddyInfo info) {
        this.buddies.put(info.getName(), info);
    }

    /**
     * Removes a buddy from the address book given a buddyinfo object
     * @param info
     * @return the BuddyInfo removed
     */
    @Autowired
    public BuddyInfo removeBuddy(BuddyInfo info) {
        return this.buddies.remove(info.getName());
    }

    @Autowired
    public BuddyInfo removeBuddyById(Long id) {
        for (BuddyInfo b : this.buddies.values()) {
            if (b.getId() == id) {
                return this.removeBuddy(b);
            }
        }
        return null;
    }

    /**
     * returns a BuddyInfo object related to the name provided
     * @param name
     * @return
     */
    @Autowired
    public BuddyInfo getBuddy(String name) {
        return this.buddies.get(name);
    }

    /**
     * returns hashmap of string BuddyInfo pairs denoting the address book
     * @return
     */
    @Autowired
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Map<String, BuddyInfo> getBuddies() {
        return buddies;
    }
    public void setBuddies(Map<String, BuddyInfo> buddies) {
        this.buddies = buddies;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (BuddyInfo b : this.getBuddies().values()) {
            s.append(b.toString()).append("</br> ");
        }
        return s.toString().isBlank() ? "empty address book" : s.toString();
    }

}
