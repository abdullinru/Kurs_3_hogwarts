package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> creatStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.creatStudent(student));
    }
    @GetMapping({"id"})
    public ResponseEntity<Student> findStudentById(@PathVariable Long id) {
        Student st = studentService.getStudentById(id);
        if (st == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(st);
    }
    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student updateStudent = studentService.updateStudent(student);
        if (updateStudent == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updateStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> removeStudent(@PathVariable Long id) {
        Student removeStudent = studentService.removeStudentById(id);
        if (removeStudent == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(removeStudent);
    }
    @GetMapping("{age}")
    public ResponseEntity<List<Student>> studentsByAge(@PathVariable Integer age) {
        List<Student> collect = studentService.getAllStudents().stream()
                .filter(e -> e.getAge() == age)
                .collect(Collectors.toList());
        if (collect.isEmpty()) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(collect);
    }
    @GetMapping
    public ResponseEntity<Collection<Student>> allStudents() {
        Collection<Student> collect = studentService.getAllStudents();
        if (collect.isEmpty()) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(collect);
    }
}
