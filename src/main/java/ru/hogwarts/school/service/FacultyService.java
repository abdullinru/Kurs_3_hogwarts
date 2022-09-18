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
        return facultyRepository.findById(id).orElse(null);
    }
    public Faculty updateFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void removeFacultyById(long id) {
        facultyRepository.findById(id).ifPresent(fa -> facultyRepository.deleteById(id));
    }

    public Collection<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    public Collection<Faculty> findFacultyByName(String name) {
        return facultyRepository.findByNameIgnoreCase(name);
    }
    public Collection<Faculty> findFacultyByColor(String color) {
        return facultyRepository.findByColorIgnoreCase(color);
    }

    public Collection<Faculty> findFacultiesByNameOrColor(String nameOrColor) {
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(nameOrColor, nameOrColor);
    }

    public Collection<Student> getStudentsOfFaculty(Long id) {
        Faculty fa = facultyRepository.findById(id).orElse(null);
        if (fa == null) {
            return Collections.emptyList();
        }
        return fa.getStudents();
    }
}
