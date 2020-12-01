package com.bootcamp.usermanager.service;

import com.bootcamp.usermanager.model.Response;
import com.bootcamp.usermanager.model.UserDto;

public interface AuthenticateService {

	Response login(UserDto userDto);

	Response register(UserDto userDto);

}
