package com.example.chatface02;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.bean.PackageBean;
import com.example.data.Common;
import com.example.data.LocalDataFactory;
import com.example.servers.ChatService;
import com.example.utils.ChatUtils;

public class SendToMsgActivity extends Activity {
	Bundle bundle;
	String name, ID;
	private EditText sendPerson_edit, msg_edit;
	PackageBean msg_PackageBean;
	public static final String tag = "MsgConnectionActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_msg_edit);
		mMessenger = new Messenger(mHandler);
		initView();
		showSendToPerson();
		bindService(new Intent(this, ChatService.class), serviceConnection,
				BIND_AUTO_CREATE);
	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Common.MESSAGE_TEST:
				Bundle b = msg.getData();
				String message = b.getString("data");
				msg_edit.append(message + "\n");
				break;
			case Common.SEND_MSG:
				Bundle reciverBundle = msg.getData();
				String reciver_msg = reciverBundle.getString("msg");
				String ID_From = reciverBundle.getString("ID");
				ChatUtils.alertToast(getBaseContext(), tag, "收到来自:" + ID_From
						+ "的消息:" + reciver_msg);
				break;
			default:
				ChatUtils.alertToast(getBaseContext(), tag, "接收到未定义事件码："
						+ msg.what);
				break;
			}
		}

	};

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

	@Override
	protected void onDestroy() {
		unbindService(serviceConnection);
		super.onDestroy();
	}

	public void initView() {
		sendPerson_edit = (EditText) findViewById(R.id.name_send_edit);
		msg_edit = (EditText) findViewById(R.id.send_msg_edit);
	}

	public void MsgSendOnclick(View view) {
		switch (view.getId()) {
		case R.id.msg_send_btn:
			LocalDataFactory dataFactory = LocalDataFactory.getInstance(this);
			String myID = dataFactory.getAccount();
			bundle = getIntent().getExtras();
			ID = bundle.getString("CallNewPhone");
			String msg = msg_edit.getText().toString();
			Bundle typeBundle = new Bundle();
			typeBundle.putString("myID", myID);
			typeBundle.putString("ID", ID);
			typeBundle.putString("msg", msg);
			Message send_msg = Message.obtain(null, Common.SEND_MSG);
			send_msg.setData(typeBundle);
			try {
				rMessenger.send(send_msg);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			break;
		}
	}

	public String showSendToPerson() {
		bundle = getIntent().getExtras();
		name = bundle.getString("CallName");
		sendPerson_edit.setText(name);
		Log.v("Call", name);
		return name;

	}

}
