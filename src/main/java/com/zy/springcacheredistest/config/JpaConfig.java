package com.zy.springcacheredistest.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * JPA配置.
 * @author zhongyang
 *
 */
@Configuration
@EnableJpaAuditing
@EnableTransactionManagement
@EntityScan({"com.zy.springcacheredistest.domain"})
@EnableJpaRepositories({"com.zy.springcacheredistest.domain"})
public class JpaConfig {

}
