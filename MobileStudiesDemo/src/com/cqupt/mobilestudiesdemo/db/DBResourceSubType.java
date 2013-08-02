package com.cqupt.mobilestudiesdemo.db;

import java.util.ArrayList;

import com.cqupt.mobilestudiesdemo.entity.ResourceSubTypeEntity;

/**
 * Interface defining operations on internal application database
 * 
 * 
 * @author ap
 * @date 2012-11-28
 */
public interface DBResourceSubType {
	/**
	 * 添加资源子类型
	 * 
	 * @param resourceSubTypeEntity
	 */
	public void addResourceSubType(ResourceSubTypeEntity resourceSubTypeEntity);

	/**
	 * 添加资源子类型列表
	 * 
	 * @param resourceSubTypeEntities
	 */
	public void addResourceSubTyepes(
			ArrayList<ResourceSubTypeEntity> resourceSubTypeEntities);

	/**
	 * 获取资源子类型实体
	 * 
	 * @param resourceSubTypeID
	 * @return 资源子类型实体，无数据返回null
	 */
	public ResourceSubTypeEntity getResourceSubType(int resourceSubTypeID);

	/**
	 * 获取一定数量的资源子类型实体
	 * 
	 * @param resourceTypeID
	 * @param index
	 *            从数据库第index行开始读取
	 * @param size
	 *            获取数据的条数
	 * @return 获取size数量的资源子类型实体，无数据返回null
	 */

	public ArrayList<ResourceSubTypeEntity> getResourceSubTypes(
			int resourceTypeID, int index, int size);

	/**
	 * 获取所有资源子类型实体
	 * 
	 * @param resourceTypeID
	 * @return 所有资源类型实体，无数据返回null
	 */

	public ArrayList<ResourceSubTypeEntity> getAllResourceSubTypes(
			int resourceTypeID);

	/**
	 * 更新资源子类型
	 * 
	 * @param resourceTypeID
	 */
	public void updateResourceSubType(
			ResourceSubTypeEntity resourceSubTypeEntity);

	/**
	 * 删除资源子类型
	 * 
	 * @param resourceSubTypeID
	 */
	public void deleteResourceSubType(int resourceSubTypeID);

	/**
	 * 根据resourceTypeID删除资源子类型
	 * 
	 * @param resourceTypeID
	 */
	public void deleteResourceSubTypes(int resourceTypeID);

	/**
	 * 删除所有资源子类型
	 */
	public void deleteAllResourceSubTypes();
}
