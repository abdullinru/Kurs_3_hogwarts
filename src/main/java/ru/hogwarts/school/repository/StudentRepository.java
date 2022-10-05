package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findStudentsByAgeBetween(int age, int age2);
    Collection<Student> findStudentsByAge(int age);

    @Query(value = "select count(*) from student", nativeQuery = true)
    Integer getCountAllStudents();

    @Query(value = "select avg (age) from student", nativeQuery = true)
    Integer getAvgAgeStudents();

    @Query(value = "select * from student order by id desc limit 2", nativeQuery = true)
    Collection<Student> getLate5Students();


}
