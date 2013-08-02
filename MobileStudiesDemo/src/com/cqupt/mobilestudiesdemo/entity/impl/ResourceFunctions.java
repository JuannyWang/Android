package com.cqupt.mobilestudiesdemo.entity.impl;

import java.util.ArrayList;

import org.ksoap2.serialization.SoapObject;

import com.cqupt.mobilestudiesdemo.entity.ResourceEntity;

public class ResourceFunctions {
	public static ArrayList<ResourceEntity> getResourceEntities(
			SoapObject result) {
		int n = result.getPropertyCount();
		ArrayList<ResourceEntity> resourceEntities = new ArrayList<ResourceEntity>();
		ResourceBuilder resourceBuilder = new ResourceBuilder();
		for (int i = 0; i < n; ++i) {
			ResourceEntity resourceEntity = resourceBuilder
					.build((SoapObject) result.getProperty(i));
			resourceEntities.add(resourceEntity);
		}
		return resourceEntities;
	}
}
