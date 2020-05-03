package com.company;

public class Iris {

    private double[] points;
    private String name;

    public Iris(double[] points, String name) {
        this.points = points;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double[] getPoints() {
        return points;
    }
}
