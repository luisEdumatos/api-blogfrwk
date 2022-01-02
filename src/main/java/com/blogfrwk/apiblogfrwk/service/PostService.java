package com.blogfrwk.apiblogfrwk.service;

import com.blogfrwk.apiblogfrwk.dto.mapper.PostMapper;
import com.blogfrwk.apiblogfrwk.dto.request.PostDTO;
import com.blogfrwk.apiblogfrwk.dto.response.MessageResponse;
import com.blogfrwk.apiblogfrwk.entity.Post;
import com.blogfrwk.apiblogfrwk.exception.PostCanNotBeDeletedException;
import com.blogfrwk.apiblogfrwk.exception.PostCanNotBeUpdatedException;
import com.blogfrwk.apiblogfrwk.exception.PostNotFoundException;
import com.blogfrwk.apiblogfrwk.repository.PostRepository;
import com.blogfrwk.apiblogfrwk.security.jwt.JwtUtils;
import com.blogfrwk.apiblogfrwk.security.services.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PostService {
    private PostRepository postRepository;
    private JwtUtils jwtUtils;

    private final PostMapper postMapper = PostMapper.INSTANCE;

    public MessageResponse createPost(PostDTO postDTO) {
        Post postToSave = postMapper.toModel(postDTO);
        postToSave.setOwnerName(getUserCurrentSection());
        Post savedPost = postRepository.save(postToSave);
        return new MessageResponse("Created Post with ID " + savedPost.getId());
    }

    public List<PostDTO> listAll() {
        List<Post> allPosts = postRepository.findAll();
        return allPosts.stream()
                .map(postMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PostDTO findByID(Long id) throws PostNotFoundException {
        Post post = verifyExists(id);
        return postMapper.toDTO(post);
    }

    public MessageResponse updateById(Long id, PostDTO postDTO) throws PostNotFoundException, PostCanNotBeUpdatedException {
        Post currentPost = verifyExists(id);
        if (!isCurrentUserOwnsOfThePost(currentPost)) {
            throw new PostCanNotBeUpdatedException();
        }
        postDTO.setId(id);
        Post postToUpdate = postMapper.toModel(postDTO);
        postToUpdate.setOwnerName(getUserCurrentSection());
        postRepository.save(postToUpdate);
        return new MessageResponse("Updated post with ID " + id);
    }

    public MessageResponse deleteById(Long id) throws PostNotFoundException, PostCanNotBeDeletedException {
        Post currentPost = verifyExists(id);
        if (!isCurrentUserOwnsOfThePost(currentPost)) {
            throw new PostCanNotBeDeletedException();
        }
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
