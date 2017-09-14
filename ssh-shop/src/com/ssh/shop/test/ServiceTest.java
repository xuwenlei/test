package com.ssh.shop.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ssh.shop.entities.User;
import com.ssh.shop.service.UserService;

public class ServiceTest {
	
	@Before
	public void init(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	
	@Test
	public void testUserService() {
		UserService userService = new UserService();
		User user = userService.login("123456", "123456");
		System.out.println(user);
	}

}
