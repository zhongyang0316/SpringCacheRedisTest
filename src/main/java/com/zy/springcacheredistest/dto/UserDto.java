package com.zy.springcacheredistest.dto;

import java.io.Serializable;
import java.util.Date;

import com.zy.springcacheredistest.enums.GenderType;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class UserDto implements Serializable {
	
	private Integer userId;
	
	private String userName;
	
	private Date birthDate;
	
	private GenderType genderType;
	
	private Date createTime;
	
	private Date lastUpdateTime;
	
}
