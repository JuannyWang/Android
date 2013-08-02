package com.proxy.activity;

import com.proxy.activity.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SelectActivity extends Activity {
	private Button StoreButton, Upload;
	private String ip, userid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select);
		ip = getIntent().getStringExtra("ip");
		userid = getIntent().getStringExtra("userid");
		StoreButton = (Button)findViewById(R.id.selectmanager);
		StoreButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("ip", ip);
				intent.putExtra("userid", userid);
				intent.setClass(SelectActivity.this, StoreUploadCodeActivity.class);
				startActivity(intent);
			}
		});
		
		Upload = (Button)findViewById(R.id.selectupload);
		Upload.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("ip", ip);
				intent.putExtra("userid", userid);
				intent.setClass(SelectActivity.this, SaleUploadCodeActivity.class);
				startActivity(intent);
			}
		});
		
		
	}
	
	
}
