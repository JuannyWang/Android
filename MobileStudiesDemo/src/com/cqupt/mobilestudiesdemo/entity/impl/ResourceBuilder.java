package com.cqupt.mobilestudiesdemo.entity.impl;

import org.ksoap2.serialization.SoapObject;

import com.cqupt.mobilestudiesdemo.entity.ResourceEntity;

public class ResourceBuilder extends SoapBuilder<ResourceEntity> {
	@Override
	public ResourceEntity build(SoapObject soapObject) {
		// TODO Auto-generated method stub
		ResourceEntity resourceEntity = new ResourceEntity();
		resourceEntity
				.setResourceID(
						Integer.parseInt(soapObject
								.getPropertySafelyAsString("resourceID")))
				.setResourceGroupID(
						Integer.parseInt(soapObject
								.getPropertySafelyAsString("resourceGroupID")))
				.setResourceSubTypeID(
						Integer.parseInt(soapObject
								.getPropertySafelyAsString("resourceSubTypeID")))
				.setResourceTypeID(
						Integer.parseInt(soapObject
								.getPropertySafelyAsString("resourceTypeID")))
				.setResourceTitle(
						soapObject.getPropertySafelyAsString("resourceTitle"))
				.setResourceComment(
						soapObject.getPropertySafelyAsString("resourceComment"))
				.setResourcePath(
						soapObject.getPropertySafelyAsString("resourcePath"))
				.setResourceDescript(
						soapObject
								.getPropertySafelyAsString("resourceDescript"))
				.setResourceCreateDate(
						soapObject
								.getPropertySafelyAsString("resourceCreateDate"));
		return resourceEntity;
	}
}
