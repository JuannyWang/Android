package com.example.mobilenetstatedemo.activity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView ip_db_tv,ip_new_tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ip_db_tv = (TextView)findViewById(R.id.myIp_db);
		ip_new_tv = (TextView)findViewById(R.id.myIP_new);
		Bundle b = new Bundle();
		String ip_new = b.getString("ip_new");
		String ip_db = b.getString("ip_db");
		ip_db_tv.setText("db_ip:" + ip_db);
		ip_new_tv.setText("db_new:" + ip_new);

	}


}
