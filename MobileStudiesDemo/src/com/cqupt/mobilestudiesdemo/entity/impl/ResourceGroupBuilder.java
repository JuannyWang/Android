package com.cqupt.mobilestudiesdemo.entity.impl;

import org.ksoap2.serialization.SoapObject;

import com.cqupt.mobilestudiesdemo.entity.ResourceGroupEntity;

public class ResourceGroupBuilder extends SoapBuilder<ResourceGroupEntity> {

	@Override
	public ResourceGroupEntity build(SoapObject soapObject) {
		// TODO Auto-generated method stub
		ResourceGroupEntity resourceGroupEntity = new ResourceGroupEntity();
		resourceGroupEntity
				.setResourceGroupID(
						Integer.parseInt(soapObject
								.getPropertySafelyAsString("resourceGroupID")))
				.setResourceSubTypeID(
						Integer.parseInt(soapObject
								.getPropertySafelyAsString("resourceSubTypeID")))
				.setResourceTypeID(
						Integer.parseInt(soapObject
								.getPropertySafelyAsString("resourceTypeID")))
				.setResourceGroupName(
						soapObject
								.getPropertySafelyAsString("resourceGroupName"));
		return resourceGroupEntity;
	}
}
