package com.company;


public class Training {


    private Double[] arguments;
    private boolean output;

    public Training(Double[] arguments, boolean output) {
        this.arguments = arguments;
        this.output = output;
    }

    public void show() {
        for (Double d : arguments) System.out.print(d + " ");
        System.out.println(output);
    }
}
