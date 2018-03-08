package com.o2o.dto;

/*
 * 封装json对象
 */
public class Result<T> {
	private boolean success;// 是否成功标志
	private T data;// 成功时返回的数据
	private String errorMsg;// 错误信息
	private int errorCode;
	// 成功时的构造器
	public Result(boolean success,T data){
		this.success=success;
		this.data=data;
	}
	//错误时的构造器
	public Result(boolean success,int errorCode,String errorMsg){
		this.success=success;
		this.errorCode=errorCode;
		this.errorMsg=errorMsg;
	}
	public boolean isSuccess(){
		return success;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
