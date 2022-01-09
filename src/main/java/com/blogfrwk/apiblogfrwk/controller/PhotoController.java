package com.blogfrwk.apiblogfrwk.controller;

import com.blogfrwk.apiblogfrwk.dto.request.PhotoDTO;
import com.blogfrwk.apiblogfrwk.dto.response.MessageResponse;
import com.blogfrwk.apiblogfrwk.exception.PhotoCanNotBeCreatedException;
import com.blogfrwk.apiblogfrwk.exception.PhotoCanNotBeUploadedException;
import com.blogfrwk.apiblogfrwk.exception.PostNotFoundException;
import com.blogfrwk.apiblogfrwk.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @PostMapping(
            value = "/uploadFile",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public MessageResponse uploadPhoto(@RequestPart("photo") PhotoDTO photo, @RequestPart("file") MultipartFile file) throws PhotoCanNotBeCreatedException, PostNotFoundException, PhotoCanNotBeUploadedException {
        return this.photoService.uploadPhoto(photo, file);
    }

    @PostMapping("/deleteFile")
    public String deleteFile(@RequestBody String fileURL) {
        return this.photoService.deleteFileFromBucket(fileURL);
    }
}