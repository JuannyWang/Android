package com.cqupt.mobilestudiesdemo.entity.impl;

import org.ksoap2.serialization.SoapObject;

import com.cqupt.mobilestudiesdemo.entity.ResourceSubTypeEntity;

public class ResourceSubTypeBuilder extends SoapBuilder<ResourceSubTypeEntity> {

	@Override
	public ResourceSubTypeEntity build(SoapObject soapObject) {
		// TODO Auto-generated method stub
		ResourceSubTypeEntity resourceSubTypeEntity = new ResourceSubTypeEntity();
		resourceSubTypeEntity
				.setResourceSubTypeID(
						Integer.parseInt(soapObject
								.getPropertySafelyAsString("resourceSubTypeID")))
				.setResourceTypeID(
						Integer.parseInt(soapObject
								.getPropertySafelyAsString("resourceTypeID")))
				.setResourceSubTypeName(
						soapObject
								.getPropertySafelyAsString("resourceSubTypeName"));
		return resourceSubTypeEntity;
	}
}
