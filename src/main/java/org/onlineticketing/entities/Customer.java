package org.onlineticketing.entities;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String id;
    private String name;
    private String address;
    private List<Ticket> bookedTickets=new ArrayList<>();
    private String password;
    private String hashedPassword;
    Boolean isLoggedIn;
public Customer(){}
    public Customer(String id, String name, String address, List<Ticket> bookedTickets, String password, String hashedPassword) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.bookedTickets = bookedTickets;
        this.password = password;
        this.hashedPassword = hashedPassword;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Ticket> getBookedTickets() {
        return bookedTickets;
    }

    public Boolean getLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public void setBookedTickets(List<Ticket> bookedTickets) {
        this.bookedTickets = bookedTickets;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
}
