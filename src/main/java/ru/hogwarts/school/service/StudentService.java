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

    // dependency injection with constructor
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Method for getting  collection of all students
    public Collection<Student> getAllStudents() {
        logger.info("Was invoked method for getting  collection of all students");
        return studentRepository.findAll();
    }

    // method for cteating new student
    public Student creatStudent(Student student) {
        logger.info("Was invoked method for create  student with body {}", student);
        return studentRepository.save(student);
    }

    // method for getting  student by id
    public Student getStudentById(long id) {
        logger.info("Was invoked method for getting  student by id: {}", id);
        return studentRepository.findById(id).orElse(null);
    }

    // method for update student
    public Student updateStudent(Student student) {
        logger.info("Was invoked method for update student with body {}", student);
        return studentRepository.save(student);
    }
    // method for remove student by id
    public void removeStudentById(long id) {
        logger.info("Was invoked method for remove student by id: {}", id);
        studentRepository.findById(id).ifPresent(st -> studentRepository.deleteById(id));
    }
    // method for finding collection of student with age between min and max age
    public Collection<Student> findStudentsByAgeBetweenMinMax(int min, int max) {
        logger.info("Was invoked method for find collection of student with age ({} - {})", min, max);
        return studentRepository.findStudentsByAgeBetween(min, max);
    }

    // method for finding collection of student by age
    public Collection<Student> findStudentsByAge(int age) {
        logger.info("Was invoked method for find colection of students by age: {}", age);
        return studentRepository.findStudentsByAge(age);
    }

    // method for getting faculty by studentId
    public Faculty getFacultyOfStudent(Long id) {
        logger.info("Was invoked method for getting faculty by studentId: {}", id);
        Student st = studentRepository.findById(id).orElse(null);
        // if student is not found by id then return null
        if (st == null) {
            logger.warn("There are no students with id: {}", id);
            return null;
        }
        // return faculty of student
        return st.getFaculty();
    }

    // method for get count all students
    public Integer getCountAllStudents() {
        logger.info("Was invoked method for get count all students ");
        return studentRepository.getCountAllStudents();
    }
    // method for get average age of students
    public Integer getAvgAgeStudents() {
        logger.info("Was invoked method for get average age of students");
        return studentRepository.getAvgAgeStudents();
    }

    // method for get 5 late students with max id
    public Collection<Student> getLate5Students() {
        logger.info("Was invoked method for get 5 late students with max id");
        return studentRepository.getLate5Students();
    }

    // Method for getting list of students with names begining letter R
    public List<String> getStudentsNameStartsWithA() {
        Stream<Student> streamStudent = studentRepository.findAll().stream();
        List<String> result = streamStudent
                .map(Student::getName)                  // get names
                .filter(name -> name.startsWith("R"))   // names starts with R
                .sorted(String::compareTo)              // nature sort names
                .map(String::toLowerCase)               // converting all characters to lowercase
                .map(StringUtils::capitalize)           // capitalize the first letter of the name
                .collect(Collectors.toList());          // collect to list
        return result;
    }

    // Method for getting avarage age of students
    public double getAvgAgeStudentStream() {
        Stream<Student> streamStudent = studentRepository.findAll().stream();
        Optional<Double> result = Optional.of(streamStudent
                .mapToInt(Student::getAge)
                .average()
                .getAsDouble());
        return result.orElse(null);
    }

    // Method for working with Threads
    public void getNameStudentsThread() {
        // Create stream
        Stream<Student> streamStudent = studentRepository.findAll().stream();

        // Getting list of student's names
        List<String> studentsName = streamStudent
                .map(a -> a.getName())
                .collect(Collectors.toList());

        System.out.println(studentsName);
        System.out.println("////////////////////////////////");

        System.out.println(studentsName.get(0));
        System.out.println(studentsName.get(1));

        // Create new thread
        new Thread(() ->
        {
            System.out.println(studentsName.get(2));
            System.out.println(studentsName.get(3));
        }).start();

        // Create new thread
        new Thread(() ->
        {
            System.out.println(studentsName.get(4));
            System.out.println(studentsName.get(5));
        }).start();
    }

    // Method for working with Synchronized Threads
    public void getNameStudentsThreadSynch() {
        // Create stream
        Stream<Student> streamStudent = studentRepository.findAll().stream();

        // Getting list of student's names
        List<String> studentsName = streamStudent
                .map(a -> a.getName())
                .collect(Collectors.toList());

        System.out.println(studentsName);
        System.out.println("////////////////////////////////");

        printStudent(studentsName.get(0));
        printStudent(studentsName.get(1));

        // Create new thread
        new Thread(() ->
        {
            printStudent(studentsName.get(2));
            printStudent(studentsName.get(3));
        }).start();

        // Create new thread
        new Thread(() ->
        {
            printStudent(studentsName.get(4));
            printStudent(studentsName.get(5));
        }).start();
    }
    // Synchronized method for print students names in console
    private synchronized void printStudent(String name) {
        System.out.println(name);
    }
}
