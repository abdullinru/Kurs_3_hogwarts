package ru.hogwarts.school.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StudentService {

    Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    public Collection<Student> getAllStudents() {
        logger.info("Was invoked method for getting  collection of all students");
        return studentRepository.findAll();
    }

    public Student creatStudent(Student student) {
        logger.info("Was invoked method for create  student with body {}", student);
        return studentRepository.save(student);
    }
    public Student getStudentById(long id) {
        logger.info("Was invoked method for getting  student by id: {}", id);
        return studentRepository.findById(id).orElse(null);
    }
    public Student updateStudent(Student student) {
        logger.info("Was invoked method for update student with body {}", student);
        return studentRepository.save(student);
    }
    public void removeStudentById(long id) {
        logger.info("Was invoked method for remove student by id: {}", id);
        studentRepository.findById(id).ifPresent(st -> studentRepository.deleteById(id));
    }
    public Collection<Student> findStudentsByAgeBetweenMinMax(int min, int max) {
        logger.info("Was invoked method for find collection of student with age ({} - {})", min, max);
        return studentRepository.findStudentsByAgeBetween(min, max);
    }
    public Collection<Student> findStudentsByAge(int age) {
        logger.info("Was invoked method for find colection of students by age: {}", age);
        return studentRepository.findStudentsByAge(age);
    }
    public Faculty getFacultyOfStudent(Long id) {
        logger.info("Was invoked method for getting faculty by studentId: {}", id);
        Student st = studentRepository.findById(id).orElse(null);
        if (st == null) {
            logger.warn("There are no students with id: {}", id);
            return null;
        }
        return st.getFaculty();
    }

    public Integer getCountAllStudents() {
        logger.info("Was invoked method for get count all students ");
        return studentRepository.getCountAllStudents();
    }
    public Integer getAvgAgeStudents() {
        logger.info("Was invoked method for get average age of students");
        return studentRepository.getAvgAgeStudents();
    }
    public Collection<Student> getLate5Students() {
        logger.info("Was invoked method for get 5 late students with max id");
        return studentRepository.getLate5Students();
    }

    public List<String> getStudentsNameStartsWithA() {
        Stream<Student> streamStudent = studentRepository.findAll().stream();
        List<String> result = streamStudent
                .map(Student::getName)
                .filter(name -> name.startsWith("R"))
                .sorted(String::compareTo)
                .map(String::toLowerCase)
                .map(StringUtils::capitalize)
                .collect(Collectors.toList());
        return result;
    }
    public double getAvgAgeStudentStream() {
        Stream<Student> streamStudent = studentRepository.findAll().stream();
        Optional<Double> result = Optional.of(streamStudent
                .mapToInt(Student::getAge)
                .average()
                .getAsDouble());
        return result.orElse(null);
    }

}
