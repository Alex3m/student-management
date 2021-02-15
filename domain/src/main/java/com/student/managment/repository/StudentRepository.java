package com.student.managment.repository;

import com.student.managment.domain.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepository extends MongoRepository<Student, String> {
    boolean existsByName(String studentName);

    Student findByName(String studentName);
}
