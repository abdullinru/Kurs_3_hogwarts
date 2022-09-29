package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Collections;

@Service
public class FacultyService {

    Logger logger = LoggerFactory.getLogger("FacultyService.class");
    private final FacultyRepository facultyRepository;
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty creatFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty with body {}", faculty);
        return facultyRepository.save(faculty);
    }

    public Faculty getFacultyById(long id) {
        logger.info("Was invoked method for getting faculty by id: {}", id);
        return facultyRepository.findById(id).orElse(null);
    }
    public Faculty updateFaculty(Faculty faculty) {
        logger.info("Was invoked method for update faculty with body {}", faculty);
        return facultyRepository.save(faculty);
    }

    public void removeFacultyById(long id) {
        logger.info("Was invoked method for remove faculty with id {}", id);
        facultyRepository.findById(id).ifPresent(fa -> facultyRepository.deleteById(id));
    }

    public Collection<Faculty> getAllFaculties() {
        logger.info("Was invoked method for getting all faculties");
        return facultyRepository.findAll();
    }

//    public Collection<Faculty> findFacultyByName(String name) {
//        return facultyRepository.findByNameIgnoreCase(name);
//    }
    public Collection<Faculty> findFacultyByColor(String color) {
        logger.info("Was invoked method for find faculties by color: {}", color);
        return facultyRepository.findByColorIgnoreCase(color);
    }

    public Collection<Faculty> findFacultiesByNameOrColor(String nameOrColor) {
        logger.info("Was invoked method for find faculties by color or name: {}", nameOrColor);
        return facultyRepository.findFacultyByNameIgnoreCaseOrColorIgnoreCase(nameOrColor, nameOrColor);
    }

    public Collection<Student> getStudentsOfFaculty(Long id) {
        logger.info("Was invoked method for getting collection of students of faculty by id {}", id);
        Faculty fa = facultyRepository.findById(id).orElse(null);
        if (fa == null) {
            logger.warn("method getStudentsOfFaculty: collection is empty");
            return Collections.emptyList();
        }
        return fa.getStudents();
    }
}
