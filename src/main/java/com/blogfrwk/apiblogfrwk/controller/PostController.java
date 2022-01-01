package com.blogfrwk.apiblogfrwk.controller;


import com.blogfrwk.apiblogfrwk.dto.request.PostDTO;
import com.blogfrwk.apiblogfrwk.dto.response.MessageResponse;
import com.blogfrwk.apiblogfrwk.exception.PostCanNotBeDeletedException;
import com.blogfrwk.apiblogfrwk.exception.PostNotFoundException;
import com.blogfrwk.apiblogfrwk.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PostController {

    private PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse createPost(@RequestBody @Valid PostDTO postDTO) {
        return postService.createPost(postDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse deleteById(@PathVariable Long id) throws PostNotFoundException, PostCanNotBeDeletedException {
        return postService.deleteById(id);
    }
}
