package com.o2o.util;

import javax.servlet.http.HttpServletRequest;

public class CodeUtil {
	public static boolean checkVerifyCode(HttpServletRequest request){
		//生成的验证码
		String verifyCodeExpected=(String)request.getSession()
				.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		//实际输入的验证码
		String verifyCodeActual=HttpServletRequestUtil.getString(request,"verifyCodeActual");
		if(verifyCodeActual==null || !verifyCodeExpected.equals(verifyCodeActual)){
			return false;
		}
		return true;
	}
}
