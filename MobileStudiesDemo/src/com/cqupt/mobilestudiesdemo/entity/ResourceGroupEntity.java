package com.cqupt.mobilestudiesdemo.entity;

import java.io.Serializable;

/**
 * 资源集合实体类<br/>
 * JQuery的链式POJO写法
 * 
 * @author ap
 * @date 2012-11-28
 */
public class ResourceGroupEntity implements Serializable {
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 1L;
	private int resourceGroupID;
	private int resourceSubTypeID;
	private int resourceTypeID;
	private String resourceGroupName;

	public int getResourceGroupID() {
		return resourceGroupID;
	}

	public ResourceGroupEntity setResourceGroupID(int resourceGroupID) {
		this.resourceGroupID = resourceGroupID;
		return this;
	}

	public int getResourceSubTypeID() {
		return resourceSubTypeID;
	}

	public ResourceGroupEntity setResourceSubTypeID(int resourceSubTypeID) {
		this.resourceSubTypeID = resourceSubTypeID;
		return this;
	}

	public int getResourceTypeID() {
		return resourceTypeID;
	}

	public ResourceGroupEntity setResourceTypeID(int resourceTypeID) {
		this.resourceTypeID = resourceTypeID;
		return this;
	}

	public String getResourceGroupName() {
		return resourceGroupName;
	}

	public ResourceGroupEntity setResourceGroupName(String resourceGroupName) {
		this.resourceGroupName = resourceGroupName;
		return this;
	}

}
