package com.cqupt.mobilestudiesdemo.entity.impl;

import java.util.ArrayList;

import org.ksoap2.serialization.SoapObject;

import com.cqupt.mobilestudiesdemo.entity.ResourceSubTypeEntity;

public class ResourceSubTypeFunctions {
	public static ArrayList<ResourceSubTypeEntity> getResourceSubTypeEntities(
			SoapObject result) {
		int n = result.getPropertyCount();
		ArrayList<ResourceSubTypeEntity> resourceSubTypeEntities = new ArrayList<ResourceSubTypeEntity>();
		ResourceSubTypeBuilder resourceSubTypeBuilder = new ResourceSubTypeBuilder();
		for (int i = 0; i < n; ++i) {
			ResourceSubTypeEntity resourceSubTypeEntity = resourceSubTypeBuilder
					.build((SoapObject) result.getProperty(i));
			resourceSubTypeEntities.add(resourceSubTypeEntity);
		}
		return resourceSubTypeEntities;
	}
}
