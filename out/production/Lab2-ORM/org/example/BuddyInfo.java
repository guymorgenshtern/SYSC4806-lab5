package org.example;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class BuddyInfo implements Serializable {

    private long id;
    private String name;
    private String phoneNumber;
    private String address;

    public BuddyInfo() {
    }
    public BuddyInfo(String name, String phoneNumber, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
    @Id
    @GeneratedValue
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * getter for name
     * @return String - name
     */
    public String getName() {
        return name;
    }

    /**
     * getter for phone number
     * @return - String - phone Number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * getter for address
     * @return String - address
     */
    public String getAddress() {
        return address;
    }

    /**
     * override of toString method to display info better
     * @return String representation of BuddyInfo
     */
    @Override
    public String toString() {
        return String.format("---%s---\nID: %d \nPhone #: %s \nAddress: %s",
                this.getName(), this.getId(), this.getPhoneNumber(), this.getAddress());
    }

    /**
     * set Name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * set Phone Number
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * set address
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }
}
