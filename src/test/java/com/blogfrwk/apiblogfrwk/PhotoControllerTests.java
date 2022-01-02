package com.blogfrwk.apiblogfrwk;

import com.blogfrwk.apiblogfrwk.controller.PhotoController;
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
    private PhotoController photoController;

    @BeforeAll
    public void setUp() {
        this.photoMockMvc = MockMvcBuilders.standaloneSetup(photoController).build();
    }

    @Test
    public void testFindAllPhotos() throws Exception {
        this.photoMockMvc.perform(MockMvcRequestBuilders.get("/api/photos")).andExpect(MockMvcResultMatchers.status().isOk());
    }
}
