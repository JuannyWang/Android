package com.example.chatface02;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatface02.db.DBManager;
import com.example.chatface02.entity.Contactor;

public class PersonDetailCall extends Activity {
	private DBManager mgr;
	private EditText nameEdit, groupEdit, newPhoneEdit, normalEdit;
	private String name, ID, group, TEl;
	private Bundle bundle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_details_layout);
		nameEdit = (EditText) findViewById(R.id.name_call_edit);
		groupEdit = (EditText) findViewById(R.id.group_call_edit);
		newPhoneEdit = (EditText) findViewById(R.id.newPhone_call_edit);
		normalEdit = (EditText) findViewById(R.id.normalPhone_call_edit);
		showPersonDetail();
		mgr = new DBManager(this);

	}

	public void DetailCallOnclick(View view) {
		Intent intent = new Intent();
		intent.putExtras(bundle);

		switch (view.getId()) {
		case R.id.back_btn:
			intent.setClass(this, ContactorsListActivity.class);
			break;
		case R.id.edit_btn:

			break;
		case R.id.voice_msg_btn:
			intent.setClass(this, ContactorsListActivity.class);
			
			break;
		case R.id.video_msg_btn:
			intent.setClass(this, VideoTalkActivity.class);

			break;
		case R.id.msg_btn:
			intent.setClass(this, SendToMsgActivity.class);

			break;
		case R.id.normalPhone_call_edit:
			intent.setClass(this, VoiceTalkActivity.class);

			break;

		}
		startActivity(intent);
		this.finish();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mgr.closeDB();
	}

	public void showPersonDetail() {
		Contactor friend = new Contactor();

		bundle = getIntent().getExtras();

		name = bundle.getString("CallName");
		group = bundle.getString("CallGroup");
		ID = bundle.getString("CallNewPhone");
		TEl = bundle.getString("CallNormalPhone");
		Log.v("Call", name + "  " + group + "  " + ID + "  " + TEl);
		nameEdit.setText(name);
		groupEdit.setText(group);
		newPhoneEdit.setText(ID);
		normalEdit.setText(TEl);

	}
}
