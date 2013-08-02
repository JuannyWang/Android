package com.example.mobilenetstatedemo.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import com.example.mobilenetstatedemo.activity.MainActivity;
import com.example.mobilenetstatedemo.adapter.DataBaseAdapter;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class NetStateListenerService extends Service{
	String ip_db,ip_now;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub

		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true){
					DataBaseAdapter db = new DataBaseAdapter(NetStateListenerService.this);
					db.open();
					Cursor c = db.getAllIP();
	
					try {
						if (c.moveToFirst()){
							ip_db = c.getString(0);
							Log.v("ip_db", ip_db);
							
						}else{
							//为空则直接插入
							ip_now = getWifiIPAddress();
							db.insertWifiIP(ip_now);
							Log.v("ip_now", ip_now);

						}
						Bundle b = new Bundle();
				        Intent intent = new Intent(NetStateListenerService.this,MainActivity.class);  
				        b.putString("ip_new", ip_now);
				        b.putString("ip_db", ip_db);
				        intent.putExtras(b);
				        startActivity(intent);

						//延时5秒
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					db.close();

				}
			}
		}).start();
	 
		return null;
	}
	
	//获取本机mac	
	public String getLocalMacAddress() {  
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);  
        WifiInfo info = wifi.getConnectionInfo();  
        return info.getMacAddress();  
    } 
	
	//获取本机wifi ip
	public String getWifiIPAddress() {
		WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		if (!wifiManager.isWifiEnabled()) {
			wifiManager.setWifiEnabled(true);
		}
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int ipAddress = wifiInfo.getIpAddress();
		String ip = intToIp(ipAddress);
		Log.v("IPAddress", ip);
		return ip;
	}

	private String intToIp(int i) {
		return (i & 0xFF) + "." +
		((i >> 8) & 0xFF) + "." +
		((i >> 16) & 0xFF) + "." +
		(i >> 24 & 0xFF);
	}
		

}
