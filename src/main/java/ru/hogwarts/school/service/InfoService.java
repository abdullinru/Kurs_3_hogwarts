package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class InfoService {

    @Value("${server.port}")
    private Integer port;

    // method for return number of port
    public Integer getPort() {
        return port;
    }
}
