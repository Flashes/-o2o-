package com.o2o.service;

import com.o2o.entity.PersonInfo;

public interface PersonInfoService {
	/*
	 * 根据用户Id获取personInfo信息
	 */
	PersonInfo getPersonInfoById(Long userId);
}
