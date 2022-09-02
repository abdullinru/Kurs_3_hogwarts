package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class FacultyService {
    private final Map<Long, Faculty> faculties = new HashMap<>();

    public Faculty creatFaculty(Faculty faculty) {
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty getFacultyById(long id) {
        if (!faculties.containsKey(id)) {
            return null;
        }
        return faculties.get(id);
    }

    public Faculty updateFaculty(Faculty faculty) {
        if (!faculties.containsKey(faculty.getId())) {
            return null;
        }
        return faculties.put(faculty.getId(), faculty);
    }

    public Faculty removeFacultyById(long id) {
        if (!faculties.containsKey(id)) {
            return null;
        }
        return faculties.remove(id);
    }
    public Faculty removeFaculty(Faculty faculty) {
        if (!faculties.containsKey(faculty.getId())) {
            return null;
        }
        return faculties.remove(faculty.getId());
    }

    public Collection<Faculty> getAllFaculties() {
        return faculties.values();
    }

}
