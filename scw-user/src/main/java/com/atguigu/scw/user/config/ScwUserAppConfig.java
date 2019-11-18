package com.atguigu.scw.user.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.atguigu.scw.user.utils.SmsTemplate;

@Configuration
public class ScwUserAppConfig {
	@ConfigurationProperties(prefix="sms")
	@Bean
	public SmsTemplate getSmsTemplate() {
		return new SmsTemplate();
	}
	@Bean
	public BCryptPasswordEncoder getBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
