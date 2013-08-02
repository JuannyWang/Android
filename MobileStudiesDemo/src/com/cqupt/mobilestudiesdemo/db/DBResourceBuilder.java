package com.cqupt.mobilestudiesdemo.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.cqupt.mobilestudiesdemo.entity.ResourceEntity;

public class DBResourceBuilder extends DataBaseBuilder<ResourceEntity> {

	@Override
	public ResourceEntity build(Cursor query) {
		// TODO Auto-generated method stub
		int columnResourceID = query.getColumnIndex("resourceID");
		int columnResourGroupID = query.getColumnIndex("resourceGroupID");
		int columnResourceSubTypeID = query.getColumnIndex("resourceSubTypeID");
		int columnResourceTypeID = query.getColumnIndex("resourceTypeID");
		int columnResourceTitle = query.getColumnIndex("resourceTitle");
		int columnResourceComment = query.getColumnIndex("resourceComment");
		int columnResourcePath = query.getColumnIndex("resourcePath");
		int columnResourceDescript = query.getColumnIndex("resourceDescript");
		int columnResourceCreateDate = query
				.getColumnIndex("resourceCreateDate");
		ResourceEntity resourceEntity = new ResourceEntity();
		resourceEntity
				.setResourceID(query.getInt(columnResourceID))
				.setResourceGroupID(columnResourGroupID)
				.setResourceSubTypeID(query.getInt(columnResourceSubTypeID))
				.setResourceTypeID(query.getInt(columnResourceTypeID))
				.setResourceTitle(query.getString(columnResourceTitle))
				.setResourceComment(query.getString(columnResourceComment))
				.setResourcePath(query.getString(columnResourcePath))
				.setResourceDescript(query.getString(columnResourceDescript))
				.setResourceCreateDate(
						query.getString(columnResourceCreateDate));
		return resourceEntity;
	}

	@Override
	public ContentValues deconstruct(ResourceEntity resourceEntity) {
		// TODO Auto-generated method stub
		ContentValues contentValues = new ContentValues();
		contentValues.put("resourceID", resourceEntity.getResourceID());
		contentValues.put("resourceGroupID",
				resourceEntity.getResourceGroupID());
		contentValues.put("resourceSubTypeID",
				resourceEntity.getResourceSubTypeID());
		contentValues.put("resourceTypeID", resourceEntity.getResourceTypeID());
		contentValues.put("resourceTitle", resourceEntity.getResourceTitle());
		contentValues.put("resourceComment",
				resourceEntity.getResourceComment());
		contentValues.put("resourcePath", resourceEntity.getResourcePath());
		contentValues.put("resourceDescript",
				resourceEntity.getResourceDescript());
		contentValues.put("resourceCreateDate",
				resourceEntity.getResourceCreateDate());
		return contentValues;
	}

}
