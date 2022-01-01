package com.blogfrwk.apiblogfrwk.controller;


import com.blogfrwk.apiblogfrwk.dto.request.PostDTO;
import com.blogfrwk.apiblogfrwk.dto.response.MessageResponse;
import com.blogfrwk.apiblogfrwk.exception.PostCanNotBeDeletedException;
import com.blogfrwk.apiblogfrwk.exception.PostNotFoundException;
import com.blogfrwk.apiblogfrwk.service.PostService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping
    public List<PostDTO> listAll() {
        return postService.listAll();
    }

    @GetMapping("/{id}")
    public PostDTO findById(@PathVariable Long id) throws PostNotFoundException {
        return postService.findByID(id);
    }

    @PutMapping("/{id}")
    public MessageResponse updateById(@PathVariable Long id, @RequestBody @Valid PostDTO postDTO) throws PostNotFoundException {
        return postService.updateById(id, postDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse deleteById(@PathVariable Long id) throws PostNotFoundException, PostCanNotBeDeletedException {
        return postService.deleteById(id);
    }
}
