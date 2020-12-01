package com.bootcamp.usermanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bootcamp.usermanager.repository.UserRepository;

@RestController
public class TestController {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/test")
	public String test() {
		
		return "<h1> Test works! </h1>";
	}
	
}
