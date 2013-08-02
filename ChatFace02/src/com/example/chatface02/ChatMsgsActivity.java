package com.example.chatface02;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.chatface02.db.DBManager;
import com.example.chatface02.entity.Contactor;
import com.example.data.Common;
import com.example.data.LocalDataFactory;
import com.example.utils.ChatUtils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.widget.EditText;

public class ChatMsgsActivity extends Activity {
	private EditText receive_edit, reply_edit;
	private DBManager mgr;
	private ArrayList<Map<String, String>> list;
	private List<Contactor> friends;
	private Bundle bundle;
	private String ID,msg;
	public static final String tag = "MsgReceiveActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_msg_chat);
		mMessenger = new Messenger(mHandler);

		initView();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mgr.closeDB();
	}

	private void initView() {
		mgr = new DBManager(this);
		initMsgShow();
		receive_edit = (EditText) findViewById(R.id.receive_msg_show);
		reply_edit = (EditText) findViewById(R.id.reply_msg_edit);
	}

	private void initMsgShow() {
		// TODO Auto-generated method stub
		bundle = getIntent().getExtras();
		ID = bundle.getString("msgNewPhone");
		msg = bundle.getString("receive_msgs");
		receive_edit.append(ID+": "+msg+"\n");
	}
	private Handler mHandler = new Handler() {
	
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case Common.MESSAGE_TEST:
					Bundle b = msg.getData();
					String message = b.getString("data");
					receive_edit.append(message + "\n");
					break;
				case Common.SEND_MSG:
					Bundle reciverBundle = msg.getData();
					String reciver_msg = reciverBundle.getString("msg");
					String ID_From = reciverBundle.getString("ID");
					if(ID_From.equals(ID)){
						receive_edit.append(ID_From + ": " + reciver_msg + "\n");
					}
					break;
				default:
					ChatUtils.alertToast(getBaseContext(), tag, "接收到未定义事件码：" + msg.what);
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

	public void ReplyMsgOnclick(View view) {
		switch (view.getId()) {
		case R.id.back_msg_btn:
			break;
		case R.id.reply_msg_btn:
			LocalDataFactory dataFactory = LocalDataFactory.getInstance(this);
			String myID = dataFactory.getAccount();
			String msg = reply_edit.getText().toString();
			Bundle typeBundle = new Bundle();
			typeBundle.putString("myID", myID);
			typeBundle.putString("ID", ID);
			typeBundle.putString("msg", msg);
			
			Message send_msg = Message.obtain(null, Common.SEND_MSG);
			send_msg.setData(typeBundle);
			receive_edit.append("我 "+myID+": "+msg+"\n");
			try {
				rMessenger.send(send_msg);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			break;
		}
	}
}
