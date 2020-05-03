package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Example {

    private double[] point;
    private int centroindIndex;

    public Example(double[] point) {
        this.point = point;
        centroindIndex = -1;
        System.out.print(this.toString());
    }

    public int getCentroindIndex() { return centroindIndex; }

    public double[] getPoints() { return point; }

    public void setCentroindIndex(int centroindIndex) {
        this.centroindIndex = centroindIndex;
    }

    public int nearestCentroid(List<double[]> centroids) {
        List<Double> distances = new ArrayList<>();
        for (double[] dbls : centroids ) { distances.add(countDistance(dbls)); }
        return distances.indexOf(Collections.min(distances));
    }

    public Double countDistance(double[] point) {
        double distance = 0.0;
        for (int i = 0; i < point.length; i++) { distance += Math.pow(this.point[i] - point[i], 2); }
        return Math.sqrt(distance);
    }

    @Override
    public String toString() { return "\n"+Arrays.toString(point) + " centroindIndex = " + centroindIndex ; }
}
