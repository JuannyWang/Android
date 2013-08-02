/**
 * 
 */
package com.ghost.testbed;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ghost.services.TestService;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-7-10
 */
public class TestActivity extends Activity {

	public static final String tag = "TestActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout main_layout = new LinearLayout(this);
		main_layout.setOrientation(LinearLayout.VERTICAL);
		
		Button btn_start = new Button(this);
		btn_start.setText("启动Service");
		btn_start.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startTestService();
			}
		});
		main_layout.addView(btn_start);
		
		Button btn_stop = new Button(this);
		btn_stop.setText("停止Service");
		btn_stop.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				stopTestService();
			}
		});
		main_layout.addView(btn_stop);
		
		Button btn_next = new Button(this);
		btn_next.setText("进入下一个Activity");
		btn_next.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				gotoNextActivity();
			}
		});
		main_layout.addView(btn_next);
		
		setContentView(main_layout);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.v(tag, "onDestroy");
	}
	
	private void gotoNextActivity() {
		Intent intent = new Intent(this,TestActivity2.class);
		startActivity(intent);
		finish();
	}
	
	private void startTestService() {
		Intent intent = new Intent(this,TestService.class);
		startService(intent);
	}
	
	private void stopTestService() {
		Intent intent = new Intent(this,TestService.class);
		stopService(intent);
	}

}
