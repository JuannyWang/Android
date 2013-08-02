/**
 * 
 */
package com.ghost.testbed;

import com.ghost.beans.PackageType;
import com.ghost.chatproject.R;
import com.ghost.connection.ServerConnection;
import com.ghost.datas.Common;
import com.ghost.services.ChatService;
import com.ghost.utils.Utils;

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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-7-23
 */
public class TestConnectionActivity extends Activity implements OnClickListener {
	public static final String tag = "TestConnectionActivity";

	private EditText et_ip, et_port, et_display;
	private Button btn_con, btn_dis, btn_send;

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Common.MESSAGE_TEST:
				Bundle b = msg.getData();
				String message = b.getString("data");
				et_display.append(message + "\n");
				break;
			default:
				Utils.alertToast(getBaseContext(), tag, "接收到未定义事件码：" + msg.what);
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_connection);
		mMessenger = new Messenger(mHandler);
		initGUI();
		bindService(new Intent(this, ChatService.class), serviceConnection,
				BIND_AUTO_CREATE);
	}

	private void initGUI() {
		et_ip = (EditText) findViewById(R.id.et_ip);
		et_port = (EditText) findViewById(R.id.et_port);
		et_display = (EditText) findViewById(R.id.et_display);
		et_display.setEnabled(false);
		btn_con = (Button) findViewById(R.id.btn_connection);
		btn_con.setOnClickListener(this);
		btn_dis = (Button) findViewById(R.id.btn_disconnection);
		btn_dis.setEnabled(false);
		btn_dis.setOnClickListener(this);
		btn_send = (Button) findViewById(R.id.btn_send);
		btn_send.setEnabled(false);
		btn_send.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Message message;
		switch (v.getId()) {
		case R.id.btn_connection:
			message = Message.obtain(null, Common.CON);
			try {
				rMessenger.send(message);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			// et_ip.setEnabled(false);
			// et_port.setEnabled(false);
			// btn_con.setEnabled(false);
			btn_dis.setEnabled(true);
			btn_send.setEnabled(true);
			break;
		case R.id.btn_disconnection:
			message = Message.obtain(null, Common.EXIT);
			try {
				rMessenger.send(message);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			// et_ip.setEnabled(true);
			// et_port.setEnabled(true);
			// btn_con.setEnabled(true);
			btn_dis.setEnabled(false);
			btn_send.setEnabled(false);
			break;
		case R.id.btn_send:
			Message test_message = Message.obtain(null, PackageType.TEST);
			try {
				rMessenger.send(test_message);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			break;
		}
	}
}
