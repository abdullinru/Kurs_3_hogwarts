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

    public List<String> getLongestNameFaculties() {
        Stream<Faculty> facultyStream = facultyRepository.findAll().stream();
        Integer maxDlina = facultyStream
                .map(Faculty::getName)
                .map(String::length)
                .max(Comparator.naturalOrder())
                .orElse(null);
        if (maxDlina == null) {
            return null;
        }
        Stream<Faculty> facultyStream2 = facultyRepository.findAll().stream();
        List<String> result =  facultyStream2
                .map(Faculty::getName)
                .filter((name) -> name.length() == maxDlina)
                .collect(Collectors.toList());
        return result;

    }

    public Integer sum() {
        return Stream
                .iterate(1, a -> a +1)
                .limit(1_000_000)
                .parallel()
                .reduce(0, Integer::sum);
    }
}
