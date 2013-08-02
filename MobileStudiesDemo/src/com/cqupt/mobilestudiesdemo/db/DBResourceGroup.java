package com.cqupt.mobilestudiesdemo.db;

import java.util.ArrayList;

import com.cqupt.mobilestudiesdemo.entity.ResourceGroupEntity;

/**
 * Interface defining operations on internal application database
 * 
 * 
 * @author ap
 * @date 2012-11-28
 */
public interface DBResourceGroup {

	/**
	 * 添加资源集合
	 * 
	 * @param resourceGroupEntity
	 */
	public void addResourceGroup(ResourceGroupEntity resourceGroupEntity);

	/**
	 * 添加资源集合列表
	 * 
	 * @param resourceGroupEntities
	 */
	public void addResourceGroups(
			ArrayList<ResourceGroupEntity> resourceGroupEntities);

	/**
	 * 获取资源集合实体
	 * 
	 * @param resourceGroupID
	 * @return 资源集合实体，无数据返回null
	 */
	public ResourceGroupEntity getResourceGroup(int resourceGroupID);

	/**
	 * 通过资源子类型获取一定数量的资源集合
	 * 
	 * @param resourceSubTypeID
	 * @param index
	 *            从数据库第index行开始读取
	 * @param size
	 *            获取数据的条数
	 * @return 获取size数量的资源集合实体，无数据返回null
	 */
	public ArrayList<ResourceGroupEntity> getRGsFromRST(int resourceSubTypeID,
			int index, int size);

	/**
	 * 通过资源类型获取一定数量的资源集合
	 * 
	 * @param resourceTypeID
	 * @param index
	 *            从数据库第index行开始读取
	 * @param size
	 *            获取数据的条数
	 * @return 获取size数量的资源集合实体，无数据返回null
	 */
	public ArrayList<ResourceGroupEntity> getRGsFromRT(int resourceTypeID,
			int index, int size);

	/**
	 * 通过资源子类型获取所有资源集合
	 * 
	 * @param resourceSubTypeID
	 * @return 所有资源集合，无数据返回null
	 */
	public ArrayList<ResourceGroupEntity> getAllRGsFromRST(int resourceSubTypeID);

	/**
	 * 通过资源类型获取所有资源集合
	 * 
	 * @param resourceTypeID
	 * @return 所有资源集合，无数据返回null
	 */
	public ArrayList<ResourceGroupEntity> getAllRGsFromRT(int resourceTypeID);

	/**
	 * 更新资源集合
	 * 
	 * @param resourceGroupID
	 */
	public void updateResourceGroup(ResourceGroupEntity resourceGroupEntity);

	/**
	 * 删除资源集合
	 * 
	 * @param resourceGroupID
	 */
	public void deleteResourceGroup(int resourceGroupID);

	/**
	 * 通过资源子类型删除资源集合
	 * 
	 * @param resourceSubTypeID
	 */
	public void deleteRGsFromRST(int resourceSubTypeID);

	/**
	 * 通过资源类型删除资源集合
	 * 
	 * @param resourceTypeID
	 */
	public void deleteRGsFromRT(int resourceTypeID);

	/**
	 * 删除所有资源集合
	 */
	public void deleteAllResourceGroup();
}
