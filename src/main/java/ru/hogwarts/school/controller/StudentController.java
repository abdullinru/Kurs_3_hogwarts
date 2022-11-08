package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;

/*
controller for processing http requests to the student entity
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    // dependency injection with constructor
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    //method for create new student
    @PostMapping
    public ResponseEntity<Student> creatStudent(@RequestBody Student student) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.creatStudent(student));
    }

    // method for getting student by id
    @GetMapping("/{id}")
    public ResponseEntity<Student> findStudentById(@PathVariable Long id) {
        Student st = studentService.getStudentById(id);

        // if student not found return 404
        if (st == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(st);
    }

    // method for getting list of students by age
    @GetMapping
    public ResponseEntity<Collection<Student>> studentsByAge(@RequestParam Integer age) {
        Collection<Student> collect = studentService.findStudentsByAge(age);
        // if students by parametr not found return 404
        if (collect.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(collect);
    }
    // Method returns list of all students
    @GetMapping("/all")
    public ResponseEntity<Collection<Student>> allStudents() {
        Collection<Student> collect = studentService.getAllStudents();

        // if list of students is empty return 404
        if (collect.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(collect);
    }

    // Metod returns list oа students by age between min and max age
    @GetMapping("/age")
    public ResponseEntity<Collection<Student>> findStudentsByAge(@RequestParam Integer min,
                                                 @RequestParam  Integer max) {
        Collection<Student> studentCollection = studentService.findStudentsByAgeBetweenMinMax(min, max);
        // if list of students is empty return 404
        if (studentCollection.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentCollection);
    }

    // Method returns faculty by student id
    @GetMapping("/{id}/faculty")
    public ResponseEntity<Faculty> getFacultyByStudentId(@PathVariable Long id) {
        Faculty fa = studentService.getFacultyOfStudent(id);

        // if faculty not found return 404
        if (fa == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(fa);
    }

    // method return count all students
    @GetMapping("/count")
    public ResponseEntity<Integer> getCountAllStudents() {
        Integer count = studentService.getCountAllStudents();
        return ResponseEntity.ok(count);
    }
    // method return average age of all students
    @GetMapping("/avgAge")
    public ResponseEntity<Integer> getAvgAgeStudents() {
        Integer avgAge = studentService.getAvgAgeStudents();
        return ResponseEntity.ok(avgAge);
    }
    // method return list of late students
    @GetMapping("/Late5")
    public ResponseEntity<Collection<Student>> getLate5Students() {
        Collection<Student> students = studentService.getLate5Students();
        return ResponseEntity.ok(students);
    }
    // method return list of students wtis name starting whis letter A
    @GetMapping("/names/startsWithA")
    public ResponseEntity<List<String>> getStudentsNameStartsWithA() {
        List<String> result = studentService.getStudentsNameStartsWithA();
        return ResponseEntity.ok(result);
    }
    // method return average age of all students with using stream
    @GetMapping("/avgAgeStream")
    public ResponseEntity<Double> getAvgAgeStudentStream() {
        Double avgAge = studentService.getAvgAgeStudentStream();
        //if students not found return 404
        if (avgAge == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(avgAge);
    }
    // method for working with threads
    @GetMapping("/nameThread")
    public void getNameStudentsThread() {
        studentService.getNameStudentsThread();
    }

    // method for working with synchronized threads
    @GetMapping("/nameThreadSynch")
    public void getNameStudentsThreadSynch() {
        studentService.getNameStudentsThreadSynch();
    }

    //method for update student
    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student updateStudent = studentService.updateStudent(student);
        if (updateStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updateStudent);
    }

    // method for remove student by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Student> removeStudent(@PathVariable Long id) {
        studentService.removeStudentById(id);
        return ResponseEntity.ok().build();
    }

}
