package com.blogfrwk.apiblogfrwk.service;


import com.blogfrwk.apiblogfrwk.dto.mapper.PostMapper;
import com.blogfrwk.apiblogfrwk.dto.request.PostDTO;
import com.blogfrwk.apiblogfrwk.dto.response.MessageResponse;
import com.blogfrwk.apiblogfrwk.entity.Post;
import com.blogfrwk.apiblogfrwk.exception.PostNotFoundException;
import com.blogfrwk.apiblogfrwk.repository.PostRepository;
import com.blogfrwk.apiblogfrwk.security.jwt.JwtUtils;
import com.blogfrwk.apiblogfrwk.security.services.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PostService {
    private PostRepository postRepository;
    private JwtUtils jwtUtils;

    private final PostMapper postMapper = PostMapper.INSTANCE;

    public MessageResponse createPost(PostDTO postDTO) {
        Post postToSave = postMapper.toModel(postDTO);
        postToSave.setOwnerName(getPostOwnerName());
        Post savedPost = postRepository.save(postToSave);
        return new MessageResponse("Created Post with ID " + savedPost.getId());
    }

    private String getPostOwnerName() {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    public MessageResponse deleteById(Long id) throws PostNotFoundException {
        verifyExists(id);
        postRepository.deleteById(id);
        return new MessageResponse("Post with ID " + id + " has been deleted successfully");
    }

    private Post verifyExists(Long id) throws PostNotFoundException {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isEmpty()) {
            throw new PostNotFoundException(id);
        }
        return optionalPost.get();
    }
}
