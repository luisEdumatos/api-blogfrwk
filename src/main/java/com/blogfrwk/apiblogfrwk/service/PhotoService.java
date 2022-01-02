package com.blogfrwk.apiblogfrwk.service;

import com.blogfrwk.apiblogfrwk.dto.mapper.PhotoMapper;
import com.blogfrwk.apiblogfrwk.dto.request.PhotoDTO;
import com.blogfrwk.apiblogfrwk.dto.response.MessageResponse;
import com.blogfrwk.apiblogfrwk.entity.Photo;
import com.blogfrwk.apiblogfrwk.entity.Post;
import com.blogfrwk.apiblogfrwk.exception.PhotoCanNotBeCreatedException;
import com.blogfrwk.apiblogfrwk.exception.PhotoNotFoundException;
import com.blogfrwk.apiblogfrwk.exception.PostNotFoundException;
import com.blogfrwk.apiblogfrwk.repository.PhotoRepository;
import com.blogfrwk.apiblogfrwk.repository.PostRepository;
import com.blogfrwk.apiblogfrwk.security.jwt.JwtUtils;
import com.blogfrwk.apiblogfrwk.security.services.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PhotoService {

    private PhotoRepository photoRepository;
    private PostRepository postRepository;
    private JwtUtils jwtUtils;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private final PhotoMapper photoMapper = PhotoMapper.INSTANCE;

    public MessageResponse createPhoto(PhotoDTO photoDTO) throws PhotoCanNotBeCreatedException, PostNotFoundException {
        Post userOwnerPost = verifyPostExists(photoDTO.getPost().getId());
        if(!isCurrentUserOwnsOfThePost(userOwnerPost)) {
            throw new PhotoCanNotBeCreatedException();
        }
        Photo photoToSave = photoMapper.toModel(photoDTO);
        photoToSave.setCreationDate(sdf.format(new Date()));
        Photo savedPhoto = photoRepository.save(photoToSave);
        return new MessageResponse("Create Photo with ID " + savedPhoto.getId());
    }

    public List<PhotoDTO> listAll() {
        List<Photo> allPhotos = photoRepository.findAll();
        return allPhotos.stream()
                .map(photoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PhotoDTO findByID(Long id) throws PhotoNotFoundException {
        Photo photo = verifyExists(id);
        return photoMapper.toDTO(photo);
    }

    private Photo verifyExists(Long id) throws PhotoNotFoundException {
        Optional<Photo> optionalPhoto = photoRepository.findById(id);
        if (optionalPhoto.isEmpty()) {
            throw new PhotoNotFoundException(id);
        }
        return optionalPhoto.get();
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
}
