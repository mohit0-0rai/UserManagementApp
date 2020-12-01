package com.bootcamp.usermanager.controller;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bootcamp.usermanager.model.Response;
import com.bootcamp.usermanager.model.UserDto;
import com.bootcamp.usermanager.service.AuthenticateService;
import com.bootcamp.usermanager.service.UserService;

@RestController
public class AuthenticateController {
	
	private static final Logger logger = LogManager.getLogger(AuthenticateController.class);
	
	@Autowired
	private AuthenticateService authenticateService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("login")
	public Response login(@RequestBody UserDto userDto) {
		logger.info("In /login");
		return authenticateService.login(userDto);
	}
	
	@PostMapping("register")
	public Response register(@RequestBody UserDto userDto) {
		logger.info("In /register");
		return authenticateService.register(userDto);
	}
	
	@PostMapping("forgot-password")
	public Response forgotPassword(@RequestBody UserDto userDto) {
		logger.info("In /forgot-password");
		return userService.forgotPassword(userDto);
	}
	
	@PostMapping("reset-password")
	public Response resetPassword(@RequestBody Map<String, Object> resetPasswordDto) {
		logger.info("In /reset-password");
		return userService.resetPassword(resetPasswordDto);
	}

}
