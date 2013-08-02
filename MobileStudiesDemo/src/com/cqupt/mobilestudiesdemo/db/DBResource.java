package com.cqupt.mobilestudiesdemo.db;

import java.util.ArrayList;

import com.cqupt.mobilestudiesdemo.entity.ResourceEntity;

/**
 * Interface defining operations on internal application database
 * 
 * 
 * @author ap
 * @date 2012-11-28
 */
public interface DBResource {
	/**
	 * 添加资源
	 * 
	 * @param resourceEntity
	 */
	public void addResource(ResourceEntity resourceEntity);

	/**
	 * 添加资源列表
	 * 
	 * @param resourceEntities
	 */
	public void addResource(ArrayList<ResourceEntity> resourceEntities);

	/**
	 * 获取资源实体
	 * 
	 * @param resourceID
	 * @return 资源实体，无数据返回null
	 */
	public ResourceEntity getResource(int resourceID);

	/**
	 * 通过资源集合获取一定数量的资源实体
	 * 
	 * @param resourceGroupID
	 * @param index
	 *            从数据库第index行开始读取
	 * @param size
	 *            获取数据的条数
	 * @return 获取size数量的资源实体，无数据返回null
	 */
	public ArrayList<ResourceEntity> getResourcesFromRG(int resourceGroupID,
			int index, int size);

	/**
	 * 通过资源子类型获取一定数量的资源实体
	 * 
	 * @param resourceSubTypeID
	 * @param index
	 *            从数据库第index行开始读取
	 * @param size
	 *            获取数据的条数
	 * @return 获取size数量的资源实体，无数据返回null
	 */
	public ArrayList<ResourceEntity> getResourcesFromRST(int resourceSubTypeID,
			int index, int size);

	/**
	 * 通过资源类型获取一定数量的资源实体
	 * 
	 * @param resourceTypeID
	 * @param index
	 *            从数据库第index行开始读取
	 * @param size
	 *            获取数据的条数
	 * @return 获取size数量的资源实体，无数据返回null
	 */
	public ArrayList<ResourceEntity> getResourcesFromRT(int resourceTypeID,
			int index, int size);

	/**
	 * 通过资源集合获取所有资源实体
	 * 
	 * @param resourceGroupID
	 * @return 所有资源实体，无数据返回null
	 */
	public ArrayList<ResourceEntity> getAllResourcesFromRG(int resourceGroupID);

	/**
	 * 通过资源子类型获取所有资源实体
	 * 
	 * @param resourceSubTypeID
	 * @return 所有资源实体，无数据返回null
	 */
	public ArrayList<ResourceEntity> getAllResourcesFromRST(
			int resourceSubTypeID);

	/**
	 * 通过资源类型获取所有资源实体
	 * 
	 * @param resourceTypeID
	 * @return 所有资源实体，无数据返回null
	 */
	public ArrayList<ResourceEntity> getAllResourcesFromRT(int resourceTypeID);

	/**
	 * 更新资源实体
	 * 
	 * @param resourceEntity
	 */
	public void updateResource(ResourceEntity resourceEntity);

	/**
	 * 删除资源
	 * 
	 * @param resourceID
	 */
	public void deleteResource(int resourceID);

	/**
	 * 通过资源集合删除资源
	 * 
	 * @param resourceGroupID
	 */
	public void deleteResourcesFromRG(int resourceGroupID);

	/**
	 * 通过资源子类型删除资源
	 * 
	 * @param resourceSubTypeID
	 */
	public void deleteResourcesFromRST(int resourceSubTypeID);

	/**
	 * 通过资源类型删除资源
	 * 
	 * @param resourceTypeID
	 */
	public void deleteResourcesFromRT(int resourceTypeID);

	/**
	 * 删除所有资源子类型
	 */
	public void deleteAllResource();
}
