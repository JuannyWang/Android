/**
 * 
 */
package com.example.appstart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;

import com.example.chatface02.ContactorsListActivity;
import com.example.chatface02.R;
import com.example.data.Common;
import com.example.data.LocalDataFactory;
import com.example.utils.ChatUtils;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-7-11
 */
public class LoadActivity extends Activity {

	public static final String tag = "LoadActivity";

	private LocalDataFactory localDataFactory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_load);
		localDataFactory = LocalDataFactory.getInstance(this);
		if (localDataFactory.getIsFirst()) {
			// 创建添加快捷方式的Intent
			Intent addIntent = new Intent(
					"com.android.launcher.action.INSTALL_SHORTCUT");
			String title = getResources().getString(R.string.app_name);
			// 加载快捷方式的图标
			Parcelable icon = Intent.ShortcutIconResource.fromContext(
					LoadActivity.this, R.drawable.ic_launcher);
			// 创建点击快捷方式后操作Intent,该处当点击创建的快捷方式后，再次启动该程序
			Intent myIntent = new Intent(LoadActivity.this, LoginActivity.class);
			// 设置快捷方式的标题
			addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
			// 设置快捷方式的图标
			addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
			// 设置快捷方式对应的Intent
			addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, myIntent);
			// 发送广播添加快捷方式
			sendBroadcast(addIntent);
			ChatUtils.alertToast(getBaseContext(), tag, "成功在桌面创建快捷方式");
			localDataFactory.setIsFirst(false);
		}
		initView();
	}
	
	private void initView() {
		if(ChatUtils.checkServiceAlive(getBaseContext(), Common.SERVICE_NAME)) {
			Intent intent = new Intent(LoadActivity.this,ContactorsListActivity.class);
			startActivity(intent);
			finish();
		} else {
			Handler mHandler = new Handler();
			mHandler.postDelayed(new Runnable() {
				public void run() {
					gotoLoginActivity();
				}
			}, 1000);
		}
	}
	
	private void gotoLoginActivity() {
		Intent intent = new Intent(LoadActivity.this,LoginActivity.class);
		startActivity(intent);
		finish();
	}

}
