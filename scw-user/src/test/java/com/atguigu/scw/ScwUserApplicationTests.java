package com.atguigu.scw;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.atguigu.scw.user.utils.HttpUtils;
import com.atguigu.scw.user.utils.SmsTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScwUserApplicationTests {

	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	DataSource dataSource;
	@Autowired
	StringRedisTemplate redisTemplate;

	@Test
	public void contextLoads() {
		logger.debug("数据源{}", dataSource);
		logger.debug("redis连接{}", redisTemplate);
	}
	@Autowired
	SmsTemplate smsTemplate;
	@Test
	public void testSms() {
	//	String substring = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
		//smsTemplate.testSms("13797330069", substring, "TP1711063");
	}

}
