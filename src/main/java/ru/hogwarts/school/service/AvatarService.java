package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {

    Logger logger = LoggerFactory.getLogger(AvatarService.class);

    // folder for storing students' covers
    @Value("${student.avatar.dir.path}")
    private String avatarsDir;

    private final AvatarRepository avatarRepository;
    private final StudentService studentService;


    // dependency injection with constructor
    public AvatarService(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }

    // Method for upload avatars on server and DB
    public void uploadAvatar(Long id, MultipartFile file) throws IOException {
        logger.info("Was invoked method for upload avatar for student with id: {}", id);
        // get student by id
        Student st = studentService.getStudentById(id);

        // getting a path to a file with a unique name
        Path filePath = Path.of(avatarsDir, id + "." + getExtension(file.getOriginalFilename()));

        // create the necessary directories if they are missing
        Files.createDirectories(filePath.getParent());

        // delete file in folder if it already exists
        Files.deleteIfExists(filePath);

        // creating input and output buffered streams
        try(InputStream is = file.getInputStream();
            OutputStream os =Files.newOutputStream(filePath, CREATE_NEW);
            BufferedInputStream bis = new BufferedInputStream(is, 1024);
            BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        // get avatar by student id
        Avatar avatar = findAvatarByStudentId(id);

        // filling avatar entity fields with setters
        avatar.setStudent(st);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());

        // save avatars in DB
        avatarRepository.save(avatar);
        }

        // method for finding avatar by student id
    public Avatar findAvatarByStudentId(Long id) {
        logger.info("Was invoked method for find avatar by studentId:{}", id);

        // return the found avatar or create a new
        return avatarRepository.findByStudentId(id).orElse(new Avatar());
    }

    // Method for getting extension of file
    private String getExtension(String filename) {
        logger.info("Was invoked method for getting extension of file");
        return filename.substring(filename.lastIndexOf('.') + 1);
    }

    // method foк getting list of avatars using pagination
    public Collection<Avatar> getSpisokOfAvatars(Integer pageNumber, Integer pageSize) {
        logger.info("Was invoked method for getting collection of  avatars on page: {}, with pageSize {}", pageNumber, pageSize);
        PageRequest pageRequest = PageRequest.of(pageNumber-1, pageSize);
        return avatarRepository.findAll(pageRequest).getContent();
    }
}
