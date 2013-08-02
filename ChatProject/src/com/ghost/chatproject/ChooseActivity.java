package com.ghost.chatproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.ghost.activitys.LoadActivity;
import com.ghost.testbed.TestActivity3;
import com.ghost.testbed.TestConnectionActivity;
import com.ghost.utils.Utils;

public class ChooseActivity extends Activity implements OnClickListener {

	public static final String tag = "ChooseActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		Button btn_normal = (Button) findViewById(R.id.btn_normal);
		btn_normal.setOnClickListener(this);

		Button btn_test = (Button) findViewById(R.id.btn_test);
		btn_test.setOnClickListener(this);
		
		Log.v(tag, "onCreate");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.v(tag, "onDestroy");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (v.getId()) {
		case R.id.btn_normal:
			Log.v(tag, "点击正常程序启动按钮");
			Utils.alertToast(getBaseContext(), tag, "点击正常程序启动按钮");
			intent = new Intent(this, LoadActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.btn_test:
			Log.v(tag, "点击测试程序启动按钮");
			Utils.alertToast(getBaseContext(), tag, "点击测试程序启动按钮");
			intent = new Intent(this, TestConnectionActivity.class);
			startActivity(intent);
			finish();
			break;
		}
	}

}
