package org.example;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
public class AddressBook implements Serializable {

    private long id;
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
    public void addBuddy(BuddyInfo info) {
        this.buddies.put(info.getName(), info);
    }

    /**
     * Removes a buddy from the address book given a buddyinfo object
     * @param info
     * @return the BuddyInfo removed
     */
    public BuddyInfo removeBuddy(BuddyInfo info) {
        return this.buddies.remove(info.getName());
    }

    /**
     * returns a BuddyInfo object related to the name provided
     * @param name
     * @return
     */
    public BuddyInfo getBuddy(String name) {
        return this.buddies.get(name);
    }

    /**
     * returns hashmap of string BuddyInfo pairs denoting the address book
     * @return
     */
    @OneToMany(cascade = CascadeType.ALL)
    public Map<String, BuddyInfo> getBuddies() {
        return buddies;
    }

    public void setBuddies(Map<String, BuddyInfo> buddies) {
        this.buddies = buddies;
    }

}
