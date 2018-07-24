package com.zy.springcacheredistest.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.zy.springcacheredistest.enums.GenderType;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "USER")
public class User implements Serializable {
	
	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", 
		parameters = {
			@Parameter(name = "sequence_name", value = "SEQ_USER")
		}
	)
	@Column(name = "USER_ID", nullable = false)
	private Integer userId;
	
	@Column(name = "USER_NAME", nullable = true, length = 80)
	private String userName;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "BIRTH_DATE", nullable = true)
	private Date birthDate;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "GENDER_TYPE", nullable = true, length = 10)
	private GenderType genderType;
	
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME", nullable = true)
	private Date createTime;
	
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATE_TIME", nullable = true)
	private Date lastUpdateTime;
	
	@Version
	@Column(name = "JPA_VERSION", nullable = true)
	private Integer jpaVersion;

}
