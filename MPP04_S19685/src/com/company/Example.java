package com.company;

import java.util.Arrays;

public class Example {

    private double[] point;
    private int centroind;

    public Example(double[] point) {
        this.point = point;
        centroind = -1;
        System.out.println(this.toString());
    }

    public void setCentroind(int centroind) {
        this.centroind = centroind;
    }

    @Override
    public String toString() {
        return Arrays.toString(point) + " centroind = " + centroind;
    }
}
