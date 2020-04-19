package com.company;

public class Training {


    private int[] arguments;
    private boolean output;

    public Training(int[] arguments, boolean output) {
        this.arguments = arguments;
        this.output = output;
    }

    public void show() {
        for (int i : arguments) System.out.print(i + " ");
        System.out.println(output);
    }

    public int[] getArguments() {
        return arguments;
    }

    public boolean getOutput() {
        return output;
    }
}
