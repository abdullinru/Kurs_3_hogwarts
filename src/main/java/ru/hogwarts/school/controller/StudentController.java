package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @PostMapping
    public ResponseEntity<Student> creatStudent(@RequestBody Student student) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.creatStudent(student));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Student> findStudentById(@PathVariable Long id) {
        Student st = studentService.getStudentById(id);
        if (st == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(st);
    }
    @GetMapping
    public ResponseEntity<Collection<Student>> studentsByAge(@RequestParam Integer age) {
        Collection<Student> collect = studentService.findStudentsByAge(age);
        if (collect.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(collect);
    }
    @GetMapping("/all")
    public ResponseEntity<Collection<Student>> allStudents() {
        Collection<Student> collect = studentService.getAllStudents();
        if (collect.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(collect);
    }
    @GetMapping("/age")
    public ResponseEntity<Collection<Student>> findStudentsByAge(@RequestParam Integer min,
                                                 @RequestParam  Integer max) {
        Collection<Student> studentCollection = studentService.findStudentsByAgeBetweenMinMax(min, max);
        if (studentCollection.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentCollection);
    }

    @GetMapping("/{id}/faculty")
    public ResponseEntity<Faculty> getFacultyByStudentId(@PathVariable Long id) {
        Faculty fa = studentService.getFacultyOfStudent(id);
        if (fa == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(fa);
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getCountAllStudents() {
        Integer count = studentService.getCountAllStudents();
        return ResponseEntity.ok(count);
    }
    @GetMapping("/avgAge")
    public ResponseEntity<Integer> getAvgAgeStudents() {
        Integer avgAge = studentService.getAvgAgeStudents();
        return ResponseEntity.ok(avgAge);
    }
    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student updateStudent = studentService.updateStudent(student);
        if (updateStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updateStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> removeStudent(@PathVariable Long id) {
        studentService.removeStudentById(id);
        return ResponseEntity.ok().build();
    }

}
