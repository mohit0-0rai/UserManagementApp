package com.bootcamp.usermanager.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bootcamp.usermanager.model.Response;
import com.bootcamp.usermanager.model.UserDto;

public class RequestValidationUtil {

	public static boolean validateLogin(UserDto userDto, Response response) {

		Pattern phonePattern = Pattern.compile("(0/91)?[7-9][0-9]{9}");
		Pattern emailPattern = Pattern.compile(
				"^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$");

		if(null == userDto.getPassword() || userDto.getPassword().isEmpty()) {
			response.setCode(Constant.FAILURE_CODE);
			response.setMessage("Password can't be empty");
			return false;
		}
		
		if (null != userDto.getEmail() && !userDto.getEmail().isEmpty()) {
			Matcher matcher = emailPattern.matcher(userDto.getEmail());
			if (!matcher.matches()) {
				response.setCode(Constant.FAILURE_CODE);
				response.setMessage("Invalid email.");
				return false;
			} else
				return true;
		} else if (null != userDto.getPhoneNumber() && !userDto.getPhoneNumber().isEmpty()) {
			Matcher matcher = phonePattern.matcher(userDto.getPhoneNumber());
			if (!matcher.matches()) {
				response.setCode(Constant.FAILURE_CODE);
				response.setMessage("Invalid phone number.");
				return false;
			} else
				return true;
		} else {
			response.setCode(Constant.FAILURE_CODE);
			response.setMessage("Email or phone number can't be empty.");
			return false;
		}
	}

	public static boolean validateRegister(UserDto userDto, Response response) {

		Pattern phonePattern = Pattern.compile("(0/91)?[7-9][0-9]{9}");
		Pattern emailPattern = Pattern.compile(
				"^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$");

		if(null == userDto.getPassword() || userDto.getPassword().isEmpty()) {
			response.setCode(Constant.FAILURE_CODE);
			response.setMessage("Password can't be empty");
			return false;
		}
		
		if (null != userDto.getEmail() && !userDto.getEmail().isEmpty()) {
			Matcher matcher = emailPattern.matcher(userDto.getEmail());
			if (!matcher.matches()) {
				response.setCode(Constant.FAILURE_CODE);
				response.setMessage("Invalid email.");
				return false;
			} else
				return true;
		} else if (null != userDto.getPhoneNumber() && !userDto.getPhoneNumber().isEmpty()) {
			Matcher matcher = phonePattern.matcher(userDto.getPhoneNumber());
			if (!matcher.matches()) {
				response.setCode(Constant.FAILURE_CODE);
				response.setMessage("Invalid phone number.");
				return false;
			} else
				return true;
		} else {
			response.setCode(Constant.FAILURE_CODE);
			response.setMessage("Email or phone number can't be empty.");
			return false;
		}
	}

}
