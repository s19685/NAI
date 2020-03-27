package com.company;

import java.util.List;

public class Training<T> {


    private List<T> arguments;
    private int output;

    public Training(List<T> arguments, int output) {
        this.arguments = arguments;
        this.output = output;
    }
}
