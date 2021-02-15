package com.student.managment.logic;

import com.student.managment.domain.Course;
import com.student.managment.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseReviewer {
    private final CourseRepository repository;
    private final String courseAverageGrade;

    public CourseReviewer(CourseRepository repository,
                          @Value("${respond.courseAverageGrade}") String courseAverageGrade) {
        this.repository = repository;
        this.courseAverageGrade = courseAverageGrade;
    }

    public List<Course> showAllCourses() {
        return repository.findAll();
    }

    public TreeMap<String, LinkedHashMap<String, Float>> showCoursesOrderByNameThanGrades() {
        List<Course> byOrderByNameAcd = repository.findByOrderByNameAcd();
        TreeMap<String, LinkedHashMap<String, Float>> coursesAndStudents = new TreeMap<>();
        Map<String, Float> studentsAndGradesForTheCourse = new HashMap<>();

        for (Course course : byOrderByNameAcd) {
            HashMap<String, ArrayList<Float>> studentsAndGrades = course.getStudentsAndGrades();
            for (String student : studentsAndGrades.keySet()) {
                float averageGrade = getAverageGrade(studentsAndGrades, student);
                studentsAndGradesForTheCourse.put(student, averageGrade);
            }
            LinkedHashMap<String, Float> orderedStudentsAndGrades = orderStudentsByAverageGrade(studentsAndGradesForTheCourse);
            coursesAndStudents.put(course.getName(), orderedStudentsAndGrades);

        }
        return coursesAndStudents;
    }

    public String showAverageGradeInCourse(String courseName) {
        HashMap<String, ArrayList<Float>> studentsAndGrades = repository.findByName(courseName).getStudentsAndGrades();
        ArrayList<Float> averageGrades = new ArrayList<>();
        for (String student : studentsAndGrades.keySet()) {
            Float totalGrade = studentsAndGrades.get(student).stream()
                    .reduce(0f, Float::sum);
            int gradesCount = studentsAndGrades.get(student).size();
            averageGrades.add(totalGrade / gradesCount);
        }
        Float average = averageGrades.stream()
                .reduce(0f, Float::sum);
        return courseName + courseAverageGrade + average;
    }

    private float getAverageGrade(HashMap<String, ArrayList<Float>> studentsAndGrades, String student) {
        float totalGrade = studentsAndGrades.get(student).stream()
                .reduce(0f, Float::sum);
        int gradesCount = studentsAndGrades.get(student).size();
        return totalGrade / gradesCount;
    }

    private LinkedHashMap<String, Float> orderStudentsByAverageGrade(Map<String, Float> studentsAndGradesForTheCourse) {
        return studentsAndGradesForTheCourse.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

}
