package com.scnu.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class MainClient {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${mail.username}")
	private String from;
	
	public void sendMain(String to, String subject, String content) {
		   try {
			   MimeMessage message = mailSender.createMimeMessage();
			   MimeMessageHelper helper = new MimeMessageHelper(message);
			   helper.setFrom(from);
			   helper.setTo(to);
	           helper.setSubject(subject);
	           helper.setText(content, true);
	           mailSender.send(helper.getMimeMessage()); 
		   }catch(MessagingException e) {
			   System.out.println("发送邮件失败，信息是: " + e.getMessage());
		   }
	}

}
