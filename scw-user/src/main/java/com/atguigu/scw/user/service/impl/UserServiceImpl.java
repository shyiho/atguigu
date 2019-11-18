package com.atguigu.scw.user.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.atguigu.scw.common.vo.response.UserResponseVo;
import com.atguigu.scw.user.bean.TMember;
import com.atguigu.scw.user.bean.TMemberAddress;
import com.atguigu.scw.user.bean.TMemberAddressExample;
import com.atguigu.scw.user.bean.TMemberExample;
import com.atguigu.scw.user.consts.UserAppConsts;
import com.atguigu.scw.user.exception.UserException;
import com.atguigu.scw.user.mapper.TMemberAddressMapper;
import com.atguigu.scw.user.mapper.TMemberMapper;
import com.atguigu.scw.user.service.UserService;
import com.atguigu.scw.user.utils.ScwUserAppUtils;
import com.atguigu.scw.user.vo.request.UserRegistVo;
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	BCryptPasswordEncoder encoder;
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	@Autowired
	TMemberMapper memberMapper;
	@Autowired
	TMemberAddressMapper memberAddress;
	@Override
	public void saveUser(UserRegistVo vo) {
		//1、判断验证码【是否失效，是否正确】
		String phoneCodeKey = UserAppConsts.PHONE_CODE_PREFIX + vo.getLoginacct()+UserAppConsts.PHONE_CODE_SUFFIX;
		String string = stringRedisTemplate.opsForValue().get(phoneCodeKey);
		Boolean flag = stringRedisTemplate.hasKey(phoneCodeKey);
		if(StringUtils.isEmpty(vo.getCode())) {
			throw new UserException("请输入验证码");
		}
		if(!flag) {
			throw new UserException("验证码过期");
		}
		if(!(string.equals(vo.getCode()))) {
			throw new UserException("验证码错误");
		}
		//2、将UserRegistVo转为TMember对象，并初始默认值
		TMember member = new TMember();
		BeanUtils.copyProperties(vo, member);
		member.setUsername(member.getLoginacct());
		member.setAuthstatus("0");
		//3、调用mapper将数据存到数据库中
		TMemberExample example = new TMemberExample();
		//3.1、验证账号和邮箱的唯一性
		example.createCriteria().andLoginacctEqualTo(member.getLoginacct());
		long l = memberMapper.countByExample(example);
		if(l>0) {
			throw new UserException("用户名已被占用");
		}
		example.clear();
		example.createCriteria().andEmailEqualTo(member.getEmail());
		 l = memberMapper.countByExample(example);
		 if(l>0) {
			 throw new UserException("邮箱已被占用");
		 }
		//3.2、保存
		 member.setUserpswd(encoder.encode(member.getUserpswd()));
		 memberMapper.insertSelective(member);
		//4、给出返回值
		//删除redis中使用过的验证码
		 stringRedisTemplate.delete(phoneCodeKey);
	}
	@Override
	public UserResponseVo doLogin(String loginacct, String userpswd) {
		TMemberExample example=new TMemberExample();
		example.createCriteria().andLoginacctEqualTo(loginacct);
		List<TMember> list = memberMapper.selectByExample(example);
		if(CollectionUtils.isEmpty(list)||list.size()>1) {
			throw new UserException("账号不存在");
		}
		TMember member = list.get(0);
		boolean flag = encoder.matches(userpswd, member.getUserpswd());
		if(!flag) {
			throw new UserException("密码错误");
		}
		//member转换为vo
		UserResponseVo vo = new UserResponseVo();
		BeanUtils.copyProperties(member, vo);
		//创建唯一的token
		String token=UUID.randomUUID().toString().replace("-", "");
		token=UserAppConsts.USER_LOGIN_TOKEN_PREFIX+token;
		vo.setAccesstoken(token); 
		//将登录成功的信息存到redis中
		stringRedisTemplate.opsForValue().set(token, ScwUserAppUtils.obj2JsonStr(vo), 7, TimeUnit.DAYS);
		return vo;
	}
	@Override
	public List<TMemberAddress> getAddress(Integer id) {
		TMemberAddressExample example = new TMemberAddressExample();
		example.createCriteria().andMemberidEqualTo(id);
		List<TMemberAddress> list = memberAddress.selectByExample(example);
		return list;
	}
 
}
