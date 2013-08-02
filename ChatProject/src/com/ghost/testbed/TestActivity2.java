/**
 * 
 */
package com.ghost.testbed;

import com.ghost.services.TestService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-7-10
 */
public class TestActivity2 extends Activity {
	
	public static final String tag = "TestActivity2";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout main_layout = new LinearLayout(this);
		main_layout.setOrientation(LinearLayout.VERTICAL);
		
		Button btn_start = new Button(this);
		btn_start.setText("停止Service");
		btn_start.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				stopTestService();
			}
		});
		main_layout.addView(btn_start);
		
		Button btn_prev = new Button(this);
		btn_prev.setText("返回前一个activity");
		btn_prev.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				gotoPrevActivity();
			}
		});
		main_layout.addView(btn_prev);
		
		setContentView(main_layout);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.v(tag, "onDestroy");
	}
	
	private void stopTestService() {
		Intent intent = new Intent(this,TestService.class);
		stopService(intent);
	}
	
	private void gotoPrevActivity() {
		Intent intent = new Intent(this,TestActivity.class);
		startActivity(intent);
		finish();
	}
}
