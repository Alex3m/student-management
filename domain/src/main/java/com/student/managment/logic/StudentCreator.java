package com.student.managment.logic;

import com.student.managment.domain.Student;
import com.student.managment.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.TreeSet;

@Service
public class StudentCreator {
    private final StudentRepository studentRepository;
    private final String studentExists;
    private final String incorrectAge;
    private final String nameRemind;

    public StudentCreator(StudentRepository studentRepository,
                          @Value("${respond.studentExists}") String studentExists,
                          @Value("${respond.incorrectAge}") String incorrectAge,
                          @Value("${respond.nameRemind}") String nameRemind) {
        this.studentRepository = studentRepository;
        this.studentExists = studentExists;
        this.incorrectAge = incorrectAge;
        this.nameRemind = nameRemind;
    }

    /**
     * @param studentName the name of the new student
     * @param ageAsString the age as a string, for internal check
     *                    method verify the input data and saves the user (if studentName is already in the DB and ensures the age is an integer
     * @return string respond according to the case;
     */
    public String createNewStudent(String studentName, String ageAsString) {
        if (studentName.isBlank()) {
            return nameRemind;
        }
        if (studentRepository.existsByName(studentName)) {
            return studentName + studentExists;
        }
        try {
            int age = Integer.parseInt(ageAsString);
            Student savedStudent = studentRepository.save(new Student(studentName, age));
            savedStudent.setCourses(new TreeSet<>());
            return savedStudent.toString();
        } catch (NumberFormatException n) {
            System.out.println(n.getMessage());
            return incorrectAge;
        }

    }
}
