package com.o2o.util;

import javax.servlet.http.HttpServletRequest;

/*
 * 负责处理HttpServletRequest参数
 */
public class HttpServletRequestUtil {
	public static int getInt(HttpServletRequest request, String key) {
		// request.getParameter(key)返回字符串类型转换成整型
		try {
			return Integer.decode(request.getParameter(key));
		} catch (Exception e) {
			return -1;
		}
	}

	// 将key转换成长整型
	public static long getLong(HttpServletRequest request, String key) {
		// request.getParameter(key)返回字符串类型转换成整型
		try {
			return Long.valueOf(request.getParameter(key));
		} catch (Exception e) {
			return -1;
		}
	}

	public static Double getDouble(HttpServletRequest request, String key) {
		// request.getParameter(key)返回字符串类型转换成整型
		try {
			return Double.valueOf(request.getParameter(key));
		} catch (Exception e) {
			return -1d;
		}
	}

	public static boolean getBoolean(HttpServletRequest request, String key) {
		// request.getParameter(key)返回字符串类型转换成整型
		try {
			return Boolean.valueOf(request.getParameter(key));
		} catch (Exception e) {
			return false;
		}
	}

	public static String getString(HttpServletRequest request, String key) {
		// request.getParameter(key)返回字符串类型转换成整型
		try {
			String result = request.getParameter(key);
			if (result != null) {
				result = result.trim();
			}
			if (result.equals("")) {
				return null;
			}
			return result;
		} catch (Exception e) {
			return null;
		}
	}
}
