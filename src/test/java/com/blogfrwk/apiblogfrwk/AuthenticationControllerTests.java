package com.blogfrwk.apiblogfrwk;

import com.blogfrwk.apiblogfrwk.controller.AuthController;
import com.blogfrwk.apiblogfrwk.dto.request.LoginRequest;
import com.blogfrwk.apiblogfrwk.dto.request.SignupRequest;
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
public class AuthenticationControllerTests extends ApiBlogfrwkApplicationTests {

    private MockMvc authMockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthController authController;

    @BeforeAll
    public void setUp() {
        this.authMockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    public void testCreateUserSignupRequest() throws Exception {
        SignupRequest signupMock = new SignupRequest();
        signupMock.setUsername("admin");
        signupMock.setEmail("admin@blogfrwk.com");
        signupMock.setPassword("123456");

        this.authMockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(signupMock)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testLoginRequest() throws Exception {
        SignupRequest signupMock = new SignupRequest();
        signupMock.setUsername("admin2");
        signupMock.setEmail("admin2@blogfrwk.com");
        signupMock.setPassword("123456");

        LoginRequest signinMock = new LoginRequest();
        signinMock.setUsername("admin2");
        signinMock.setPassword("123456");

        this.authController.registerUser(signupMock);

        this.authMockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signin")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(signinMock)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
