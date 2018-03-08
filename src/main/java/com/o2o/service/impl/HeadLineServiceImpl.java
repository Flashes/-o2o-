package com.o2o.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.o2o.cache.JedisUtil;
import com.o2o.dao.HeadLineDao;
import com.o2o.entity.HeadLine;
import com.o2o.exceptions.HeadLineOperationException;
import com.o2o.service.HeadLineService;

@Service
public class HeadLineServiceImpl implements HeadLineService {
	@Autowired
	private HeadLineDao headLineDao;
	@Autowired
	private JedisUtil.Keys jedisKeys;
	@Autowired
	private JedisUtil.Strings jedisStrings;
	private static Logger logger = LoggerFactory.getLogger(HeadLineServiceImpl.class);

	@Transactional
	public List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException {
		// TODO Auto-generated method stub
		// 定义redis的key前缀
		String key = HLLISTKEY;
		// 定义接收对象
		List<HeadLine> headLineList = null;
		// 定义jackson数据转换操作类
		ObjectMapper mapper = new ObjectMapper();
		// 拼接出redis的key
		if (headLineCondition != null && headLineCondition.getEnableStatus() != null) {
			key = key + "_" + headLineCondition.getEnableStatus();
		}
		// 判断key是否存在
				if (!jedisKeys.exists(key)) {
					// 若不存在，则从数据库里面取出相应数据
					headLineList = headLineDao.queryHeadLine(headLineCondition);
					// 将相关的实体类集合转换成string,存入redis里面对应的key中
					String jsonString;
					try {
						jsonString = mapper.writeValueAsString(headLineList);
					} catch (JsonProcessingException e) {
						e.printStackTrace();
						logger.error(e.getMessage());
						throw new HeadLineOperationException(e.getMessage());
					}
					jedisStrings.set(key, jsonString);
				} else {
					// 若存在，则直接从redis里面取出相应数据
					String jsonString = jedisStrings.get(key);
					// 指定要将string转换成的集合类型
					JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, HeadLine.class);
					try {
						// 将相关key对应的value里的的string转换成对象的实体类集合
						headLineList = mapper.readValue(jsonString, javaType);
					} catch (JsonParseException e) {
						e.printStackTrace();
						logger.error(e.getMessage());
						throw new HeadLineOperationException(e.getMessage());
					} catch (JsonMappingException e) {
						e.printStackTrace();
						logger.error(e.getMessage());
						throw new HeadLineOperationException(e.getMessage());
					} catch (IOException e) {
						e.printStackTrace();
						logger.error(e.getMessage());
						throw new HeadLineOperationException(e.getMessage());
					}
				}
		return headLineList;
	}

}
