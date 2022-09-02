package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
@Service
public class StudentService {
    private final Map<Long, Student> students = new HashMap<>();
    private long idStudent = 1;

    public Collection<Student> getAllStudents() {
        return students.values();
    }

    public Student creatStudent(Student student) {
        student.setId(idStudent++);
        students.put(student.getId(), student);
        return student;
    }

    public Student getStudentById(long id) {
        return students.get(id);
    }

    public Student updateStudent(Student student) {
        if (!students.containsKey(student.getId())) {
            return null;
        }
        return students.put(student.getId(), student);
    }

    public Student removeStudentById(long id) {
        return students.remove(id);
    }
    public Student removeStudent(Student student) {
        return students.remove(student.getId());
    }
}
