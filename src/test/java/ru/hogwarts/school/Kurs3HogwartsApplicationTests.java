package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.controller.StudentController;

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

        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/11/faculty",  String.class))
                .isNotEmpty();
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/15/faculty", String.class))
                .isNullOrEmpty();

    }

//    public void testPostStudent() throws Exception{
//
//        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/11", String.class))
//                .isNotEmpty();
//    }

}
