package com.o2o.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.o2o.entity.LocalAuth;

public interface LocalAuthDao {
	/*
	 * 登录
	 */
	LocalAuth queryLocalByUserNameAndPwd(@Param("username")String username,@Param("password")String password);
	/*
	 * 通过用户id查询对应localauth
	 */
	LocalAuth queryLocalByUserId(@Param("userId") long userId);
	/*
	 * 添加平台帐号
	 */
	int insertLocalAuth(LocalAuth localAuth);
	/*
	 * 通过userId,username,password更改密码
	 */
	int updateLocalAuth(@Param("userId") Long userId, @Param("username") String username,
			@Param("password") String password, @Param("newPassword") String newPassword,
			@Param("lastEditTime") Date lastEditTime);
}
