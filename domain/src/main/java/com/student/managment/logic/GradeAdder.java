package com.student.managment.logic;

import com.student.managment.domain.Course;
import com.student.managment.repository.CourseRepository;
import com.student.managment.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Adds grades for specific student to specific course
 */
@Service
public class GradeAdder {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final String nonExistingCourse;
    private final String notStudentAtCourse;
    private final String incorrectGrade;
    private final String gradeSuccessfullyAdded;

    public GradeAdder(CourseRepository courseRepository,
                      StudentRepository studentRepository, @Value("${respond.nonExistingCourse}") String nonExistingCourse,
                      @Value("${respond.notStudentAtCourse}") String notStudentAtCourse,
                      @Value("${respond.incorrectGrade}") String incorrectGrade,
                      @Value("${respond.gradeSuccessfullyAdded}") String gradeSuccessfullyAdded) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.nonExistingCourse = nonExistingCourse;
        this.notStudentAtCourse = notStudentAtCourse;
        this.incorrectGrade = incorrectGrade;
        this.gradeSuccessfullyAdded = gradeSuccessfullyAdded;
    }

    /**
     * @param studentName   of the student for whom we will add grade
     * @param courseName    of the course for which is the grade
     * @param gradeAsString the grade as a string for check
     *                      checks if the course and the student are existing in db
     *                      and verify the student is a part of the course
     *                      validate the grade
     * @return string answer according to the case
     */
    public String addGrade(String studentName, String courseName, String gradeAsString) {

        if (!courseRepository.existsByName(courseName)) {
            return courseName + nonExistingCourse;
        }
        if (!studentRepository.existsByName(studentName)) {
            return studentName + notStudentAtCourse;
        }
        Course course = courseRepository.findByName(courseName);
        boolean doesStudentParticipateInTheCourse = course.getStudentsAndGrades().containsKey(studentName);
        float grade = 0;
        if (!doesStudentParticipateInTheCourse) {
            return studentName + notStudentAtCourse;
        }
        try {
            grade = Float.parseFloat(gradeAsString);
            if (grade < 2.0 || grade > 6.0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException n) {
            System.out.println(n.getMessage());
            return gradeAsString + incorrectGrade;
        }
        course.getStudentsAndGrades().get(studentName).add(grade);
        Course savedCourseGrade = courseRepository.save(course);
        ArrayList<Float> floats = savedCourseGrade.getStudentsAndGrades().get(studentName);

        return floats.get(floats.size() - 1) + gradeSuccessfullyAdded + studentName + " in " + courseName;
    }
}
