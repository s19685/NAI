package com.company;

import java.util.List;

public class Plant {

    List<Double> arguments;
    private String type;

    public Plant(int leafLength, int leafWidth, int flakeLength, int flakeWidth, String type) {
        this.leafLength = leafLength;
        this.leafWidth = leafWidth;
        this.flakeLength = flakeLength;
        this.flakeWidth = flakeWidth;
        this.type = type;
    }


}
