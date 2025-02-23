package org.onlineticketing.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Train {
    private String id;
    private String trainName;
    private List<String> routes=new ArrayList<>();
    private HashMap<Integer,Boolean> seats=new HashMap<>();
    public Train(){
        super();
    }

    @Override
    public String toString() {
        return "Train{" +
                "id='" + id + '\'' +
                ", trainName='" + trainName + '\'' +
                ", routes=" + routes +
                ", seats=" + seats +
                '}';
    }

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

    public HashMap<Integer, Boolean> getSeats() {
        return seats;
    }

    public void setSeats(HashMap<Integer,Boolean> seats) {
        this.seats = seats;
    }

    public Train(String id, String trainName, List<String> routes, HashMap<Integer,Boolean> seats) {
        this.id = id;
        this.trainName = trainName;
        this.routes = routes;
        this.seats = seats;
    }
}
