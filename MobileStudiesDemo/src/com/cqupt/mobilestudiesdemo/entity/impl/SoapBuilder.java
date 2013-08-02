package com.cqupt.mobilestudiesdemo.entity.impl;

import org.ksoap2.serialization.SoapObject;

/**
 * 
 * @author ap
 * 
 * @param <T>
 */
public abstract class SoapBuilder<T> {
	public abstract T build(SoapObject soapObject);
}
