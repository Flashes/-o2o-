package com.o2o.service;

import java.io.InputStream;

import com.o2o.entity.Shop;
import com.o2o.exceptions.ShopOperationException;
import com.o2o.dto.ImageHolder;
import com.o2o.dto.ShopExecution;

public interface ShopService {
	/*
	 * 获取店铺列表
	 */
	ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);
	/*
	 * 通过店铺id获取店铺信息
	 */
	Shop getByShopId(long shopId);
	/*
	 * 更新店铺信息，包括对图片的处理
	 */
	ShopExecution modifyShop(Shop shop,ImageHolder thumbnail)throws ShopOperationException;
	ShopExecution addShop(Shop shop, ImageHolder thumbnail);
}
