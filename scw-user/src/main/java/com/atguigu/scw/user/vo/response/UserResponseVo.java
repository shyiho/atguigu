package com.atguigu.scw.user.vo.response;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserResponseVo implements Serializable{
	//登录成功后 用户信息存在redis中的key
	private String accesstoken;
	//页面中需要显示的信息
	private Integer id;

	private String loginacct;

	private String username;

	private String email;

	private String authstatus;

	private String usertype;

	private String realname;

	private String cardnum;

	private String accttype;
}
