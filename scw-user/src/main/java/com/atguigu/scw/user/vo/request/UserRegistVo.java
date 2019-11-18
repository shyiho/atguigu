package com.atguigu.scw.user.vo.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//描述浏览器注册时提交的用户信息
@Data
@ToString
public class UserRegistVo implements Serializable{
	private String loginacct;//手机号
	private String userpswd;
	private String email;
	private String code;
	private String usertype;//用户类型：0、个人，1、企业
}
