package org.onlineticketing.entities;

import java.util.ArrayList;
import java.util.List;

public class Ticket {
    private String id;
    private Train train;
    private Double perSeatPrice;
    private Double totalPrice;
    private Integer totalSeats;
    private List<List<Integer>> seats=new ArrayList<>();

    public Ticket(String id, Train train, Double perSeatPrice, Double totalPrice, Integer totalSeats, List<List<Integer>> seats) {
        this.id = id;
        this.train = train;
        this.perSeatPrice = perSeatPrice;
        this.totalPrice = totalPrice;
        this.totalSeats = totalSeats;
        this.seats = seats;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public Double getPerSeatPrice() {
        return perSeatPrice;
    }

    public void setPerSeatPrice(Double perSeatPrice) {
        this.perSeatPrice = perSeatPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public List<List<Integer>> getSeats() {
        return seats;
    }

    public void setSeats(List<List<Integer>> seats) {
        this.seats = seats;
    }
}
