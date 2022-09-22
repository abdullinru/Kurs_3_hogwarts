package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {
    @Value("${student.avatar.dir.path}")
    private String avatarsDir;

    private final AvatarRepository avatarRepository;
    private final StudentService studentService;


    public AvatarService(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }

    public void uploadAvatar(Long id, MultipartFile file) throws IOException {
        Student st = studentService.getStudentById(id);

        Path filePath = Path.of(avatarsDir, id + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        
        try(InputStream is = file.getInputStream();
            OutputStream os =Files.newOutputStream(filePath, CREATE_NEW);
            BufferedInputStream bis = new BufferedInputStream(is, 1024);
            BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = findAvatarByStudentId(id);
        avatar.setStudent(st);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());

        avatarRepository.save(avatar);
        }

    public Avatar findAvatarByStudentId(Long id) {
        return avatarRepository.findByStudentId(id).orElse(new Avatar());
    }
    private String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf('.') + 1);
    }
}
