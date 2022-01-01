package com.blogfrwk.apiblogfrwk.service;


import com.blogfrwk.apiblogfrwk.dto.mapper.PostMapper;
import com.blogfrwk.apiblogfrwk.dto.request.PostDTO;
import com.blogfrwk.apiblogfrwk.dto.response.MessageResponse;
import com.blogfrwk.apiblogfrwk.entity.Post;
import com.blogfrwk.apiblogfrwk.exception.PostNotFoundException;
import com.blogfrwk.apiblogfrwk.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PostService {
    private PostRepository postRepository;
    private final PostMapper postMapper = PostMapper.INSTANCE;

    public MessageResponse createPost(PostDTO postDTO) {
        Post postToSave = postMapper.toModel(postDTO);
        Post savedPost = postRepository.save(postToSave);
        return new MessageResponse("Created Post with ID " + savedPost.getId());
    }

    public void deleteById(Long id) throws PostNotFoundException {
        verifyExists(id);
        postRepository.deleteById(id);
    }

    private Post verifyExists(Long id) throws PostNotFoundException {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isEmpty()) {
            throw new PostNotFoundException(id);
        }
        return optionalPost.get();
    }
}
