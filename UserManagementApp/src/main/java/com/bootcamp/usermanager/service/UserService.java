package com.bootcamp.usermanager.service;

import java.util.Map;
import java.util.Optional;

import com.bootcamp.usermanager.entity.User;
import com.bootcamp.usermanager.model.Response;
import com.bootcamp.usermanager.model.UserDto;

public interface UserService {

	Response forgotPassword(UserDto userDto);
		
	Optional<User> verifyResetLink(Response response, String link);
	
	String generateResetToken();

	Response resetPassword(Map<String, Object> resetPasswordDto);

	Response updatePassword(Map<String, Object> updatePasswordDto);

	Response addContact(String userId);

}
