package com.student.managment.repository;

import com.student.managment.domain.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeacherRepository extends MongoRepository<Teacher, String> {
    boolean existsByName(String teacherName);

    boolean findByName(String teacherName);

}
