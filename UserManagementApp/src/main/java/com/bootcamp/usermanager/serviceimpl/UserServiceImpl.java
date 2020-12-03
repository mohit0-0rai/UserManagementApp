package com.bootcamp.usermanager.serviceimpl;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bootcamp.usermanager.entity.User;
import com.bootcamp.usermanager.model.Response;
import com.bootcamp.usermanager.model.UserDto;
import com.bootcamp.usermanager.repository.UserRepository;
import com.bootcamp.usermanager.service.EmailService;
import com.bootcamp.usermanager.service.UserService;
import com.bootcamp.usermanager.util.Constant;
import com.bootcamp.usermanager.util.JwtUtil;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

	@Autowired
	private HttpServletRequest httpRequest;

	@Autowired
	private EmailService emailService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Response forgotPassword(UserDto userDto) {
		Response response = new Response();
		try {
			if (null == userDto.getEmail() || userDto.getEmail().isEmpty()) {
				response.setCode(Constant.FAILURE_CODE);
				response.setMessage("Invalid Email.");
				return response;
			}
			Optional<User> userOpt = userRepository.findByEmail(userDto.getEmail());
			if (!userOpt.isPresent()) {
				response.setCode(Constant.FAILURE_CODE);
				response.setMessage("Email doesn't exist.");
				return response;
			}
			String token = generateResetToken(), to = userDto.getEmail(), subject = "UserManager: Reset your password";
			emailService.sendEmail(token, to, subject);

			userRepository.updateResetToken(token);
			response.setCode(Constant.SUCCESS_CODE);
			response.setMessage("Reset link sent successfully.");
			return response;

		} catch (Exception e) {

			logger.error(e.getMessage());
			response.setCode(Constant.SERVER_ERROR_CODE);
			response.setMessage("Technical Error, please try after some time.");
			return response;
		}
	}

	@Override
	public String generateResetToken() {
		return UUID.randomUUID().toString();
	}

	@Override
	public Response resetPassword(Map<String, Object> resetPasswordDto) {
		Response response = new Response();
		response.setCode(Constant.FAILURE_CODE);
		response.setMessage("Invalid request");
		try {
			if (null == resetPasswordDto || null == resetPasswordDto.get("link")) {
				logger.info("Invalid Request: Link not present");
				return response;
			}

			if (null == resetPasswordDto.get("password") || null == resetPasswordDto.get("confirmPassword")) {
				logger.info("Invalid Request: Password not present");
				return response;
			}

			if (!resetPasswordDto.get("password").toString()
					.equals(resetPasswordDto.get("confirmPassword").toString())) {
				logger.info("Invalid Request: Passwords doesn't match, pass1: " + resetPasswordDto.get("password")
						+ ", pass2: " + resetPasswordDto.get("confirmPassword"));
				return response;
			}

			Optional<User> userOpt = verifyResetLink(response, (String) resetPasswordDto.get("link"));
			if (null == userOpt) {
				return response;
			}

			userRepository.updatePassword(userOpt.get().getId(),
					passwordEncoder.encode((String) resetPasswordDto.get("password")));
			logger.info("Password reset successfully");
			response.setCode(Constant.SUCCESS_CODE);
			response.setMessage("Password reset successfully.");

		} catch (Exception e) {
			response.setCode(Constant.SERVER_ERROR_CODE);
			response.setMessage(Constant.SERVER_ERROR_MESSAGE);
		}

		return response;
	}

	@Override
	public Optional<User> verifyResetLink(Response response, String link) {
		String token = null;
		try {
			String[] splitLink = link.split("/");
			token = splitLink[splitLink.length - 1];
			logger.info("Token received: " + token);
			Optional<User> userOpt = userRepository.findByResetToken(token);
			if (!userOpt.isPresent()) {
				response.setCode(Constant.FAILURE_CODE);
				response.setMessage("Invalid token.");
				return null;
			}

			User user = userOpt.get();
			if (user.getTokenExpirationDate().before(new Date())) {
				logger.info("Expired token");
				response.setCode(Constant.FAILURE_CODE);
				response.setMessage("Token expired.");
				return null;
			}
			logger.info("Token successfully validated");
			response.setCode(Constant.SUCCESS_CODE);
			response.setMessage("Token validated.");

			return userOpt;

		} catch (Exception e) {
			logger.error(e.getMessage());
			response.setCode(Constant.SERVER_ERROR_CODE);
			response.setMessage(Constant.SERVER_ERROR_MESSAGE);
			return null;
		}
	}

	@Override
	public Response updatePassword(Map<String, Object> updatePasswordDto) {
		Response response = new Response();
		try {

			Integer id = JwtUtil.getUserId(httpRequest.getHeader("Authorisation"));
			if (null == id) {
				logger.info("JWT can't be validated");
				response.setCode(Constant.FAILURE_CODE);
				response.setMessage("Invalid JWT");
				return response;
			}

			if (null == updatePasswordDto.get("oldPassword") || null == updatePasswordDto.get("password")
					|| null == updatePasswordDto.get("confirmPassword") || !updatePasswordDto.get("password").toString()
							.equals(updatePasswordDto.get("confirmPassword").toString())) {
				response.setCode(Constant.FAILURE_CODE);
				response.setMessage("Invalid request.");
				return response;
			}

			Optional<User> userOpt = userRepository.findById(id);

			if (!passwordEncoder.matches(updatePasswordDto.get("oldPassword").toString(),
					userOpt.get().getPassword())) {
				logger.info("Old password doesn't match");
				response.setCode(Constant.FAILURE_CODE);
				response.setMessage("Wrong password.");
				return response;
			}
			userRepository.updatePassword(id, passwordEncoder.encode(updatePasswordDto.get("password").toString()));

			response.setCode(Constant.SUCCESS_CODE);
			response.setMessage("Password updated successfully.");

		} catch (Exception e) {
			response.setCode(Constant.SERVER_ERROR_CODE);
			response.setMessage(Constant.SERVER_ERROR_MESSAGE);
		}

		return response;
	}

	@Override
	public Response addContact(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
