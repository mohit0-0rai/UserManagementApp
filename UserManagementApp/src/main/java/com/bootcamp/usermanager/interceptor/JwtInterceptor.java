package com.bootcamp.usermanager.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.bootcamp.usermanager.util.Constant;
import com.bootcamp.usermanager.util.JwtUtil;

@Component
public class JwtInterceptor implements HandlerInterceptor {

	private static final Logger logger = LogManager.getLogger(JwtInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("Checking authorisation");
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		boolean status = false;
		String userId = null;
		String token = null;
		String authHeader = null;
		try {
			authHeader = httpRequest.getHeader("Authorisation");
			token = authHeader != null ? authHeader.substring(7) : "";
			if (!token.isEmpty()) {

				userId = JwtUtil.validateToken(token).getId();
				logger.info("UserId: " + userId);
				status = true;
			} else {
				status = false;
			}
		} catch (Exception e) {
			status = false;
			logger.error(e.getMessage());
		}
		if (status) {
			httpRequest.setAttribute("userId", userId);
		} else {
			logger.info("Unauthorised access, invalid token: " + token);
			JSONObject responseJson = new JSONObject();
			responseJson.put("code", Constant.UNAUTHORIZED_CODE);
			responseJson.put("message", "Unauthorised access");
			httpResponse.setStatus(HttpServletResponse.SC_OK);
			httpResponse.getWriter().write(responseJson.toString());
			httpResponse.getWriter().flush();
			httpResponse.getWriter().close();
		}
		
		return status;
	}

}
