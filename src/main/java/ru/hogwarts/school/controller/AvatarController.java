package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long id,
                                               @RequestParam MultipartFile avatarFile) throws IOException {
        if (avatarFile.getSize() > 1024 * 500) {
            return ResponseEntity.badRequest().body("File is too BIG");
        }

        avatarService.uploadAvatar(id, avatarFile);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}/avatarFromDb")
    public ResponseEntity<byte[]> dowloadAvatarsFromDB(@PathVariable Long id) {
        Avatar avatar = avatarService.findAvatarByStudentId(id);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        httpHeaders.setContentLength(avatar.getData().length);

        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(avatar.getData());
    }
    @GetMapping("{id}/avatarFromServer")
    public void downloadAvatarFromServer (@PathVariable Long id,
                                          HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.findAvatarByStudentId(id);
        Path path = Path.of(avatar.getFilePath());

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream();
        ) {
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }


}
