package com.bootcamp.usermanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticateControllerTest {

	 @Autowired
	  private WebApplicationContext context;

	  private MockMvc mockMvc;

	  @BeforeEach
	  public void setUp() {
	    this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
	        //.apply(documentationConfiguration(restDocumentation))
	        .build();
	  }
	  
	  @Test
		public void loginUser() throws Exception {
			
		String exampleUserJson = "{ \"phoneNumber\": \"9599944411\", \"password\": \"Qwerty@123\"}";
	      MvcResult result = this.mockMvc.perform(post("/login")
		               .content(exampleUserJson)
		               .contentType(MediaType.APPLICATION_JSON)).andReturn();
		               //.andExpect(status().isOk());
	      int status = result.getResponse().getStatus();
	      System.out.println("******** status - " + status);
	      System.out.println(result.getResponse().getContentAsString());
	      assertEquals(HttpStatus.OK.value(), status, "Incorrect Response Status");

	    }
	  
}
