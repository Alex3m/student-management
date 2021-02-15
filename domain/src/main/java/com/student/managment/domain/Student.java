package com.student.managment.domain;

import java.util.Objects;
import java.util.TreeSet;

public class Student {
    private String id;
    private String name;
    private int age;
    private TreeSet<String> courses;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }


    public TreeSet<String> getCourses() {
        return courses;
    }

    public void setCourses(TreeSet<String> courses) {
        this.courses = courses;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return age == student.age && Objects.equals(id, student.id) && Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age);
    }

    @Override
    public String toString() {
        return "Student " +
                "name: '" + name + ", " +
                ", age: " + age +
                ' ';
    }

}
