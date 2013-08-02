package com.example.chatface02;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements OnClickListener {

	private Button vedioButton = null;
	private Button smsButton = null;
	private Button softDailButton = null;    
	private Button ptDailshowButton = null;  
	
	private Button historyButton = null;
	private Button contactorsButton = null;
	private Button msgButton = null;   
	private Button otherFunButton = null;
	
	private ArrayList<ContactorHistory> toucherHistoryList; 
	private ListView historyListView; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		softDailButton = (Button)findViewById(R.id.softDailButton_main);
		ptDailshowButton = (Button)findViewById(R.id.ptDailButton_main);
		smsButton = (Button)findViewById(R.id.smsDailButton_main);
		vedioButton = (Button)findViewById(R.id.vedioDailButton_main);
		
		historyButton = (Button)findViewById(R.id.historyButton_main);
		contactorsButton = (Button)findViewById(R.id.contactorsButton_main);
		msgButton = (Button)findViewById(R.id.msgButton_main);
		otherFunButton = (Button)findViewById(R.id.otherfunButton_main);
		
		softDailButton.setOnClickListener(this);
		ptDailshowButton.setOnClickListener(this);
		smsButton.setOnClickListener(this);
		vedioButton.setOnClickListener(this);
		
		historyButton.setOnClickListener(this);
		contactorsButton.setOnClickListener(this);
		msgButton.setOnClickListener(this);
		otherFunButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.softDailButton_main:
			Intent softIntent = new Intent();
			softIntent.setClass(MainActivity.this, VoiceTalkActivity.class);
			startActivity(softIntent);
			break;
		case R.id.ptDailButton_main:

			break;
		case R.id.smsDailButton_main:
			Intent smsIntent = new Intent();
			smsIntent.setClass(MainActivity.this, SendToMsgActivity.class);
			startActivity(smsIntent);
			break;
		case R.id.vedioDailButton_main:
			Intent vedioIntent = new Intent();
			vedioIntent.setClass(MainActivity.this, VideoTalkActivity.class);
			startActivity(vedioIntent);
			break;
		case R.id.contactorsButton_main:
			Intent contactorIntent = new Intent();
			contactorIntent.setClass(MainActivity.this, ContactorsListActivity.class);
			startActivity(contactorIntent);
			break;
		case R.id.msgButton_main:
			Intent receiveIntent = new Intent();
			receiveIntent.setClass(MainActivity.this, SMSListActivity.class);
			startActivity(receiveIntent);
			break;
		case R.id.otherfunButton_main:
			Intent otherIntent = new Intent();
			otherIntent.setClass(MainActivity.this, VideoTalkInviteActivity.class);
			startActivity(otherIntent);
			break;
		}
	}

}
