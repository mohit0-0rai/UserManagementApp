package com.bootcamp.usermanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestAuthenticationController {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@BeforeEach
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
				// .apply(documentationConfiguration(restDocumentation))
				.build();
	}

	@Test
	public void loginUserWithCorrectCredentials() throws Exception {

		String userJson = "{ \"email\": \"mohitrai@hotmail.com\", \"password\": \"pass@123\"}";
		MvcResult result = this.mockMvc
				.perform(post("/login").content(userJson).contentType(MediaType.APPLICATION_JSON)).andReturn();
		String jsonResponseString = result.getResponse().getContentAsString();
		JSONObject jsonResponse = new JSONObject(jsonResponseString);
		assertEquals(Integer.toString(HttpStatus.OK.value()), jsonResponse.get("code"), "Incorrect Response Code");

	}

	@Test
	public void loginUserWithIncorrectCredentials() throws Exception {

		String userJson = "{ \"email\": \"mohit@hotmail.com\", \"password\": \"pass@123\"}";
		MvcResult result = this.mockMvc
				.perform(post("/login").content(userJson).contentType(MediaType.APPLICATION_JSON)).andReturn();
		String jsonResponseString = result.getResponse().getContentAsString();
		JSONObject jsonResponse = new JSONObject(jsonResponseString);
		assertEquals(Integer.toString(HttpStatus.FORBIDDEN.value()), jsonResponse.get("code"), "Incorrect Response Code");

	}

	@Test
	public void registerUserWithEmailValidRequest() throws Exception {

		String userJson = "{ \"email\": \"mohit@hotmail.com\", \"password\": \"pass@123\"}";
		MvcResult result = this.mockMvc
				.perform(post("/register").content(userJson).contentType(MediaType.APPLICATION_JSON)).andReturn();
		String jsonResponseString = result.getResponse().getContentAsString();
		JSONObject jsonResponse = new JSONObject(jsonResponseString);
		assertEquals(Integer.toString(HttpStatus.OK.value()), jsonResponse.get("code"), "Incorrect Response Code");

	}
	
	@Test
	public void regsiterUserWitheEmailInvalidRequest() throws Exception {

		String userJson = "{ \"email\": \"mohit\", \"password\": \"pass@123\"}";
		MvcResult result = this.mockMvc
				.perform(post("/register").content(userJson).contentType(MediaType.APPLICATION_JSON)).andReturn();
		String jsonResponseString = result.getResponse().getContentAsString();
		JSONObject jsonResponse = new JSONObject(jsonResponseString);
		assertEquals(Integer.toString(HttpStatus.BAD_REQUEST.value()), jsonResponse.get("code"), "Incorrect Response Code");

	}
	
	@Test
	public void registerUserWithEmailAlreadyPresent() throws Exception {

		String userJson = "{ \"email\": \"mohitrai@hotmail.com\", \"password\": \"pass@123\"}";
		MvcResult result = this.mockMvc
				.perform(post("/register").content(userJson).contentType(MediaType.APPLICATION_JSON)).andReturn();
		String jsonResponseString = result.getResponse().getContentAsString();
		JSONObject jsonResponse = new JSONObject(jsonResponseString);
		assertEquals(Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR.value()), jsonResponse.get("code"), "Incorrect Response Code");

	}
	
	@Test
	public void registerUserWithPhoneValidRequest() throws Exception {

		String userJson = "{ \"phoneNumber\": \"9878761388\", \"password\": \"pass@123\"}";
		MvcResult result = this.mockMvc
				.perform(post("/register").content(userJson).contentType(MediaType.APPLICATION_JSON)).andReturn();
		String jsonResponseString = result.getResponse().getContentAsString();
		JSONObject jsonResponse = new JSONObject(jsonResponseString);
		assertEquals(Integer.toString(HttpStatus.OK.value()), jsonResponse.get("code"), "Incorrect Response Code");

	}
	
	@Test
	public void regsiterUserWithPhoneInvalidRequest() throws Exception {

		String userJson = "{ \"phoneNumber\": \"mohit\", \"password\": \"pass@123\"}";
		MvcResult result = this.mockMvc
				.perform(post("/register").content(userJson).contentType(MediaType.APPLICATION_JSON)).andReturn();
		String jsonResponseString = result.getResponse().getContentAsString();
		JSONObject jsonResponse = new JSONObject(jsonResponseString);
		assertEquals(Integer.toString(HttpStatus.BAD_REQUEST.value()), jsonResponse.get("code"), "Incorrect Response Code");

	}
	
	@Test
	public void registerUserWithPhoneAlreadyPresent() throws Exception {

		String userJson = "{ \"phoneNumber\": \"9891898717\", \"password\": \"pass@123\"}";
		MvcResult result = this.mockMvc
				.perform(post("/register").content(userJson).contentType(MediaType.APPLICATION_JSON)).andReturn();
		String jsonResponseString = result.getResponse().getContentAsString();
		JSONObject jsonResponse = new JSONObject(jsonResponseString);
		assertEquals(Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR.value()), jsonResponse.get("code"), "Incorrect Response Code");

	}
}
