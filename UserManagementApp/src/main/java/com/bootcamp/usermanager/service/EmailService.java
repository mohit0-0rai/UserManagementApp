package com.bootcamp.usermanager.service;

import org.springframework.mail.javamail.JavaMailSender;

public interface EmailService {

	JavaMailSender configureEmailSender() throws Exception;
	
	void sendEmail(String token, String emailTo, String subject) throws Exception;
	
}
