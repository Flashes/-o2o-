package com.o2o.dao;

import java.util.List;

import com.o2o.entity.ProductImg;

public interface ProductImgDao {
	/*
	 * 批量插入图片
	 */
	int batchInsertProductImg(List<ProductImg> productImgList);
	/*
	 * 删除指定商品下的所有详情图
	 */
	int deleteProductImgByProductId(long productId);
	/**
	 * 列出某个商品的详情图列表
	 * 
	 * @param productId
	 * @return
	 */
	List<ProductImg> queryProductImgList(long productId);
}
