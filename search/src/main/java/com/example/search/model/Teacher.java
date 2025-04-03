package com.example.search.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Teacher {

    private int id;
    private String name;
    @JsonProperty("students")
    private List<Student> students;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
