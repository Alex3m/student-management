package com.student.managment.logic;

import com.student.managment.DomainApplication;
import com.student.managment.domain.Teacher;
import com.student.managment.repository.CourseRepository;
import com.student.managment.repository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE)
class TeacherCreatorTest {
    @Autowired
    TeacherCreator teacherCreator;

    @MockBean
    TeacherRepository repository;
    @MockBean
    CourseRepository courseRepository;
    @MockBean
    DomainApplication domainApplication;

    String testName = "testName";
    String degreeInput = "BSc";

    @Test
    void createNewTeacher() {
        Teacher teacher = new Teacher(testName, degreeInput);
        String expected = "Successfully created: " + teacher.toString();

        when(repository.existsByName(testName))
                .thenReturn(false);
        when(repository.save(teacher))
                .thenReturn(teacher);

        String result = teacherCreator.createNewTeacher(testName, degreeInput);

        verify(repository).existsByName(testName);
        verify(repository).save(teacher);

        assertEquals(expected, result);
    }

    @Test
    void createNewTeacherWithoutName() {
        String expected = "The name must not be empty.";

        String nameInput = "";

        String result = teacherCreator.createNewTeacher(nameInput, degreeInput);

        verifyNoInteractions(repository);
        assertEquals(expected, result);
    }
}