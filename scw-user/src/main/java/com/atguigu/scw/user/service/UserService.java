package com.atguigu.scw.user.service;


import java.util.List;

import com.atguigu.scw.common.vo.response.UserResponseVo;
import com.atguigu.scw.user.bean.TMemberAddress;
import com.atguigu.scw.user.vo.request.UserRegistVo;

public interface UserService {

	void saveUser(UserRegistVo vo);

	UserResponseVo doLogin(String loginacct, String userpswd);

	List<TMemberAddress> getAddress(Integer id);

}
