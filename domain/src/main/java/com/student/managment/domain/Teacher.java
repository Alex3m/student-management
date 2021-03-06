package com.student.managment.domain;

import java.util.Objects;

public class Teacher {
    private String id;
    private String name;
    private String degree;

    public Teacher(String name, String degree) {
        this.name = name;
        this.degree = degree;
    }

    public Teacher() {
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

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(id, teacher.id) && Objects.equals(name, teacher.name) && Objects.equals(degree, teacher.degree);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, degree);
    }

    @Override
    public String
    toString() {
        return "Teacher " +
                "id: '" + id + '\'' +
                ", name: '" + name + ", " +
                ", degree: '" + degree + ", " +
                ' ';
    }
}
