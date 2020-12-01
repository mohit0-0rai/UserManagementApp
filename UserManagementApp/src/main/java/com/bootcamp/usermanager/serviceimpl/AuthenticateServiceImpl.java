package com.bootcamp.usermanager.serviceimpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bootcamp.usermanager.entity.User;
import com.bootcamp.usermanager.model.Response;
import com.bootcamp.usermanager.model.UserDto;
import com.bootcamp.usermanager.repository.UserRepository;
import com.bootcamp.usermanager.service.AuthenticateService;
import com.bootcamp.usermanager.util.Constant;
import com.bootcamp.usermanager.util.JwtUtil;
import com.bootcamp.usermanager.util.RequestValidationUtil;

@Service
public class AuthenticateServiceImpl implements AuthenticateService {

	private static final Logger logger = LogManager.getLogger(AuthenticateServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Response login(UserDto userDto) {
		Response response = new Response();
		// logger.info("User request: " + userDto);
		try {
			boolean validationStatus = RequestValidationUtil.validateLogin(userDto, response);
			if (!validationStatus)
				return response;
			Optional<User> userOpt = userRepository.findByEmailOrPhoneNumber(userDto.getEmail(), userDto.getEmail());

			if (userOpt.isPresent() && passwordEncoder.matches(userDto.getPassword(), userOpt.get().getPassword())) {
				String token = JwtUtil.generateToken(userOpt.get().getId());
				Map<String, Object> data = new HashMap<>();
				data.put("token", token);
				response.setCode(Constant.SUCCESS_CODE);
				response.setMessage("Logged in successfully");
				response.setData(data);
				userRepository.updateLastLogin(userOpt.get().getId());
			} else {
				response.setCode(Constant.FAILURE_CODE);
				response.setMessage("Invalid login credentials");
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setCode(Constant.SERVER_ERROR_CODE);
			response.setMessage(Constant.SERVER_ERROR_MESSAGE);
		}
//		} finally {
//			logger.info("Response to user: " + response);
//		}
		return response;
	}

	@Override
	public Response register(UserDto userDto) {
		Response response = new Response();
		// logger.info("User Request: " + userDto);
		try {
			boolean validationStatus = RequestValidationUtil.validateRegister(userDto, response);
			if (!validationStatus) {
				return response;
			}

			if (null != userDto.getEmail() && !userDto.getEmail().isEmpty()) {
				Optional<User> userOpt = userRepository.findByEmail(userDto.getEmail());
				if (userOpt.isPresent()) {
					response.setCode(Constant.FAILURE_CODE);
					response.setMessage("Email already exists.");
					return response;
				}
			}

			if (null != userDto.getPhoneNumber() && !userDto.getPhoneNumber().isEmpty()) {
				Optional<User> userOpt = userRepository.findByPhoneNumber(userDto.getPhoneNumber());
				if (userOpt.isPresent()) {
					response.setCode(Constant.FAILURE_CODE);
					response.setMessage("Phone number already exists.");
					return response;
				}
			}
			User user = new User();
			user.setPassword(passwordEncoder.encode(userDto.getPassword()));
			user.setEmail(userDto.getEmail());
			user.setPhoneNumber(userDto.getPhoneNumber());
			user = userRepository.save(user);

			response.setCode(Constant.SUCCESS_CODE);
			response.setMessage("Registered successfully");
			Map<String, Object> data = new HashMap<>();
			data.put("user", user);
			response.setData(data);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Constant.SERVER_ERROR_CODE);
			response.setMessage(Constant.SERVER_ERROR_MESSAGE);
			return response;
		}
//		} finally {
//			logger.info("Response to user: " + response);
//		}
	}

}
