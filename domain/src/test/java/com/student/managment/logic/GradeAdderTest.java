package com.student.managment.logic;

import com.student.managment.DomainApplication;
import com.student.managment.domain.Course;
import com.student.managment.repository.CourseRepository;
import com.student.managment.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class GradeAdderTest {
    @Autowired
    GradeAdder gradeAdder;

    @MockBean
    CourseRepository courseRepository;
    @MockBean
    StudentRepository studentRepository;
    @MockBean
    DomainApplication domainApplication;

    String studentInput = "testStudent";
    String courseInput = "testCourse";
    String gradeInput = "6.0";
    Course course = new Course(courseInput, 1f);
    ArrayList<Float> grades = new ArrayList<Float>(List.of(6f));
    HashMap<String, ArrayList<Float>> testStudentsGrades = new HashMap<>();


    @Test
    void addGradeForNonExistingCourse() {
        String expected = courseInput + " course does not exists in our database. Please verify the name and try again.";

        when(courseRepository.existsByName(courseInput))
                .thenReturn(false);

        String result = gradeAdder.addGrade(studentInput, courseInput, gradeInput);

        verify(courseRepository).existsByName(courseInput);
        verifyNoMoreInteractions(courseRepository);
        verifyNoInteractions(studentRepository);

        assertEquals(expected, result);
    }

    @Test
    void addGradeToNonExistingStudent() {
        String expected = studentInput + " is not student in this course";
        when(courseRepository.existsByName(courseInput))
                .thenReturn(true);
        when(studentRepository.existsByName(studentInput))
                .thenReturn(false);

        String result = gradeAdder.addGrade(studentInput, courseInput, gradeInput);

        verify(courseRepository).existsByName(courseInput);
        verify(studentRepository).existsByName(studentInput);
        verifyNoMoreInteractions(studentRepository);
        verifyNoMoreInteractions(courseRepository);

        assertEquals(expected, result);
    }

    @Test
    void addGradeToStudentNotFromTheCourse() {
        String expected = studentInput + " is not student in this course";
        when(courseRepository.existsByName(courseInput))
                .thenReturn(true);
        when(studentRepository.existsByName(studentInput))
                .thenReturn(true);
        when(courseRepository.findByName(courseInput))
                .thenReturn(course);

        String result = gradeAdder.addGrade(studentInput, courseInput, gradeInput);

        verify(courseRepository).existsByName(courseInput);
        verify(studentRepository).existsByName(studentInput);
        verify(courseRepository).findByName(courseInput);
        verifyNoMoreInteractions(courseRepository);
        verifyNoMoreInteractions(studentRepository);
        assertEquals(expected, result);
    }

    @Test
    void addGradeLowerThanMinimum() {
        String lowerGradeInput = "1.99";
        String expected = lowerGradeInput + " is incorrect grade format. The Grade should be number and between 2.0 and 6.0. Please verify the grade and try again.";
        course.getStudentsAndGrades().put(studentInput, grades);

        when(courseRepository.existsByName(courseInput))
                .thenReturn(true);
        when(studentRepository.existsByName(studentInput))
                .thenReturn(true);
        when(courseRepository.findByName(courseInput))
                .thenReturn(course);

        String result = gradeAdder.addGrade(studentInput, courseInput, lowerGradeInput);

        verify(courseRepository).existsByName(courseInput);
        verify(studentRepository).existsByName(studentInput);
        verify(courseRepository).findByName(courseInput);
        verifyNoMoreInteractions(courseRepository);
        verifyNoMoreInteractions(studentRepository);
        assertEquals(expected, result);

    }

    @Test
    void addGradeHigherThanMaximum() {
        String higherGradeInput = "6.01";
        String expected = higherGradeInput + " is incorrect grade format. The Grade should be number and between 2.0 and 6.0. Please verify the grade and try again.";

        course.getStudentsAndGrades().put(studentInput, grades);
        when(courseRepository.existsByName(courseInput))
                .thenReturn(true);
        when(studentRepository.existsByName(studentInput))
                .thenReturn(true);
        when(courseRepository.findByName(courseInput))
                .thenReturn(course);

        String result = gradeAdder.addGrade(studentInput, courseInput, higherGradeInput);

        verify(courseRepository).existsByName(courseInput);
        verify(studentRepository).existsByName(studentInput);
        verify(courseRepository).findByName(courseInput);
        verifyNoMoreInteractions(courseRepository);
        verifyNoMoreInteractions(studentRepository);
        assertEquals(expected, result);

    }

    @Test
    void addGradeNaN() {
        String notANumberInput = "A";
        String expected = notANumberInput + " is incorrect grade format. The Grade should be number and between 2.0 and 6.0. Please verify the grade and try again.";

        course.getStudentsAndGrades().put(studentInput, grades);
        when(courseRepository.existsByName(courseInput))
                .thenReturn(true);
        when(studentRepository.existsByName(studentInput))
                .thenReturn(true);
        when(courseRepository.findByName(courseInput))
                .thenReturn(course);

        String result = gradeAdder.addGrade(studentInput, courseInput, notANumberInput);

        verify(courseRepository).existsByName(courseInput);
        verify(studentRepository).existsByName(studentInput);
        verify(courseRepository).findByName(courseInput);
        verifyNoMoreInteractions(courseRepository);
        verifyNoMoreInteractions(studentRepository);
        assertEquals(expected, result);
    }

    @Test
    void addGrade() {
        String expected = gradeInput + " grade successfully added to: " + studentInput + " in " + courseInput;

        course.getStudentsAndGrades().put(studentInput, grades);
        when(courseRepository.existsByName(courseInput))
                .thenReturn(true);
        when(studentRepository.existsByName(studentInput))
                .thenReturn(true);
        when(courseRepository.findByName(courseInput))
                .thenReturn(course);
        when(courseRepository.save(course))
                .thenReturn(course);
        String result = gradeAdder.addGrade(studentInput, courseInput, gradeInput);

        verify(courseRepository).existsByName(courseInput);
        verify(studentRepository).existsByName(studentInput);
        verify(courseRepository).findByName(courseInput);
        verify(courseRepository).save(course);
        verifyNoMoreInteractions(courseRepository);
        verifyNoMoreInteractions(studentRepository);
        assertEquals(expected, result);

    }

}