package com.cqupt.mobilestudiesdemo.entity;

public interface WebServiceApi {
	public static final String URL = "http://202.202.43.36/webcollege/wenfeng.asmx";
	public static final String NAMESPACE = "http://tempuri.org/";

	/**
	 * WebService 方法名称
	 */
	public static final String GET_TYPE_NAME_MD = "GetTypeName";
	public static final String GET_SUB_TYPE_NAME_MD = "GetSubTypeName";
	public static final String GET_GROUP_NAME_MD = "GetGroupName";
	public static final String GET_RESOURCE_LIST_MD = "GetResourceList";

	/**
	 * SoapObject属性名称
	 */
	public static final String GET_TYPE_NAME_PRO = "GetTypeNameResult";
	public static final String GET_SUB_TYPE_NAME_PRO = "GetSubTypeNameResult";
	public static final String GET_GROUP_NAME_PRO = "GetGroupNameResult";
	public static final String GET_RESOURCE_LIST_PRO = "GetResourceListResult";

	/**
	 * SoapObject公共属性名称
	 */
	public static final String DIFFGRAM_PRO = "diffgram";
	public static final String NEW_DATA_SET_PRO = "NewDataSet";

	public static final String GET_TYPE_SOAP_ACTION = NAMESPACE
			+ GET_TYPE_NAME_MD;
	public static final String GET_SUB_TYPE_SOAP_ACTION = NAMESPACE
			+ GET_SUB_TYPE_NAME_MD;
	public static final String GET_GROUP_SOAP_ACTION = NAMESPACE
			+ GET_GROUP_NAME_MD;
	public static final String GET_RESOURCE_SOAP_ACTION = NAMESPACE
			+ GET_RESOURCE_LIST_MD;

	/**
	 * WebService 方法参数名称
	 */
	public static final String PARAMS_TYPE_ID = "typeID";
	public static final String PARAMS_SUB_TYPE_ID = "subTypeID";
	public static final String PARAMS_GROUP_ID = "groupID";

	/**
	 * 数据处理状态ID
	 */
	public static final int GET_DATA_SUCCESS = 0x0;// 获取数据成功
	public static final int NET_ERR = 0x1;// 网络错误
	public static final int GET_DATA_ERR = 0x2;// 获取数据错误

	/**
	 * * 获取资源列表
	 * 
	 * @param groupID
	 * @return 返回数据处理状态ID
	 */

	public int GetResource(int groupID);

	/**
	 * 获取资源集合列表
	 * 
	 * @param subTypeID
	 * @return 返回数据处理状态ID
	 */
	public int GetResourceGroup(int subTypeID);

	/**
	 * 获取资源子类型列表
	 * 
	 * @param typeID
	 * @return 返回数据处理状态ID
	 */
	public int GetResourceSubType(int typeID);

	/**
	 * 获取资源类型列表
	 * 
	 * @return 返回数据处理状态ID
	 */
	public int GetResourceType();

}
