package com.o2o.dao;



import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.o2o.base.BaseTest;
import com.o2o.entity.Area;

public class AreaDaoTest extends BaseTest{
@Autowired
private AreaDao areaDao;
@Test
public void testQueryArea(){
	List<Area> areaList=areaDao.queryArea();
	//如果返回的areaList大小为2则通过测试
	assertEquals(2,areaList.size());
}
}
