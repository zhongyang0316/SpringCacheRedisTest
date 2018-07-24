package com.zy.springcacheredistest.init;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.zy.springcacheredistest.domain.User;
import com.zy.springcacheredistest.domain.UserRepository;
import com.zy.springcacheredistest.enums.GenderType;

@Component
public class UserInitial implements ApplicationListener<ContextRefreshedEvent> {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private boolean alreadySetup = false;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent paramE) {
		
		if (this.alreadySetup) {
			return;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		User user = new User();
		user.setUserName("zhongyang");
		user.setGenderType(GenderType.MAN);
		try {
			user.setBirthDate(sdf.parse("1993-03-16"));
		} catch (ParseException e) {
			this.logger.error("init error:{}", e);
		}
		this.userRepository.save(user);
		
		this.alreadySetup = true;
	}

}
