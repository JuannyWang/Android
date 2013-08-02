package com.cqupt.mobilestudiesdemo.entity.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.util.Log;

import com.cqupt.mobilestudiesdemo.db.DBResourceGroupImpl;
import com.cqupt.mobilestudiesdemo.db.DBResourceImpl;
import com.cqupt.mobilestudiesdemo.db.DBResourceSubTypeImpl;
import com.cqupt.mobilestudiesdemo.entity.WebServiceApi;

public class WebServiceApiImpl implements WebServiceApi {
	private Context mContext = null;

	public WebServiceApiImpl(Context context) {
		mContext = context;
	}

	/**
	 * 校验数据
	 * 
	 * @param soapObject
	 * @param result
	 * @param flagID
	 * @return
	 */
	private SoapObject judgeSoapObject(SoapObject soapObject, String result,
			int flagID) {
		// 返回数据错误
		if (flagID != GET_DATA_SUCCESS) {
			return null;
		}
		Log.d("WebService-" + result, soapObject.toString());
		// 返回数据为null
		if (soapObject.getPropertyCount() <= 0) {
			return null;
		}
		soapObject = (SoapObject) soapObject.getProperty(0);
		soapObject = (SoapObject) soapObject.getPropertySafely(result);
		if (soapObject.getPropertyCount() <= 0) {
			return null;
		}
		soapObject = (SoapObject) soapObject.getPropertySafely(DIFFGRAM_PRO);
		if (soapObject.getPropertyCount() <= 0) {
			return null;
		}
		soapObject = (SoapObject) soapObject
				.getPropertySafely(NEW_DATA_SET_PRO);
		if (soapObject.getPropertyCount() <= 0) {
			return null;
		}
		return soapObject;
	}

	/**
	 * 获取数据
	 * 
	 * @param soapObject
	 * @param method
	 * @param soapAction
	 * @param params
	 * @return
	 */
	private int ResultWebService(SoapObject soapObject, String method,
			String soapAction, Map<String, Object> params) {
		// TODO Auto-generated method stub
		SoapObject result = null;
		/**
		 * 数据处理状态ID
		 */
		int flagID = GET_DATA_SUCCESS;
		try {
			SoapObject request = new SoapObject(NAMESPACE, method);
			if (params != null) {
				if (params.size() > 0) {
					Set<String> setMap = params.keySet();
					Iterator<String> it = setMap.iterator();
					while (it.hasNext()) {
						String strKey = it.next();
						Object strValue = params.get(strKey);
						request.addProperty(strKey, strValue);
					}
				}
			}

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.bodyOut = request;
			envelope.dotNet = true;

			HttpTransportSE httpTransportSE = new HttpTransportSE(URL);
			httpTransportSE.debug = true;
			httpTransportSE.call(soapAction, envelope);

			result = (SoapObject) envelope.bodyIn;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			result = null;
			flagID = NET_ERR;
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			result = null;
			flagID = GET_DATA_ERR;
		}

		/**
		 * 将返回值添加到该SoapObjet
		 */
		soapObject.addSoapObject(result);
		return flagID;
	}

	@Override
	public int GetResource(int groupID) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PARAMS_GROUP_ID, groupID);

		SoapObject result = new SoapObject();
		int flagID = ResultWebService(result, GET_RESOURCE_LIST_MD,
				GET_RESOURCE_SOAP_ACTION, params);
		/**
		 * 获取数据并校验数据
		 */
		result = judgeSoapObject(result, GET_RESOURCE_LIST_PRO, flagID);
		if (result != null) {
			/**
			 * 当获取的资源不为null时将数据写入数据库
			 */
			DBResourceImpl dbResourceImpl = new DBResourceImpl(mContext);
			dbResourceImpl.addResource(ResourceFunctions
					.getResourceEntities(result));

		}

		return flagID;
	}

	@Override
	public int GetResourceGroup(int subTypeID) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PARAMS_SUB_TYPE_ID, subTypeID);
		SoapObject result = new SoapObject();
		int flagID = ResultWebService(result, GET_GROUP_NAME_MD,
				GET_GROUP_SOAP_ACTION, params);
		/**
		 * 获取数据并校验数据
		 */
		result = judgeSoapObject(result, GET_GROUP_NAME_PRO, flagID);
		if (result != null) {
			/**
			 * 当获取的资源不为null时将数据写入数据库
			 */
			DBResourceGroupImpl dbResourceGroupImpl = new DBResourceGroupImpl(
					mContext);
			dbResourceGroupImpl.addResourceGroups(ResourceGroupFunctions
					.getResourceGroupEntities(result));
		}

		return flagID;
	}

	@Override
	public int GetResourceSubType(int typeID) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PARAMS_TYPE_ID, typeID);
		SoapObject result = new SoapObject();
		int flagID = ResultWebService(result, GET_SUB_TYPE_NAME_MD,
				GET_SUB_TYPE_SOAP_ACTION, params);
		/**
		 * 获取数据并校验数据
		 */
		result = judgeSoapObject(result, GET_SUB_TYPE_NAME_PRO, flagID);
		if (result != null) {
			/**
			 * 当获取的资源不为null时将数据写入数据库
			 */
			DBResourceSubTypeImpl dbResourceSubTypeImpl = new DBResourceSubTypeImpl(
					mContext);
			dbResourceSubTypeImpl.addResourceSubTyepes(ResourceSubTypeFunctions
					.getResourceSubTypeEntities(result));
		}

		return flagID;
	}

	@Override
	public int GetResourceType() {
		// TODO Auto-generated method stub
		return 0;
	}


}
