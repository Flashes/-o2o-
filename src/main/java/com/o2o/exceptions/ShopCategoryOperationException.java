package com.o2o.exceptions;

public class ShopCategoryOperationException extends RuntimeException{
	private static final long serialVersionUID = 5423986306645291467L;

	public ShopCategoryOperationException(String msg) {
		super(msg);
	}
}
