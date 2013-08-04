package com.example.chatface02;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatface02.db.DBManager;
import com.example.chatface02.entity.Contactor;
import com.example.data.Common;
import com.example.data.LocalDataFactory;
import com.example.servers.ChatService;

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
		bindService(new Intent(this, ChatService.class), serviceConnection,
				BIND_AUTO_CREATE);

	}

	public void DetailCallOnclick(View view) {
		Intent intent = new Intent();
		bundle.putInt("Common", 0);
		
		switch (view.getId()) {
		case R.id.back_btn:
			intent.setClass(this, ContactorsListActivity.class);
			break;
		case R.id.edit_btn:

			break;
		case R.id.voice_msg_btn:
			LocalDataFactory dataFactory = LocalDataFactory.getInstance(this);
			String myID = dataFactory.getAccount();
			bundle = getIntent().getExtras();
			ID = bundle.getString("CallNewPhone");
			Bundle typeBundle = new Bundle();
			typeBundle.putString("myID", myID);
			typeBundle.putString("ID", ID);
			Message send_voice = Message.obtain(null, Common.VOICE_COMMUNCATION);
			send_voice.setData(typeBundle);
			try {
				rMessenger.send(send_voice);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			intent.setClass(this, VoiceTalkActivity.class);
			
			break;
		case R.id.video_msg_btn:
			intent.setClass(this, VideoTalkActivity.class);

			break;
		case R.id.msg_btn:
			intent.setClass(this, SendToMsgActivity.class);

			break;
		case R.id.normalPhone_call_edit:

			break;

		}
		intent.putExtras(bundle);
		startActivity(intent);
		this.finish();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unbindService(serviceConnection);

		mgr.closeDB();
	}
	/**
	 * 自己的消息对象
	 */
	private Messenger mMessenger;

	/**
	 * 交互目标的消息对象
	 */
	private Messenger rMessenger;

	private ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			rMessenger = new Messenger(service);
			Message msg = Message.obtain(null, Common.BLIND);
			msg.replyTo = mMessenger;
			try {
				rMessenger.send(msg);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Message msg = Message.obtain(null, Common.BLIND);
			msg.replyTo = null;
			try {
				rMessenger.send(msg);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			mMessenger = null;
		}
	};


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
