package com.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.o2o.entity.Shop;

public interface ShopDao {
	/**
	 * 分页查询店铺，可输入的条件有：店铺名(模糊匹配)，店铺状态，店铺类别，区域id，owner
	 * @param shopCondition
	 * @param rowIndex:从第几行开始取
	 * @param pageSize:返回的条数
	 * @return
	 */
	List<Shop> queryShopList(@Param("shopCondition")Shop shopCondition,
			@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);
	/*
	 * 返回queryShopList总数
	 */
	int queryShopCount(@Param("shopCondition")Shop shopCondition);
	/*
	 * 通过shopId查询店铺
	 */
	Shop queryByShopId(long shopId);
	/*
	 * 新增店铺
	 */
	int insertShop(Shop shop);
	/*
	 * 更新店铺
	 */
	int updateShop(Shop shop);
}
