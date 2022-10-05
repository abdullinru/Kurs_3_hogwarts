package ru.hogwarts.school;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Kurs3HogwartsApplicationTests {

    @LocalServerPort
    private int port;

    @InjectMocks
    private StudentController studentController;
    @Autowired
    private TestRestTemplate restTemplate;
//    @Autowired
//    private StudentService studentService;
    @MockBean
    private StudentRepository studentRepository;

    private static final ObjectMapper mapper = new ObjectMapper();


    @BeforeEach
    void init() {
        Student student = new Student(222, "rus", 33);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
    }

    @Test
    void contextLoads() throws Exception{
        Assertions.assertThat(studentController).isNotNull();

    }

    @Test
    public void testGetStudentInfo()  throws Exception {
        String expectid = "{\"id\":222,\"name\":\"rus\",\"age\":33}";
        String expectid2 = "{id:222,name:\"rus\",age:33}";
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/student/11", String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        Assertions.assertThat(response.getBody()).isEqualTo(expectid);
        JSONAssert.assertEquals(expectid2, response.getBody(), false);
    }
    @Test
    public void testPostStudent() throws Exception {
        Student student = new Student(222, "rus", 33);
        String expectid = "{\"id\":222,\"name\":\"rus\",\"age\":33}";

        HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<String> response = restTemplate.postForEntity("/student", student, String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(response.getBody()).isEqualTo(expectid);

    }
    @Test
    public void testDeleteStudent() throws Exception {

        HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<String> response = restTemplate.exchange("/student/1", HttpMethod.DELETE, entity, String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }
    @Test
    public void testEditStudent() throws Exception {
        Student student = new Student(333, "das", 3);

        HttpEntity<Student> entity = new HttpEntity<>(student);
        ResponseEntity<Student> response = restTemplate.exchange("/student/", HttpMethod.PUT, entity, Student.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }


}
