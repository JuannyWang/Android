/**
 * 
 */
package com.example.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 本地数据操作工厂
 * 
 * @author 玄雨
 * @qq 821580467
 * @date 2013-7-11
 */
public class LocalDataFactory {

	private SharedPreferences sp;
	private SharedPreferences.Editor editor;

	private static LocalDataFactory thisObject;

	private LocalDataFactory(Context context) {
		sp = context.getSharedPreferences(Common.DATA_FILE_NAME,
				Context.MODE_PRIVATE);
		editor = sp.edit();
	}

	public static LocalDataFactory getInstance(Context context) {
		if (thisObject == null) {
			thisObject = new LocalDataFactory(context);
		}
		return thisObject;
	}

	public void setAccount(String account) {
		editor.putString("account", account);
		editor.commit();
	}

	public String getAccount() {
		return sp.getString("account", "");
	}

	public void setIsFirst(boolean isFirst) {
		editor.putBoolean("isFirst", isFirst);
		editor.commit();
	}

	public boolean getIsFirst() {
		return sp.getBoolean("isFirst", true);
	}
}
