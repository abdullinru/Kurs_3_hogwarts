package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FacultyService {

    Logger logger = LoggerFactory.getLogger(FacultyService.class);
    private final FacultyRepository facultyRepository;

    // dependency injection with constructor
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    // method for creating new faculty
    public Faculty creatFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty with body {}", faculty);
        return facultyRepository.save(faculty);
    }

    // Method for getting faculty by id
    public Faculty getFacultyById(long id) {
        logger.info("Was invoked method for getting faculty by id: {}", id);
        // if faculty is not found by id then return null
        return facultyRepository.findById(id).orElse(null);
    }

    // method for updating faculty
    public Faculty updateFaculty(Faculty faculty) {
        logger.info("Was invoked method for update faculty with body {}", faculty);
        return facultyRepository.save(faculty);
    }

    // Method for removing faculty by id
    public void removeFacultyById(long id) {
        logger.info("Was invoked method for remove faculty with id {}", id);
        // remove faculty if faculty is found
        facultyRepository.findById(id).ifPresent(fa -> facultyRepository.deleteById(id));
    }

    // Method returns list of all faculties
    public Collection<Faculty> getAllFaculties() {
        logger.info("Was invoked method for getting all faculties");
        return facultyRepository.findAll();
    }

    // Method for getting all faculties by color
    public Collection<Faculty> findFacultyByColor(String color) {
        logger.info("Was invoked method for find faculties by color: {}", color);
        return facultyRepository.findByColorIgnoreCase(color);
    }

    // Method for getting all faculties by color or name
    public Collection<Faculty> findFacultiesByNameOrColor(String nameOrColor) {
        logger.info("Was invoked method for find faculties by color or name: {}", nameOrColor);
        return facultyRepository.findFacultyByNameIgnoreCaseOrColorIgnoreCase(nameOrColor, nameOrColor);
    }

    // Method for getting all students of faculty
    public Collection<Student> getStudentsOfFaculty(Long id) {
        logger.info("Was invoked method for getting collection of students of faculty by id {}", id);
        // finding faculty by id
        Faculty fa = facultyRepository.findById(id).orElse(null);

        // if faculty is not found then return emptyList
        if (fa == null) {
            logger.warn("method getStudentsOfFaculty: collection is empty");
            return Collections.emptyList();
        }
        // getting list of students
        return fa.getStudents();
    }

    // method for getting Faculties with longest name
    public List<String> getLongestNameFaculties() {
        Stream<Faculty> facultyStream = facultyRepository.findAll().stream();
        // getting the maximum length of the faculty name
        Integer maxDlina = facultyStream
                .map(Faculty::getName)
                .map(String::length)
                .max(Comparator.naturalOrder())
                .orElse(null);
        // if maxDlina is not found returns null
        if (maxDlina == null) {
            return null;
        }
        // getting list of faculties by MaxDlina
        Stream<Faculty> facultyStream2 = facultyRepository.findAll().stream();
        List<String> result =  facultyStream2
                .map(Faculty::getName)
                .filter((name) -> name.length() == maxDlina)
                .collect(Collectors.toList());
        return result;

    }

    /*
    tutorial method to calculate sum of numbers from 1 to 1_000_00s using parallel stream
     */
    public Long sum() {
        Long reduce = Stream
                .iterate(1L, a -> a + 1)
                .limit(1_000_000L)
                .parallel()
                .reduce(0L, Long::sum);
        return reduce;
    }
}
