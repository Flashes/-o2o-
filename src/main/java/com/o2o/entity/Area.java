package com.o2o.entity;

import java.util.Date;

public class Area {
	// id，这里用引用类型
	// 原因是当我们使用基本类型时，如果该值为空，则基本类型会给我们赋一个初始值
	// 在一些情况下，我们不想要这个初始值，空即是null,所以使用引用类型
	private Integer areaId;
	// 名称
	private String areaName;
	// 权重
	private Integer priority;
	// 创建时间
	private Date createTime;
	// 修改时间
	private Date lastEditTime;

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastEditTime() {
		return lastEditTime;
	}

	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
}
