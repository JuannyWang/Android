/**
 * 
 */
package com.example.utils;

import java.security.MessageDigest;
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
abstract public class ChatUtils {

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
	
	/**
	 * MD5加密
	 * @param s
	 * @return
	 */
	public final static String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
