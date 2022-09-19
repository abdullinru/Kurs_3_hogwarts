package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Kurs3HogwartsApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception{
        Assertions.assertThat(studentController).isNotNull();
    }
    @Test
    public void testGetStudents() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/11", String.class))
                .isNotEmpty();
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/1", String.class))
                .isNullOrEmpty();

        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student?age=33",  String.class))
                .isNotEmpty();
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/?age=39", String.class))
                .isNullOrEmpty();

        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/age?min=30&max=40",  String.class))
                .isNotEmpty();
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/age?min=50&max=50", String.class))
                .isNullOrEmpty();

        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/all",  String.class))
                .isNotEmpty();

        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/10/faculty",  String.class))
                .isNotEmpty();
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/15/faculty", String.class))
                .isNullOrEmpty();
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/100/faculty", String.class))
                .isNullOrEmpty();

    }
    @Test
    public void testPostStudent() throws Exception{
        Student studentTest = new Student(11, "Ruslan", 33);

        Assertions.assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student/",studentTest, String.class))
                .isNotEmpty();

    }
//    @Test
//    public void testPutStudent() throws Exception{
//        Student studentTest = new Student(11, "Ruslan Abdullin", 33);
//        Student studentTest2 = new Student(1, "Abdullin", 33);
//
//        Assertions.assertThat(this.restTemplate.execute("http://localhost:" + port + "/student/", HttpMethod.PUT, studentTest, String.class))
//                .isNotNull();
//    }
//@Test
//public void testDeleteStudent() throws Exception{
//    Student studentTest = new Student();
//    studentTest.setName("Saule");
//    studentTest.setAge(30);
//
//    Assertions.assertThat(this.restTemplate.delete("http://localhost:" + port + "/student/30", studentTest, String.class));
//}

}
