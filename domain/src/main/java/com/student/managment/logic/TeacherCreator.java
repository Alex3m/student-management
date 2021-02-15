package com.student.managment.logic;

import com.student.managment.domain.Teacher;
import com.student.managment.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TeacherCreator {
    private final TeacherRepository repository;
    private final String teacherExists;
    private final String incorrectDegree;
    private final String degrees;
    private final String successfullyCreated;
    private final String nameReminder;

    public TeacherCreator(TeacherRepository repository,
                          @Value("${respond.teacherExists}") String teacherExists,
                          @Value("${respond.incorrectDegree}") String incorrectDegree,
                          @Value("${degrees}") String degrees,
                          @Value("${respond.successfullyCreated}") String successfullyCreated,
                          @Value("${respond.nameRemind}") String nameReminder) {
        this.repository = repository;
        this.teacherExists = teacherExists;
        this.incorrectDegree = incorrectDegree;
        this.degrees = degrees;
        this.successfullyCreated = successfullyCreated;
        this.nameReminder = nameReminder;
    }

    public String createNewTeacher(String teacherName, String degree) {
        if (teacherName.isBlank()) {
            return nameReminder;
        }
        if (repository.existsByName(teacherName)) {
            return teacherExists;
        }
        if (!degrees.contains(degree)) {
            return incorrectDegree;
        }
        Teacher savedTeacher = repository.save(new Teacher(teacherName, degree));
        return successfullyCreated + savedTeacher.toString();
    }

}

