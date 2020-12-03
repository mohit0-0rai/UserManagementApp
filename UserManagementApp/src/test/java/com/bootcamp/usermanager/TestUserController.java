package com.bootcamp.usermanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
public class TestUserController {

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
	public void resetPasswordValidRequest() throws Exception {

		String userJson = "{ \"oldPassword\": \"pass@123\", \"password\": \"pass@123\", \"confirmPassword\": \"pass@123\"}";
		MvcResult result = this.mockMvc
				.perform(post("/user/update-password").header("Authorisation",
						"Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwiaWF0IjoxNjA3MDExMjM5LCJleHAiOjE2MDcwMjkyMzl9.H8LQSAjdVYF7cS1nXqw4IQz33lHjuj87G8b_kimKimw")
						.content(userJson).contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		String jsonResponseString = result.getResponse().getContentAsString();
		JSONObject jsonResponse = new JSONObject(jsonResponseString);
		assertEquals(Integer.toString(HttpStatus.OK.value()), jsonResponse.get("code"), "Incorrect Response Code");

	}

	@Test
	public void resetPasswordInvalidRequest() throws Exception {

		String userJson = "{ \"oldPassword\": \"notcorrect@123\", \"password\": \"pass@123\", \"confirmPassword\": \"pass@123\"}";
		MvcResult result = this.mockMvc.perform(post("/user/update-password").header("Authorisation",
				"Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwiaWF0IjoxNjA3MDExMjM5LCJleHAiOjE2MDcwMjkyMzl9.H8LQSAjdVYF7cS1nXqw4IQz33lHjuj87G8b_kimKimw")
				.content(userJson).contentType(MediaType.APPLICATION_JSON)).andReturn();
		String jsonResponseString = result.getResponse().getContentAsString();
		JSONObject jsonResponse = new JSONObject(jsonResponseString);
		assertEquals(Integer.toString(HttpStatus.BAD_REQUEST.value()), jsonResponse.get("code"),
				"Incorrect Response Code");

	}
}
