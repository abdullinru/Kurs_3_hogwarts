package ru.hogwarts.school.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock
    private StudentRepository studentRepositoryMock;
    @InjectMocks
    StudentService out;

    Student ruslan;
    Student dastan;
    List<Student> students;

    public void init() {
        ruslan = new Student(1, "Ruslan", 33);
        dastan = new Student(2, "Dastan", 3);
        students.add(ruslan);
        students.add(dastan);
    }
    @Test
    public void creatPositiveTest() {
        Mockito.when(studentRepositoryMock.save(ruslan)).thenReturn(ruslan);
        Student actual = out.creatStudent(ruslan);
        Assertions.assertThat(ruslan).isEqualTo(actual);
    }
    @Test
    public void editPositiveTest() {
        Mockito.when(studentRepositoryMock.save(ruslan)).thenReturn(ruslan);
        Student actual = out.updateStudent(ruslan);
        Assertions.assertThat(ruslan).isEqualTo(actual);
    }
//    @Test
//    public void getByIdPositiveTest() {
//        Mockito.when(studentRepositoryMock.findById(1L).get()).thenReturn(ruslan);
//        Student actual = out.getStudentById(1);
//        Assertions.assertThat(ruslan).isEqualTo(actual);
//    }
    @Test
    public void getAllPositiveTest() {
        Mockito.when(studentRepositoryMock.findAll()).thenReturn(students);
        Collection<Student> actual = out.getAllStudents();
        Assertions.assertThat(students).isEqualTo(actual);
    }
//    @Test
//    public void removePositiveTest() {
//        Assertions.assertThat(ruslan).isEqualTo(actual);
//    }


}