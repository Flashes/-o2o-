package com.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.o2o.entity.ProductCategory;

public interface ProductCategoryDao {
	/*
	 * 通过店铺id查询店铺商品类别
	 */
	List<ProductCategory> queryProductCategoryList(long shopId);
	/*
	 * 商品类别的批量添加
	 * 返回值表示影响的行数
	 */
	int batchInsertProductCategory(List<ProductCategory> productCategoryList);
	/*
	 * 删除指定商品类别
	 */
	int deleteProductCategory(@Param("productCategoryId")long productCategory,@Param("shopId")long shopId);
}
