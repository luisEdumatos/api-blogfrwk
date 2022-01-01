package com.blogfrwk.apiblogfrwk;

import com.blogfrwk.apiblogfrwk.controller.AuthController;
import com.blogfrwk.apiblogfrwk.controller.PostController;
import com.blogfrwk.apiblogfrwk.dto.request.PostDTO;
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
public class PostControllerTests extends ApiBlogfrwkApplicationTests {

    private MockMvc postMockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthController authController;

    @Autowired
    private PostController postController;

    @BeforeAll
    public void setUp() {
        this.postMockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }

    @Test
    public void testCreatePost() throws Exception {
        PostDTO postMock = new PostDTO();
        postMock.setDescription("Descricao do Post de Teste");

        this.postMockMvc.perform(MockMvcRequestBuilders.post("/api/posts")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(postMock)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testListAllPosts() throws Exception {
        this.postMockMvc.perform(MockMvcRequestBuilders.get("/api/posts")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    public void testFindPostById() throws Exception {
        PostDTO postMock = new PostDTO();
        postMock.setDescription("Descricao do Post de Teste");

        this.postController.createPost(postMock);

        this.postMockMvc.perform(MockMvcRequestBuilders.get("/api/posts/1")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeletePostById() throws Exception {
        PostDTO postMock = new PostDTO();
        postMock.setDescription("Descricao do Post de Teste");

        this.postController.createPost(postMock);

        this.postMockMvc.perform(MockMvcRequestBuilders.delete("/api/posts/2")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(postMock)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
