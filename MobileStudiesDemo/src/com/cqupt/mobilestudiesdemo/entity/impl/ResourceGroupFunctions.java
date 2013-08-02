package com.cqupt.mobilestudiesdemo.entity.impl;

import java.util.ArrayList;

import org.ksoap2.serialization.SoapObject;

import com.cqupt.mobilestudiesdemo.entity.ResourceGroupEntity;

public class ResourceGroupFunctions {
	public static ArrayList<ResourceGroupEntity> getResourceGroupEntities(
			SoapObject result) {
		int n = result.getPropertyCount();
		ArrayList<ResourceGroupEntity> resourceGroupEntities = new ArrayList<ResourceGroupEntity>();
		ResourceGroupBuilder resourceGroupBuilder = new ResourceGroupBuilder();
		for (int i = 0; i < n; ++i) {
			ResourceGroupEntity resourceGroupEntity = resourceGroupBuilder
					.build((SoapObject) result.getProperty(i));
			resourceGroupEntities.add(resourceGroupEntity);
		}
		return resourceGroupEntities;
	}
}
