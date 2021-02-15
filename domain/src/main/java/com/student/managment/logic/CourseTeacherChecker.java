package com.student.managment.logic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class CourseTeacherChecker {
    private final String differentTeacher;
    private final String yesNo;
    private final Scanner scanner;

    public CourseTeacherChecker(@Value("${question.differentTeacher}") String differentTeacher,
                                @Value("${question.yesNo}") String yesNo, Scanner scanner) {

        this.differentTeacher = differentTeacher;
        this.yesNo = yesNo;
        this.scanner = scanner;
    }

    public boolean isSettingNewTeacher(String courseName, String teacherName) {
        System.out.println(courseName + differentTeacher + teacherName + yesNo);
        String answer = scanner.nextLine();
        while (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no")) {
            System.out.println(yesNo);
            answer = scanner.nextLine();
        }
        return answer.equalsIgnoreCase("yes");
    }
}
