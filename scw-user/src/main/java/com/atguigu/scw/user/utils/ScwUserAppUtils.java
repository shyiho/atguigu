package com.atguigu.scw.user.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;

public class ScwUserAppUtils {
	private static Gson gson=new Gson();
	//将对象转为Json字符串的方法
	public static String obj2JsonStr(Object obj) {
		return gson.toJson(obj);
	}
	//正则验证手机号码
	public static boolean isPhone(String phone) {
	    String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
	    if (phone.length() != 11) {
	        return false;
	    } else {
	        Pattern p = Pattern.compile(regex);
	        Matcher m = p.matcher(phone);
	        boolean isMatch = m.matches();
	        return isMatch;
	    }
	}
}
