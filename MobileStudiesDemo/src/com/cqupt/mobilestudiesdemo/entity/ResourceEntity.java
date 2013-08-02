package com.cqupt.mobilestudiesdemo.entity;

import java.io.Serializable;

/**
 * 资源实体类<br/>
 * JQuery的链式POJO写法
 * 
 * @author ap
 * @date 2012-11-28
 */
public class ResourceEntity implements Serializable {
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -8881291522159195446L;

	private int resourceID;
	private int resourceGroupID;
	private int resourceSubTypeID;
	private int resourceTypeID;
	private String resourceTitle;
	private String resourceComment;
	private String resourcePath;
	private String resourceDescript;
	private String resourceCreateDate;

	public int getResourceID() {
		return resourceID;
	}

	public ResourceEntity setResourceID(int resourceID) {
		this.resourceID = resourceID;
		return this;
	}

	public int getResourceGroupID() {
		return resourceGroupID;
	}

	public ResourceEntity setResourceGroupID(int resourceGroupID) {
		this.resourceGroupID = resourceGroupID;
		return this;
	}

	public int getResourceSubTypeID() {
		return resourceSubTypeID;
	}

	public ResourceEntity setResourceSubTypeID(int resourceSubTypeID) {
		this.resourceSubTypeID = resourceSubTypeID;
		return this;
	}

	public int getResourceTypeID() {
		return resourceTypeID;
	}

	public ResourceEntity setResourceTypeID(int resourceTypeID) {
		this.resourceTypeID = resourceTypeID;
		return this;
	}

	public String getResourceTitle() {
		return resourceTitle;
	}

	public ResourceEntity setResourceTitle(String resourceTitle) {
		this.resourceTitle = resourceTitle;
		return this;
	}

	public String getResourceComment() {
		return resourceComment;
	}

	public ResourceEntity setResourceComment(String resourceComment) {
		this.resourceComment = resourceComment;
		return this;
	}

	public String getResourcePath() {
		return resourcePath;
	}

	public ResourceEntity setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
		return this;
	}

	public String getResourceDescript() {
		return resourceDescript;
	}

	public ResourceEntity setResourceDescript(String resourceDescript) {
		this.resourceDescript = resourceDescript;
		return this;
	}

	public String getResourceCreateDate() {
		return resourceCreateDate;
	}

	public ResourceEntity setResourceCreateDate(String resourceCreateDate) {
		this.resourceCreateDate = resourceCreateDate;
		return this;
	}

}