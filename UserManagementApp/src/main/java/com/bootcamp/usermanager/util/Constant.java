package com.bootcamp.usermanager.util;

public interface Constant {
	String SUCCESS_CODE = "200";
	String FAILURE_CODE = "400";
	String FORBIDDEN_CODE = "403";
	String SERVER_ERROR_CODE = "500";
	String UNAUTHORIZED_CODE = "401";
	String APP_SECRET_KEY = "ifVSetMV3LpjwHOocTIGLmQGNm2UWbgY";
	Integer JWT_TOKEN_EXPIRATION = 60000 * 60 * 5;
	Integer PASSWORD_RESET_TOKEN_EXPIRATION = 60000 * 60 * 10;
	String SERVER_ERROR_MESSAGE = "Technical error occured, Please try after some time.";
}
