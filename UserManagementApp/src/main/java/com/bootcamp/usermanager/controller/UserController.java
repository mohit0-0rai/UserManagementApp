package com.bootcamp.usermanager.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bootcamp.usermanager.model.Response;
import com.bootcamp.usermanager.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("update-password")
	public Response updatePassword(@RequestBody Map<String, Object> updatePasswordDto) {
		return userService.updatePassword(updatePasswordDto);
	}
}
