package com.o2o.service;

import java.util.List;

import com.o2o.dto.ProductCategoryExecution;
import com.o2o.entity.ProductCategory;
import com.o2o.exceptions.ProductCategoryOperationException;

public interface ProductCategoryService {
	/*
	 * 查询某个店铺下所有商品类别信息
	 */
	List<ProductCategory> getProductCategoryList(long shopId);

	/*
	 * 批量插入商品信息
	 */
	ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
			throws ProductCategoryOperationException;

	/*
	 * 删除商品信息
	 * 将shopId置为空，并删除该商品类别
	 */
	ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
			throws ProductCategoryOperationException;
}
