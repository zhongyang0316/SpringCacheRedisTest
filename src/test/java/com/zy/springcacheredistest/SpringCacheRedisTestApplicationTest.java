package com.zy.springcacheredistest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zy.springcacheredistest.dto.UserDto;
import com.zy.springcacheredistest.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringCacheRedisTestApplicationTest {
	
	@Autowired
	private UserService userService;
	
	@Test
	public void test1() throws Exception {
		
		System.out.println("start");
		
		//删除缓存
		this.userService.removeCacheByUserId(1);
		
		UserDto userDto = this.userService.findByUserId(1);
		
		//第二次查询
		UserDto userDto2 = this.userService.findByUserId(1);
		
		//更新
		userDto2.setUserName("xiaoming");
		UserDto userDto3 = this.userService.save(userDto2.getUserId(), userDto2);
		
		UserDto userDto4 = this.userService.findByUserId(1);
		
		UserDto userDto5 = this.userService.findByUserId(1);
		
		System.out.println("");
	}

}
