package com.o2o.service;

import java.util.List;

import com.o2o.dto.ImageHolder;
import com.o2o.dto.ProductExecution;
import com.o2o.entity.Product;
import com.o2o.exceptions.ProductOperationException;

public interface ProductService {
	/*
	 * 添加商品信息和商品图片
	 */
	ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList)
			throws ProductOperationException;

	/*
	 * 通过商品Id查询唯一的商品信息
	 */
	Product getProductById(long productId);

	/*
	 * 修改商品信息以及修改图片
	 */
	ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList)
			throws ProductOperationException;
	/*
	 * 查询商品列表并分页
	 */
	ProductExecution getProductList(Product productCondition,int pageIndex,int pageSize);
}
