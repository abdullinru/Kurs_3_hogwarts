package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<Faculty> creatFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.creatFaculty(faculty));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> findFacultyById(@PathVariable Long id) {
        Faculty findFaculty = facultyService.getFacultyById(id);
        if (findFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(findFaculty);
    }
    @GetMapping
    public ResponseEntity<Collection<Faculty>> facultiesByColor(@RequestParam String color) {
        Collection<Faculty> fa = facultyService.findFacultyByColor(color);
        return ResponseEntity.ok(fa);
    }
    @GetMapping("/find")
    public ResponseEntity<Collection<Faculty>> findfacultyByNameOrColor(@RequestParam String nameOrcolor) {
        Collection<Faculty> fa = facultyService.findFacultiesByNameOrColor(nameOrcolor);
        return ResponseEntity.ok(fa);
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<Collection<Student>> getAllStudentsOfFaculty(@PathVariable Long id) {
        Collection<Student> students = facultyService.getStudentsOfFaculty(id);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/all")
    public ResponseEntity<Collection<Faculty>> allFaculties() {
        Collection<Faculty> collect = facultyService.getAllFaculties();
        if (collect.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(collect);
    }

    @PutMapping
    public ResponseEntity<Faculty> updateStudent(@RequestBody Faculty faculty) {
        Faculty updateFaculty = facultyService.updateFaculty(faculty);
        if (updateFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updateFaculty);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Faculty> removeFaculty(@PathVariable Long id) {
        facultyService.removeFacultyById(id);
        return ResponseEntity.ok().build();
    }
}
