package com.student.managment.logic;

import com.student.managment.domain.Course;
import com.student.managment.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class CourseCreator {
    private final CourseRepository repository;
    private final String successfullyCreated;
    private final String nameRemind;
    private final String courseExists;
    private final String incorrectHoursFormat;

    public CourseCreator(CourseRepository repository,
                         @Value("${respond.successfullyCreated}") String successfullyCreated,
                         @Value("${respond.nameRemind}") String nameRemind,
                         @Value("${respond.courseExists}") String courseExists,
                         @Value("${respond.courseExists}") String incorrectHoursFormat) {
        this.repository = repository;
        this.successfullyCreated = successfullyCreated;
        this.nameRemind = nameRemind;
        this.courseExists = courseExists;
        this.incorrectHoursFormat = incorrectHoursFormat;
    }

    /**
     * @param courseName          wished name for the course
     * @param totalHoursAsAString expected course hours (as string for check)
     *                            checks if provided name is blank
     *                            checks is course with the same name already exists
     *                            checks if the hours are correct type
     *                            creates and saves the course if complies with the requirements
     * @return string with answer according to the case
     */
    public String createNewCourse(String courseName, String totalHoursAsAString) {
        Course course = new Course();
        if (courseName.isBlank()) {
            return nameRemind;
        }
        if (repository.existsByName(courseName)) {
            return courseName + courseExists;
        }
        try {
            float totalHours = Float.parseFloat(totalHoursAsAString);
            course.setName(courseName);
            course.setTotalHours(totalHours);
            course.setStudentsAndGrades(new HashMap<>());
        } catch (NumberFormatException n) {
            System.out.println(n.getMessage());
            return incorrectHoursFormat;
        }
        return successfullyCreated + repository.save(course).toString();
    }
}
