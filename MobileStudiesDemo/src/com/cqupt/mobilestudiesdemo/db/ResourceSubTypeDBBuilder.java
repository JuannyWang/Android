package com.cqupt.mobilestudiesdemo.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.cqupt.mobilestudiesdemo.entity.ResourceSubTypeEntity;

public class ResourceSubTypeDBBuilder extends
		DataBaseBuilder<ResourceSubTypeEntity> {

	@Override
	public ResourceSubTypeEntity build(Cursor query) {
		// TODO Auto-generated method stub
		int columnResourceSubTypeID = query.getColumnIndex("resourceSubTypeID");
		int columnResourceTypeID = query.getColumnIndex("resourceTypeID");
		int columnResourceSubTypeName = query
				.getColumnIndex("resourceSubTypeName");
		ResourceSubTypeEntity resourceSubTypeEntity = new ResourceSubTypeEntity();
		resourceSubTypeEntity
				.setResourceSubTypeID(query.getInt(columnResourceSubTypeID))
				.setResourceTypeID(query.getInt(columnResourceTypeID))
				.setResourceSubTypeName(
						query.getString(columnResourceSubTypeName));
		return resourceSubTypeEntity;
	}

	@Override
	public ContentValues deconstruct(ResourceSubTypeEntity resourceSubTypeEntity) {
		// TODO Auto-generated method stub
		ContentValues contentValues = new ContentValues();
		contentValues.put("resourceSubTypeID",
				resourceSubTypeEntity.getResourceSubTypeID());
		contentValues.put("resourceTypeID",
				resourceSubTypeEntity.getResourceTypeID());
		contentValues.put("resourceSubTypeName",
				resourceSubTypeEntity.getResourceSubTypeName());
		return contentValues;
	}

}
