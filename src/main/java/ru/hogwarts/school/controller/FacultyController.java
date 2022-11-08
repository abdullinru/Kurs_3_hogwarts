package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;

/*
controller for handling requests to the faculty entity
 */
@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    // dependency injection with constructor
    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    // method for create new faculty
    @PostMapping
    public ResponseEntity<Faculty> creatFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.creatFaculty(faculty));
    }

    // method for getting faculty by id
    @GetMapping("/{id}")
    public ResponseEntity<Faculty> findFacultyById(@PathVariable Long id) {
        Faculty findFaculty = facultyService.getFacultyById(id);

        // if faculty not found, then 404
        if (findFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(findFaculty);
    }

    // method returns a list of faculties by color
    @GetMapping
    public ResponseEntity<Collection<Faculty>> facultiesByColor(@RequestParam String color) {
        Collection<Faculty> facultyCollection = facultyService.findFacultyByColor(color);
        return ResponseEntity.ok(facultyCollection);
    }

    // method returns a list of faculties by color or name
    @GetMapping("/find/{nameOrColor}")
    public ResponseEntity<Collection<Faculty>> findfacultyByNameOrColor(@PathVariable String nameOrColor) {
        Collection<Faculty> facultyCollection = facultyService.findFacultiesByNameOrColor(nameOrColor);
        return ResponseEntity.ok(facultyCollection);
    }

    // method returns list students by faculty's id
    @GetMapping("/{id}/students")
    public ResponseEntity<Collection<Student>> getAllStudentsOfFaculty(@PathVariable Long id) {
        Collection<Student> students = facultyService.getStudentsOfFaculty(id);

        //if students not found then 404
        if (students.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(students);
    }

    // method return list all faculties
    @GetMapping("/all")
    public ResponseEntity<Collection<Faculty>> allFaculties() {
        Collection<Faculty> collect = facultyService.getAllFaculties();

        // if list of faculties if empty, then 404
        if (collect.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(collect);
    }

    // method returns faculties whis longest name
    @GetMapping("/name/maxLength")
    public ResponseEntity<List<String>> getLongestNameFaculties() {
        List<String> result = facultyService.getLongestNameFaculties();

        // if faculties not found, then 404
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    //method for calculate sum
    @GetMapping("/sum")
    public ResponseEntity<Long> sum() {
        Long sum = facultyService.sum();
        return ResponseEntity.ok(sum);
    }

    // method for update faculty
    @PutMapping
    public ResponseEntity<Faculty> updateStudent(@RequestBody Faculty faculty) {
        Faculty updateFaculty = facultyService.updateFaculty(faculty);
        if (updateFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updateFaculty);
    }

    // method for remove faculty by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Faculty> removeFaculty(@PathVariable Long id) {
        facultyService.removeFacultyById(id);
        return ResponseEntity.ok().build();
    }
}
