package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import java.util.Collection;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student creatStudent(Student student) {
        return studentRepository.save(student);
    }
    public Student getStudentById(long id) {

        return studentRepository.findById(id).get();
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }
    public void removeStudentById(long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> findStudentsByAgeBetweenMinMax(int min, int max) {
        return studentRepository.findStudentsByAgeBetween(min, max);
    }

    public Faculty getFacultyOfStudent(Long id) {
        Student st = studentRepository.findById(id).get();
        if (st != null) {
            return st.getFaculty();
        }
        return null;
    }

}
