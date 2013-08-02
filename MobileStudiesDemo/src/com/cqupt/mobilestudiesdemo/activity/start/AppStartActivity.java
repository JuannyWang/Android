package com.cqupt.mobilestudiesdemo.activity.start;

import java.io.File;

import com.cqupt.mobilestudiesdemo.activity.MainActivity;
import com.cqupt.mobilestudiesdemo.activity.R;
import com.cqupt.mobilestudiesdemo.media.MusicActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

public class AppStartActivity extends Activity implements Runnable {

	// 是否是第一次使用
	private boolean isFirstUse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_start_layout);

		/**
		 * 启动一个延迟线程
		 */
		new Thread(this).start();

	}


	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			/**
			 * 延迟两秒时间
			 */
			Thread.sleep(2000);

			// 读取SharedPreferences中需要的数据
			@SuppressWarnings("deprecation")
			SharedPreferences preferences = getSharedPreferences("isFirstUse",
					MODE_WORLD_READABLE);

			isFirstUse = preferences.getBoolean("isFirstUse", true);

			/**
			 * 如果用户不是第一次使用则直接调转到显示界面,否则调转到引导界面
			 */
			if (isFirstUse) {
				startActivity(new Intent(AppStartActivity.this,
						WellcomeActivity.class));
			} else {
				startActivity(new Intent(AppStartActivity.this,
						MainActivity.class));
			}
			finish();

			// 实例化Editor对象
			Editor editor = preferences.edit();
			// 存入数据
			editor.putBoolean("isFirstUse", false);
			// 提交修改
			editor.commit();

		} catch (InterruptedException e) {

		}
	}

}
