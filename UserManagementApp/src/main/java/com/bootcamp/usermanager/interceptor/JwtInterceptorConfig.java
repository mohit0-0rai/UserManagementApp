package com.bootcamp.usermanager.interceptor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class JwtInterceptorConfig implements WebMvcConfigurer {

	@Autowired
	JwtInterceptor jwtInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		List<String> urls = new ArrayList<String>();
		urls.add("/login");
		urls.add("/register");
		urls.add("/error");
		urls.add("/forgot-password");
		urls.add("/reset-password");
		registry.addInterceptor(jwtInterceptor).excludePathPatterns(urls);
	}
}
