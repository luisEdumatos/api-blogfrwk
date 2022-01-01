package com.blogfrwk.apiblogfrwk;

import com.blogfrwk.apiblogfrwk.controller.AuthController;
import com.blogfrwk.apiblogfrwk.dto.request.LoginRequest;
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

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private AuthController authController;

	private static LoginRequest loginRequest;
	private static SignupRequest signupRequest;

	@Test
	void contextLoads() {
	}

	@BeforeAll
	public void setUp() {
		this.authMockMvc = MockMvcBuilders.standaloneSetup(authController).build();
	}

	private void createSignupRequestMock() {
		signupRequest = new SignupRequest();
		signupRequest.setUsername("admin");
		signupRequest.setEmail("admin@blogfrwk.com");
		signupRequest.setPassword("123456");
	}

	private void createLoginRequestMock() {
		loginRequest = new LoginRequest();
		loginRequest.setUsername("admin");
		loginRequest.setPassword("123456");
	}

	@Test
	public void testCreateUserSignupRequest() throws Exception {
		this.createSignupRequestMock();
		this.authMockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(signupRequest)))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

}
