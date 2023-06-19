package com.example.demo1;

public class Teacher extends User{
    private String course = "";
    public Teacher(String name, String surname, String username, String password) {
        super(name, surname, username, password);
    }
    public Teacher() {
    }
    public Teacher(String name, String surname){
        super(name, surname);
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
