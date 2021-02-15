package com.student.managment.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Course {
    private String id;
    private String name;
    private Float totalHours;
    private String teacherName;
    private HashMap<String, ArrayList<Float>> studentsAndGrades;

    public Course(String name, Float totalHours) {
        this.name = name;
        this.totalHours = totalHours;
        this.studentsAndGrades = new HashMap<>();
    }

    public Course(String name, Float totalHours, HashMap<String, ArrayList<Float>> studentsAndGrades) {
        this.name = name;
        this.totalHours = totalHours;
        this.studentsAndGrades = studentsAndGrades;
    }

    public Course() {
    }

    public HashMap<String, ArrayList<Float>> getStudentsAndGrades() {
        return studentsAndGrades;
    }

    public void setStudentsAndGrades(HashMap<String, ArrayList<Float>> studentsAndGrades) {
        this.studentsAndGrades = studentsAndGrades;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id) && Objects.equals(name, course.name) && Objects.equals(totalHours, course.totalHours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, totalHours);
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

    public Float getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(Float totalHours) {
        this.totalHours = totalHours;
    }

    @Override
    public String toString() {
        return "Course " +
                ", name: " + name + ", " +
                ", total hours: " + totalHours +
                ", teacher:'" + teacherName + ", " +
                ", students: " + studentsAndGrades.keySet() +
                ' ';
    }
}
