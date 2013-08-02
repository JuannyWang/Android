package com.cqupt.mobilestudiesdemo.entity;

import java.io.Serializable;

/**
 * 资源子类型实体类<br/>
 * JQuery的链式POJO写法
 * 
 * @author ap
 * @date 2012-11-28
 */
public class ResourceSubTypeEntity implements Serializable {
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 1L;
	private int resourceSubTypeID;
	private int resourceTypeID;
	private String resourceSubTypeName;

	public int getResourceSubTypeID() {
		return resourceSubTypeID;
	}

	public ResourceSubTypeEntity setResourceSubTypeID(int resourceSubTypeID) {
		this.resourceSubTypeID = resourceSubTypeID;
		return this;
	}

	public int getResourceTypeID() {
		return resourceTypeID;
	}

	public ResourceSubTypeEntity setResourceTypeID(int resourceTypeID) {
		this.resourceTypeID = resourceTypeID;
		return this;
	}

	public String getResourceSubTypeName() {
		return resourceSubTypeName;
	}

	public ResourceSubTypeEntity setResourceSubTypeName(
			String resourceSubTypeName) {
		this.resourceSubTypeName = resourceSubTypeName;
		return this;
	}

}
