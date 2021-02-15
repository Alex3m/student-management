package com.student.managment.logic;

import com.student.managment.DomainApplication;
import com.student.managment.domain.Student;
import com.student.managment.repository.CourseRepository;
import com.student.managment.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE)
class StudentCreatorTest {
    @Autowired
    StudentCreator studentCreator;
    @MockBean
    StudentRepository repository;
    @MockBean
    CourseRepository courseRepository;
    @MockBean
    DomainApplication domainApplication;

    String nameInput = "testStudent";
    int ageInput = 1;
    String ageAsString = String.valueOf(ageInput);

    @Test
    void createNewStudentWithCorrectInfo() {

        Student student = new Student(nameInput, ageInput);
        when(repository.existsByName(nameInput))
                .thenReturn(false);
        when(repository.save(student))
                .thenReturn(student);
        studentCreator.createNewStudent(nameInput, String.valueOf(ageInput));
        verify(repository).existsByName(nameInput);
        verify(repository).save(student);
    }

    @Test
    void createNewStudentWithExistingUser() {
        String expected = nameInput +" as a student, already exists in our database. Please verify the name and try again.";

        when(repository.existsByName(nameInput))
                .thenReturn(true);

        String result = studentCreator.createNewStudent(nameInput, ageAsString);
        verify(repository).existsByName(nameInput);
        verifyNoMoreInteractions(repository);
        assertEquals(expected, result);
    }

    @Test
    void createNewUserWithIncorrectAge() {
        String ageAsAString = "a";
        String expected = "Age must be a number. Please verify the age and try again";
        when(repository.existsByName(nameInput))
                .thenReturn(false);
        String result = studentCreator.createNewStudent(nameInput, ageAsAString);

        verify(repository).existsByName(nameInput);
        verifyNoMoreInteractions(repository);
        assertEquals(expected, result);

    }
}