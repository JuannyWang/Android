package com.ghost;

import com.ghost.activitys.LoginActivity;
import com.ghost.activitys.TempActivity;
import com.ghost.utils.Constants;
import com.ghost.utils.SharePreferenceUtil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.app.Activity;
import android.content.Intent;

public class StartActivity extends Activity {

	private Handler myHandler;
	private SharePreferenceUtil util;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		myHandler = new Handler();
		util = new SharePreferenceUtil(this, Constants.SAVE_USER);
		if (util.getisFirst()) {
			// 创建添加快捷方式的Intent
			Intent addIntent = new Intent(
					"com.android.launcher.action.INSTALL_SHORTCUT");
			String title = getResources().getString(R.string.app_name);
			// 加载快捷方式的图标
			Parcelable icon = Intent.ShortcutIconResource.fromContext(
					StartActivity.this, R.drawable.ic_launcher);
			// 创建点击快捷方式后操作Intent,该处当点击创建的快捷方式后，再次启动该程序
			Intent myIntent = new Intent(StartActivity.this,
					StartActivity.class);
			// 设置快捷方式的标题
			addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
			// 设置快捷方式的图标
			addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
			// 设置快捷方式对应的Intent
			addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, myIntent);
			// 发送广播添加快捷方式
			sendBroadcast(addIntent);
			myHandler.postDelayed(new Runnable() {

				@Override
				public void run() {
					gotoLoginActivity();
				}
			}, 1000);
		} else {
			myHandler.postDelayed(new Runnable() {

				@Override
				public void run() {
					gotoFriendList();
				}
			}, 1000);
		}
	}
	
	private void gotoFriendList() {
		Intent intent = new Intent(this, TempActivity.class);
		startActivity(intent);
		finish();
	}

	private void gotoLoginActivity() {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
		finish();
	}

}
