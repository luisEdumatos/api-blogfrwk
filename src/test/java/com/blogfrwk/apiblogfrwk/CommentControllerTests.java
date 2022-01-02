package com.blogfrwk.apiblogfrwk;

import com.blogfrwk.apiblogfrwk.controller.CommentController;
import com.blogfrwk.apiblogfrwk.controller.PostController;
import com.blogfrwk.apiblogfrwk.dto.request.CommentDTO;
import com.blogfrwk.apiblogfrwk.dto.request.PostDTO;
import com.blogfrwk.apiblogfrwk.entity.Post;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@TestPropertySource(locations="classpath:test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CommentControllerTests extends ApiBlogfrwkApplicationTests {

    private MockMvc commentMockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostController postController;

    @Autowired
    private CommentController commentController;

    @BeforeAll
    public void setUp() {
        this.commentMockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }

    @Test
    public void testCreateComment() throws Exception {
        PostDTO postDTOMock = new PostDTO();
        postDTOMock.setDescription("Descricao do Post de Teste");

        this.postController.createPost(postDTOMock);

        Post postMock = new Post();
        postMock.setDescription("Descricao do Post de Teste");
        postMock.setId(1L);

        CommentDTO commentDTOMock = new CommentDTO();
        commentDTOMock.setComment("Descricao do Comentario");
        commentDTOMock.setPost(postMock);

        this.commentMockMvc.perform(MockMvcRequestBuilders.post("/api/comments")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(commentDTOMock)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

}
