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
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<Faculty> creatFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.creatFaculty(faculty));
    }

    @GetMapping({"id"})
    public ResponseEntity<Faculty> findFacultyById(@PathVariable Long id) {
        Faculty findFaculty = facultyService.getFacultyById(id);
        if (findFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(findFaculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> updateStudent(@RequestBody Faculty faculty) {
        Faculty updateFaculty = facultyService.updateFaculty(faculty);
        if (updateFaculty == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updateFaculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> removeFaculty(@PathVariable Long id) {
        Faculty removeFaculty = facultyService.removeFacultyById(id);
        if (removeFaculty == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(removeFaculty);
    }
    @GetMapping("{color}")
    public ResponseEntity<List<Faculty>> facultiesByAge(@PathVariable String color) {
        List<Faculty> collect = facultyService.getAllFaculties().stream()
                .filter(e -> e.getColor().equals(color))
                .collect(Collectors.toList());
        if (collect.isEmpty()) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(collect);
    }
    @GetMapping
    public ResponseEntity<Collection<Faculty>> allFaculties() {
        Collection<Faculty> collect = facultyService.getAllFaculties();
        if (collect.isEmpty()) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(collect);
    }
}
