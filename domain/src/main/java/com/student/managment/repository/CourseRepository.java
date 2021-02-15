package com.student.managment.repository;

import com.student.managment.domain.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CourseRepository extends MongoRepository<Course, String> {
    boolean existsByName(String courseName);

    Course findByName(String courseName);

    List<Course> findByOrderByNameAcd();
}
