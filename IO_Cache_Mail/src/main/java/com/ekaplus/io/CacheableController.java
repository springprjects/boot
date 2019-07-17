package com.ekaplus.io;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping("/app")
public class CacheableController {

	@Autowired
	private CacheableService cacheableService;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	
	@GetMapping("/getGreet")
	public String getMessage(@RequestParam(value="name", defaultValue="World") String name) {
		SimpleMailMessage  mimeMessage =new SimpleMailMessage();
		mimeMessage.setFrom("khuthbu123@gmail.com");
		mimeMessage.setTo("khuthbu123@gmail.com");
		mimeMessage.setSubject("Test");

		javaMailSender.send(mimeMessage);
		return cacheableService.getGreet(name);
	}
	
	@GetMapping("/getGreetEvicted")
	public String getMessageEvict() {
		 cacheableService.evictGreet();
		 return "Evicted";
	}
}
