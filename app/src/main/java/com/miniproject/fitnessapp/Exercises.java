package com.miniproject.fitnessapp;


import java.io.Serializable;

public class Exercises implements Serializable {
    private String name;
    private int duration;
    private String image;
    private String guidelines;
    private String help;
    private int calories;

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "Exercises{" +
                "name='" + name + '\'' +
                ", duration=" + duration +
                ", image='" + image + '\'' +
                '}';
    }

    public Exercises(String name, int duration, String image, String guidelines, String help, int calories) {
        this.name = name;
        this.duration = duration;
        this.image = image;
        this.guidelines = guidelines;
        this.help = help;
        this.calories = calories;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGuidelines() {
        return guidelines;
    }

    public void setGuidelines(String guidelines) {
        this.guidelines = guidelines;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
