/**
 * 
 */
package com.ghost.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * 封装类，辅助输出工具类
 * 
 * @author 玄雨
 * @qq 821580467
 * @date 2013-7-10
 */
abstract public class Utils {

	/**
	 * 显示一个Toast 注：会自动产生log日志
	 * 
	 * @param context
	 * @param tag
	 * @param message
	 */
	public static void alertToast(Context context, String tag, String message) {
		Log.v("[auto]" + tag, message);
		Toast.makeText(context, tag + " : " + message, Toast.LENGTH_SHORT)
				.show();
	}

	/**
	 * 检查Service是否存活
	 * 
	 * @param context
	 * @param serviceName
	 *            Service完整名称
	 * @return
	 */
	public static boolean checkServiceAlive(Context context, String serviceName) {
		final ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		final List<RunningServiceInfo> services = activityManager
				.getRunningServices(Integer.MAX_VALUE);
		for (RunningServiceInfo runningServiceInfo : services) {
			if (runningServiceInfo.service.getClassName().equals(serviceName)) {
				return true;
			}
		}
		return false;
	}

}
