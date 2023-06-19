package com.example.demo1;

import java.util.ArrayList;

public class Courses {
    private String courseName;
    private String amountOfCredits;

    public Courses(String courseName, String amountOfCredits) {
        this.courseName = courseName;
        this.amountOfCredits = amountOfCredits;
    }
    public Courses() {
    }

    public String getCourseName() {
        return courseName;
    }
    public String getAmountOfCredits() {
        return amountOfCredits;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    public void setAmountOfCredits(String amountOfCredits) {
        this.amountOfCredits = amountOfCredits;
    }
}
