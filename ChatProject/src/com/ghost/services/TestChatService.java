/**
 * 
 */
package com.ghost.services;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-7-11
 */
public class TestChatService extends Service {

	public static final String tag = "TestChatService";

	private String send_str = "尚未初始化的字符串";

	private Thread thread;

	private boolean flag;

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			rMessenger = msg.replyTo;
			switch (msg.what) {
			case 0:
				send_str = "我擦，你想发什么！！！";
				break;
			default:
				break;
			}
		}

	};

	private Messenger mMessenger;
	private Messenger rMessenger;

	@Override
	public IBinder onBind(Intent intent) {
		Log.v(tag, "onBind");
		flag = true;
		thread.start();
		return mMessenger.getBinder();
	}

	@Override
	public void onRebind(Intent intent) {
		Log.v(tag, "onRebind");
		super.onRebind(intent);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Log.v(tag, "onStart");
		super.onStart(intent, startId);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.v(tag, "onUnbind");
		flag = false;
		return super.onUnbind(intent);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		thread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (flag) {
					Message msg = Message.obtain(null, 0);
					Bundle b = new Bundle();
					b.putString("data", send_str);
					msg.setData(b);
					try {
						if (rMessenger != null) {
							Log.v(tag, "发送消息:" + msg);
							rMessenger.send(msg);
						} else {
							Log.v(tag, "布吉岛往哪发...");
						}
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					send_str = "我随机啦...." + Math.random();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		});
		mMessenger = new Messenger(mHandler);
		Log.v(tag, "onCreate");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.v(tag, "onDestroy");
	}

}
