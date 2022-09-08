package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import java.util.Collection;
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
}
