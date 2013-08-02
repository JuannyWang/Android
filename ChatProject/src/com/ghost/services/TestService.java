/**
 * 
 */
package com.ghost.services;

import com.ghost.utils.Utils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-7-10
 */
public class TestService extends Service {
	
	public static final String tag = "TestService";
	
	private boolean flag;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		Utils.alertToast(getBaseContext(), tag, "Service绑定");
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		flag = true;
		Utils.alertToast(getBaseContext(), tag, "Service创建");
		new Thread(new Runnable() {

			@Override
			public void run() {
				while(flag) {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Log.v(tag, "消息循环");
				}
			}
			
		}).start();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Utils.alertToast(getBaseContext(), tag, "Service销毁");
		flag = false;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		Utils.alertToast(getBaseContext(), tag, "Service开始");
	}
	
	

}
