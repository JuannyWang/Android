package com.example.mobilenetstatedemo.broadcastreceiver;

import com.example.mobilenetstatedemo.service.NetStateListenerService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootBroadcastReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		Intent service = new Intent(arg0,NetStateListenerService.class);
		arg0.startService(service);
		Log.v("TAG", "开机NetStateListener服务自动启动.....");  
		
	}


}
