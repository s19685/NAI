package com.company;

import java.util.List;

public class Result {

    private double distance;
    private String type;

    public double getDistance() {
        return distance;
    }

    public String getType() {
        return type;
    }

    public Result(double distance, String type) {
        this.distance = distance;
        this.type = type;
    }

    @Override
    public String toString() {
        return  "distance= " + distance +" type= " + type+"\n";
    }


}
