package com.blogfrwk.apiblogfrwk.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.blogfrwk.apiblogfrwk.dto.request.PhotoDTO;
import com.blogfrwk.apiblogfrwk.dto.response.MessageResponse;
import com.blogfrwk.apiblogfrwk.entity.Photo;
import com.blogfrwk.apiblogfrwk.entity.Post;
import com.blogfrwk.apiblogfrwk.exception.PhotoCanNotBeCreatedException;
import com.blogfrwk.apiblogfrwk.exception.PhotoCanNotBeUploadedException;
import com.blogfrwk.apiblogfrwk.exception.PostNotFoundException;
import com.blogfrwk.apiblogfrwk.repository.PhotoRepository;
import com.blogfrwk.apiblogfrwk.repository.PostRepository;
import com.blogfrwk.apiblogfrwk.security.jwt.JwtUtils;
import com.blogfrwk.apiblogfrwk.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class PhotoService {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private JwtUtils jwtUtils;

    private AmazonS3 amazonS3;

    @Value("${endpointUrl}")
    private String endpointUrl;
    @Value("${bucketName}")
    private String bucketName;
    @Value("${region}")
    private String region;
    @Value("${accessKey}")
    private String accessKey;
    @Value("${secretKey}")
    private String secretKey;

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.amazonS3 = new AmazonS3Client(credentials);
    }

    public MessageResponse uploadPhoto(PhotoDTO photo, MultipartFile multipartFile) throws PostNotFoundException, PhotoCanNotBeCreatedException, PhotoCanNotBeUploadedException {
        Post userOwnerPost = verifyPostExists(photo.getPost().getId());
        if(!isCurrentUserOwnsOfThePost(userOwnerPost)) {
            throw new PhotoCanNotBeCreatedException();
        }
        Photo photoToSave = new Photo();
        Photo savedPhoto = new Photo();
        photoToSave.setPost(photo.getPost());
        photoToSave.setCreationDate(sdf.format(new Date()));
        String fileURL = "";
        String fileName = "";
        try {
            File file = convertMultipartFileToFile(multipartFile);
            fileName = multipartFile.getOriginalFilename();
            fileURL = endpointUrl + "/" + bucketName + "/" + fileName;
            uploadFileToBucket(fileName, file);
            file.delete();
        } catch (Exception e) {
            throw new PhotoCanNotBeUploadedException();
        }

        photoToSave.setPhotoPath("https://" + bucketName + ".s3." + region + ".amazonaws.com/" + fileName);
        savedPhoto = photoRepository.save(photoToSave);

        return new MessageResponse("Create Photo with ID " + savedPhoto.getId());
    }

    private Post verifyPostExists(Long id) throws PostNotFoundException {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isEmpty()) {
            throw new PostNotFoundException(id);
        }
        return optionalPost.get();
    }

    private String getUserCurrentSection() {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    public boolean isCurrentUserOwnsOfThePost(Post post) {
        String userCurrentSection = getUserCurrentSection();
        if (post.getOwnerName() != null && post.getOwnerName().equals(userCurrentSection)) {
            return true;
        }
        return false;
    }

    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

    private void uploadFileToBucket(String fileName, File file) {
        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public String deleteFileFromBucket(String fileName) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucketName, fileName));
        return "Deletion Successful";
    }
}
