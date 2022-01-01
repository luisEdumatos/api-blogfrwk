package com.blogfrwk.apiblogfrwk.service;


import com.blogfrwk.apiblogfrwk.dto.mapper.PostMapper;
import com.blogfrwk.apiblogfrwk.dto.request.PostDTO;
import com.blogfrwk.apiblogfrwk.dto.response.MessageResponse;
import com.blogfrwk.apiblogfrwk.entity.Post;
import com.blogfrwk.apiblogfrwk.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PostService {
    private PostRepository postRepository;
    private final PostMapper postMapper = PostMapper.INSTANCE;

    public MessageResponse createPost(PostDTO postDTO) {
        Post postToSave = postMapper.toModel(postDTO);
        Post savedPost = postRepository.save(postToSave);
        return new MessageResponse("Created post with ID " + savedPost.getId());
    }
}
