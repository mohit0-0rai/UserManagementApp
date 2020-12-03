package com.bootcamp.usermanager.serviceimpl;

import java.util.Properties;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.bootcamp.usermanager.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

	@Override
	public JavaMailSender configureEmailSender() throws Exception {

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp-mail.outlook.com");
		mailSender.setPort(587);

		mailSender.setUsername("mohitmicros@hotmail.com");
		mailSender.setPassword("Mowgli.jungle1");

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "false");

		return mailSender;
	}

	@Override
	public void sendEmail(String token, String emailTo, String subject) throws Exception {
		JavaMailSender mailSender = configureEmailSender();
		String url = "localhost:8080/usermanager/verify-token/" + token;
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("mohitmicros@hotmail.com");
		mailMessage.setTo(emailTo);
		mailMessage.setSubject(subject);
		mailMessage.setText("Please click on this password reset link " + url);

		mailSender.send(mailMessage);
	}

}
