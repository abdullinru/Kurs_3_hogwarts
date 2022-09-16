package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Collections;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty creatFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty getFacultyById(long id) {
        return facultyRepository.findById(id).get();
    }
    public Faculty updateFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void removeFacultyById(long id) {
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    public Faculty findFacultyByName(String name) {
        return facultyRepository.findFirstByNameIgnoreCase(name);
    }
    public Faculty findFacultyByColor(String color) {
        return facultyRepository.findFirstByColorIgnoreCase(color);
    }

    public Collection<Student> getStudentsOfFaculty(Long id) {
        Faculty fa = facultyRepository.findById(id).get();
        if (fa == null) {
            return Collections.emptyList();
        }
        return fa.getStudents();
    }
}
