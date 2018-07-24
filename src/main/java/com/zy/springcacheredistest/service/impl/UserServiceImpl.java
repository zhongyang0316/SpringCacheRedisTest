package com.zy.springcacheredistest.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import com.zy.springcacheredistest.constants.CacheRedisConstants;
import com.zy.springcacheredistest.domain.User;
import com.zy.springcacheredistest.domain.UserRepository;
import com.zy.springcacheredistest.dto.UserDto;
import com.zy.springcacheredistest.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserRepository userRepository;

	@Cacheable(value = CacheRedisConstants.USER_CACHE_NAME, 
			key = "#userId",
			cacheManager = CacheRedisConstants.CACHE_REDIS_MANAGER_NAME)
	@Override
	public UserDto findByUserId(Integer userId) {
		this.logger.info("findByUserId,userId:{}", userId);
		User user = this.userRepository.findOne(userId);
		return new UserConverter().convert(user);
	}

	@CacheEvict(value = CacheRedisConstants.USER_CACHE_NAME, 
			key = "#userId",
			cacheManager = CacheRedisConstants.CACHE_REDIS_MANAGER_NAME)
	@Override
	public UserDto save(Integer userId, UserDto userDto) {
		this.logger.info("save,userId:{}", userId);
		User user = this.userRepository.findOne(userId);
		BeanUtils.copyProperties(userDto, user);
		user = this.userRepository.save(user);
		return new UserConverter().convert(user);
	}

	@CacheEvict(value = CacheRedisConstants.USER_CACHE_NAME, 
			key = "#userId",
			cacheManager = CacheRedisConstants.CACHE_REDIS_MANAGER_NAME)
	@Override
	public void removeByUserId(Integer userId) {
		this.logger.info("removeByUserId,userId:{}", userId);
		this.userRepository.delete(userId);
	}
	
	@CacheEvict(value = CacheRedisConstants.USER_CACHE_NAME, 
			key = "#userId",
			cacheManager = CacheRedisConstants.CACHE_REDIS_MANAGER_NAME)
	@Override
	public void removeCacheByUserId(Integer userId) {
		this.logger.info("removeCacheByUserId,userId:{}", userId);
	}
	
	public class UserConverter implements Converter<User, UserDto> {

		@Override
		public UserDto convert(User user) {
			if (user != null) {
				UserDto userDto = new UserDto();
				BeanUtils.copyProperties(user, userDto);
				return userDto;
			} else {
				return null;
			}
		}
		
	}

}
