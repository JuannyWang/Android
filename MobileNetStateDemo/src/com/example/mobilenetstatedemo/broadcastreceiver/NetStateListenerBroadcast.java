package com.example.mobilenetstatedemo.broadcastreceiver;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import com.example.mobilenetstatedemo.adapter.DataBaseAdapter;
import com.example.mobilenetstatedemo.service.NetStateListenerService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetStateListenerBroadcast extends BroadcastReceiver {

	private static final String TAG = "NetStateListener";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub

		Log.e("TAG", "网络状态监听");
		boolean success = false;
		// 获得网络连接服务
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// 获取类型为mobile的网络状态
		NetworkInfo mobNetInfo = connManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		// 获取Wifi网络连接状态
		NetworkInfo wifiNetInfo = connManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifiNetInfo.isConnected()) {
			// 发送Intent启动服务，由服务来完成比较耗时的操作
			Intent service = new Intent(context, NetStateListenerService.class);
			context.startService(service);
		}
	}


}
