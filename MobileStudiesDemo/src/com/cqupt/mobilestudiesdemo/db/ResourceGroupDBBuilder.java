package com.cqupt.mobilestudiesdemo.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.cqupt.mobilestudiesdemo.entity.ResourceGroupEntity;

public class ResourceGroupDBBuilder extends
		DataBaseBuilder<ResourceGroupEntity> {

	@Override
	public ResourceGroupEntity build(Cursor query) {
		// TODO Auto-generated method stub
		int columnResourGroupID = query.getColumnIndex("resourceGroupID");
		int columnResourceSubTypeID = query.getColumnIndex("resourceSubTypeID");
		int columnResourceTypeID = query.getColumnIndex("resourceTypeID");
		int columnResourceGroupName = query.getColumnIndex("resourceGroupName");
		ResourceGroupEntity resourceGroupEntity = new ResourceGroupEntity();
		resourceGroupEntity
				.setResourceGroupID(query.getInt(columnResourGroupID))
				.setResourceSubTypeID(query.getInt(columnResourceSubTypeID))
				.setResourceTypeID(query.getInt(columnResourceTypeID))
				.setResourceGroupName(query.getString(columnResourceGroupName));
		return resourceGroupEntity;
	}

	@Override
	public ContentValues deconstruct(ResourceGroupEntity resourceGroupEntity) {
		// TODO Auto-generated method stub
		ContentValues contentValues = new ContentValues();
		contentValues.put("resourceGroupID",
				resourceGroupEntity.getResourceGroupID());
		contentValues.put("resourceSubTypeID",
				resourceGroupEntity.getResourceSubTypeID());
		contentValues.put("resourceTypeID",
				resourceGroupEntity.getResourceTypeID());
		contentValues.put("resourceGroupName",
				resourceGroupEntity.getResourceGroupName());
		return contentValues;
	}

}
