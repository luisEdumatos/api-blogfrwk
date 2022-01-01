package com.blogfrwk.apiblogfrwk;

import com.blogfrwk.apiblogfrwk.controller.AuthController;
import com.blogfrwk.apiblogfrwk.controller.PostController;
import com.blogfrwk.apiblogfrwk.dto.request.LoginRequest;
import com.blogfrwk.apiblogfrwk.dto.request.PostDTO;
import com.blogfrwk.apiblogfrwk.dto.request.SignupRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ApiBlogfrwkApplicationTests {

	private MockMvc authMockMvc;

	private MockMvc postMockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private AuthController authController;

	@Autowired
	private PostController postController;

	@Test
	void contextLoads() {
	}

	@BeforeAll
	public void setUp() {
		this.authMockMvc = MockMvcBuilders.standaloneSetup(authController).build();
		this.postMockMvc = MockMvcBuilders.standaloneSetup(postController).build();
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

	@Test
	public void testCreatePost() throws Exception {
		SignupRequest signupMock = new SignupRequest();
		signupMock.setUsername("admin3");
		signupMock.setEmail("admin3@blogfrwk.com");
		signupMock.setPassword("123456");

		LoginRequest signinMock = new LoginRequest();
		signinMock.setUsername("admin3");
		signinMock.setPassword("123456");

		this.authController.registerUser(signupMock);
		this.authController.authenticateUser(signinMock);

		PostDTO postMock = new PostDTO();
		postMock.setDescription("Descricao do Post de Teste");

		this.postMockMvc.perform(MockMvcRequestBuilders.post("/api/posts")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(postMock)))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}
}
