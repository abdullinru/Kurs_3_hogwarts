package ru.hogwarts.school.controller;

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
import java.util.Collection;

/*
controller for processing http requests for downloading and receiving a student cover
 */
@RestController
@RequestMapping("/avatar")
public class AvatarController {
    private final AvatarService avatarService;

    /*
    dependency injection with constructor
     */
    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    /*
    method to upload student cover to server and db
     */
    @PostMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long id,
                                               @RequestParam MultipartFile avatarFile) throws IOException {
        /*
        file size should be no more than 500 kb
         */
        if (avatarFile.getSize() > 1024 * 500) {
            return ResponseEntity.badRequest().body("File is too BIG");
        }

        avatarService.uploadAvatar(id, avatarFile);
        return ResponseEntity.ok().build();
    }

    /*
    method to get student cover from db
     */
    @GetMapping("{id}/FromDb")
    public ResponseEntity<byte[]> dowloadAvatarsFromDB(@PathVariable Long id) {
        //getting a cover by student ID
        Avatar avatar = avatarService.findAvatarByStudentId(id);

        //filling http headers
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        httpHeaders.setContentLength(avatar.getData().length);

        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(avatar.getData());
    }

    /*
    method to get student cover from server
     */
    @GetMapping("{id}/FromServer")
    public void downloadAvatarFromServer (@PathVariable Long id,
                                          HttpServletResponse response) throws IOException {
        //getting a cover by student ID
        Avatar avatar = avatarService.findAvatarByStudentId(id);

        // saving file path to path variable
        Path path = Path.of(avatar.getFilePath());

        // creating input and output data stream
        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream();
        ) {

            //filling http headers
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());

            // data transfer
            is.transferTo(os);
        }
    }
    /*
        method to get cover list of all students using pagination
    */
    @GetMapping("/all")
    public ResponseEntity<Collection<Avatar>> getSpisokOfAvatars(@RequestParam("page") Integer pageNumber,
                                                                @RequestParam("size") Integer pageSize) {
        Collection<Avatar> avatars = avatarService.getSpisokOfAvatars(pageNumber, pageSize);
        return ResponseEntity.ok(avatars);
    }


}
