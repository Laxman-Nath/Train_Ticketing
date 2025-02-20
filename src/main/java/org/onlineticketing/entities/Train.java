package org.onlineticketing.entities;

import java.util.ArrayList;
import java.util.List;

public class Train {
    private String id;
    private String trainName;
    private List<String> routes=new ArrayList<>();
    private List<List<Boolean>> seats=new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public List<String> getRoutes() {
        return routes;
    }

    public void setRoutes(List<String> routes) {
        this.routes = routes;
    }

    public List<List<Boolean>> getSeats() {
        return seats;
    }

    public void setSeats(List<List<Boolean>> seats) {
        this.seats = seats;
    }

    public Train(String id, String trainName, List<String> routes, List<List<Boolean>> seats) {
        this.id = id;
        this.trainName = trainName;
        this.routes = routes;
        this.seats = seats;
    }
}
