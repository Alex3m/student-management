package com.student.managment.logic;

import com.student.managment.domain.Course;
import com.student.managment.domain.Student;
import com.student.managment.repository.CourseRepository;
import com.student.managment.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;


@Service
public class StudentAverageGradeCalculator {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final String nonExistingStudent;
    private final String studentAverageGradeOfCourse;
    private final String studentNoCourses;

    public StudentAverageGradeCalculator(StudentRepository studentRepository, CourseRepository courseRepository,
                                         @Value("${respond.nonExistingStudent}") String nonExistingStudent,
                                         @Value("${respond.studentAverageGradeOfCourse}") String studentAverageGradeOfCourse,
                                         @Value("${respond.studentNoCourses}") String studentNoCourses) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.nonExistingStudent = nonExistingStudent;
        this.studentAverageGradeOfCourse = studentAverageGradeOfCourse;
        this.studentNoCourses = studentNoCourses;
    }


    /**
     * @param studentName of the student for whom we want the average grade;
     *                    checks if the name is actually of a student
     *                    checks if participate and in which courses
     *                    checks if there are grades in those courses
     * @return short string answer according to the situation
     */
    public String calculate(String studentName) {
        if (!studentRepository.existsByName(studentName)) {
            return studentName + nonExistingStudent;
        }
        Student currentStudent = studentRepository.findByName(studentName);
        if (currentStudent.getCourses().size() == 0) {
            return studentName + studentNoCourses;
        }
        return studentName + studentAverageGradeOfCourse + getAvgGradeForParticipatedCourses(studentName);

    }

    /**
     * @param studentName of the student for whom we are looking
     *                    run through all of its courses, filters the one with grades
     *                    calculate average for each course
     * @return return the average from all
     */
    private float getAvgGradeForParticipatedCourses(String studentName) {
        Student currentStudent = studentRepository.findByName(studentName);
        return currentStudent.getCourses().stream()
                .map(courseRepository::findByName)
                .filter(course -> hasGrades(studentName, course))
                .map(course -> getAverageGradeForOneCourse(course.getStudentsAndGrades(), studentName))
                .reduce(0f, Float::sum);
    }

    /**
     * @param studentName of the student for whom we are looking
     * @param course      current course
     * @return if he has grades for this course
     */
    private boolean hasGrades(String studentName, Course course) {
        return course.getStudentsAndGrades().get(studentName).size() != 0;
    }

    /**
     * @param studentsAndGrades of the current course
     * @param studentName       of the student for whom we are looking
     * @return average grade for the current course
     */
    private float getAverageGradeForOneCourse(HashMap<String, ArrayList<Float>> studentsAndGrades, String studentName) {
        float totalGrade = studentsAndGrades.get(studentName).stream()
                .reduce(0f, Float::sum);
        int gradesCount = studentsAndGrades.get(studentName).size();
        return totalGrade / gradesCount;
    }
}
