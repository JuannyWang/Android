package com.number.search;

import java.io.InputStream;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.number.search.service.MobileInfoService;

public class StartActivity extends Activity {
	
	private TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		tv = (TextView) findViewById(R.id.tv_display);

		Button button = (Button) this.findViewById(R.id.btn_confirm);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EditText et = (EditText) findViewById(R.id.et_phone);
				String phoneNumber = et.getText().toString();
				InputStream inStream = StartActivity.this.getClass().getClassLoader().getResourceAsStream("mobilesoap.xml");
				tv.setText("查询中.....");
				try {
					tv.setText(MobileInfoService.getMobileAddress(
							inStream, phoneNumber));
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(StartActivity.this, e.getMessage(),
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private void exit() {
		this.finish();
	}

	public void onBackPressed() {
		exit();
	}

}
