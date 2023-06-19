package com.example.demo1;

public class Student extends User{

    public Student(String name, String surname, String username, String password) {
        super(name, surname, username, password);
    }
    public Student(){
    }
    public Student(String name, String surname) {
        super(name, surname);
    }

}
