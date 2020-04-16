package com.company;

import java.io.File;

public class Preceptron {
    private String name;


    public Preceptron(String name){
        this.name = name;
        learn(new File("data/"+name));
    }

    private void learn(File directory) {
        for (File file : directory.listFiles() ) studyFile(file);
    }


    private void studyFile(File file) {


    }

    public boolean check(String test) {
        return true;
    }


    @Override
    public String toString() {
        return "Preceptron{"+ name + "}";
    }

    public String getName() {
        return name;
    }


}
