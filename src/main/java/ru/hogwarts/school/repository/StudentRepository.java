package ru.hogwarts.school.repository;

import org.springframework.boot.logging.java.JavaLoggingSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findStudentsByAgeBetween(int age, int age2);

    Collection<Student> findStudentsByAge(int age);

}
