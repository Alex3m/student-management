package com.student.managment.logic;

import com.student.managment.domain.Course;
import com.student.managment.domain.Student;
import com.student.managment.repository.CourseRepository;
import com.student.managment.repository.StudentRepository;
import com.student.managment.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CourseUpdater {
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final CourseTeacherChecker checker;
    private final String nonExistingTeacher;
    private final String nonExistingStudent;
    private final String nonExistingCourse;
    private final String alreadyTeaching;
    private final String alreadyStudent;
    private final String singedUp;
    private final String successfullyUpdated;
    private final String updateCancelled;


    public CourseUpdater(CourseRepository courseRepository, TeacherRepository teacherRepository,
                         StudentRepository studentRepository, CourseTeacherChecker checker,
                         @Value("${respond.nonExistingTeacher}") String nonExistingTeacher,
                         @Value("${respond.nonExistingStudent}") String nonExistingStudent,
                         @Value("${respond.nonExistingCourse}") String nonExistingCourse,
                         @Value("${respond.alreadyTeaching}") String alreadyTeaching,
                         @Value("${respond.alreadyStudent}") String alreadyStudent,
                         @Value("${respond.singedUp}") String singedUp,
                         @Value("${respond.successfullyUpdated}") String successfullyUpdated,
                         @Value("${respond.updateCancelled}") String updateCancelled) {
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.checker = checker;
        this.nonExistingTeacher = nonExistingTeacher;
        this.nonExistingStudent = nonExistingStudent;
        this.nonExistingCourse = nonExistingCourse;
        this.alreadyTeaching = alreadyTeaching;
        this.alreadyStudent = alreadyStudent;
        this.singedUp = singedUp;


        this.successfullyUpdated = successfullyUpdated;
        this.updateCancelled = updateCancelled;
    }

    public String addTeacherToCourse(String teacherName, String courseName) {
        if (!teacherRepository.existsByName(teacherName)) {
            return teacherName + nonExistingTeacher;
        }
        if (!courseRepository.existsByName(courseName)) {
            return courseName + nonExistingCourse;
        }
        Course course = courseRepository.findByName(courseName);
        if (course.getTeacherName().equalsIgnoreCase(teacherName)) {
            return teacherName + alreadyTeaching;
        }
        if (course.getTeacherName().isBlank()) {
            course.setTeacherName(teacherName);
            courseRepository.save(course);
            return successfullyUpdated;
        }
        if (checker.isSettingNewTeacher(courseName, teacherName)) {
            course.setTeacherName(teacherName);
            return successfullyUpdated + courseRepository.save(course).toString();
        } else {
            return updateCancelled;
        }
    }

    public String addStudentToCourse(String studentName, String courseName) {
        if (!studentRepository.existsByName(studentName)) {
            return studentName + nonExistingStudent;
        }
        if (!courseRepository.existsByName(courseName)) {
            return courseName + nonExistingCourse;
        }
        Course currentCourse = courseRepository.findByName(courseName);
        if (currentCourse.getStudentsAndGrades().containsKey(studentName)) {
            return studentName + alreadyStudent;
        }
        saveUpdatedStudentAndCourseInDB(studentName, courseName, currentCourse);

        return studentName + singedUp + courseName;
    }

    private void saveUpdatedStudentAndCourseInDB(String studentName, String courseName, Course currentCourse) {
        Student currentStudent = studentRepository.findByName(studentName);
        currentStudent.getCourses().add(courseName);
        studentRepository.save(currentStudent);
        currentCourse.getStudentsAndGrades().put(studentName, new ArrayList<>());
        courseRepository.save(currentCourse);
    }
}
