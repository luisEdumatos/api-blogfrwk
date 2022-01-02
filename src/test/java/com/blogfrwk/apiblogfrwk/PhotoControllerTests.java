package com.blogfrwk.apiblogfrwk;

import com.blogfrwk.apiblogfrwk.controller.PhotoController;
import com.blogfrwk.apiblogfrwk.controller.PostController;
import com.blogfrwk.apiblogfrwk.dto.request.PhotoDTO;
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
public class PhotoControllerTests extends ApiBlogfrwkApplicationTests {

    private MockMvc photoMockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostController postController;

    @Autowired
    private PhotoController photoController;

    @BeforeAll
    public void setUp() {
        this.photoMockMvc = MockMvcBuilders.standaloneSetup(photoController).build();
    }

    @Test
    public void testCreatePhoto() throws Exception {
        PostDTO postDTOMock = new PostDTO();
        postDTOMock.setDescription("Descricao do Post de Teste");

        this.postController.createPost(postDTOMock);

        Post postMock = new Post();
        postMock.setDescription("Descricao do Post de Teste");
        postMock.setId(1L);

        PhotoDTO photoDTOMock = new PhotoDTO();
        photoDTOMock.setPhotoContent("Conteudo");
        photoDTOMock.setPhotoPath("Caminho");
        photoDTOMock.setPost(postMock);

        this.photoMockMvc.perform(MockMvcRequestBuilders.post("/api/photos")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(photoDTOMock)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testListAllPhotos() throws Exception {
        this.photoMockMvc.perform(MockMvcRequestBuilders.get("/api/photos")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testFindPhotoById() throws Exception {
        PostDTO postDTOMock = new PostDTO();
        postDTOMock.setDescription("Descricao do Post de Teste");

        this.postController.createPost(postDTOMock);

        Post postMock = new Post();
        postMock.setDescription("Descricao do Post de Teste");
        postMock.setId(1L);

        PhotoDTO photoDTOMock = new PhotoDTO();
        photoDTOMock.setPhotoContent("Conteudo");
        photoDTOMock.setPhotoPath("Caminho");
        photoDTOMock.setPost(postMock);

        this.photoController.createPhoto(photoDTOMock);

        this.photoMockMvc.perform(MockMvcRequestBuilders.get("/api/photos/1")).andExpect(MockMvcResultMatchers.status().isOk());
    }
}
