package com.o2o.service;

import java.io.IOException;
import java.util.List;

import com.o2o.entity.HeadLine;

public interface HeadLineService {
	public static final String HLLISTKEY = "headlinelist";
	/*
	 * 根据传入的条件返回指定的头条列表
	 */
	List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException;
}
