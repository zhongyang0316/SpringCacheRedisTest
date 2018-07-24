package com.zy.springcacheredistest.service;

import com.zy.springcacheredistest.dto.UserDto;

public interface UserService {
	
	UserDto findByUserId(Integer userId);
	
	UserDto save(Integer userId, UserDto userDto);
	
	void removeByUserId(Integer userId);
	
	void removeCacheByUserId(Integer userId);

}
