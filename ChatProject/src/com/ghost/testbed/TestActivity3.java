/**
 * 
 */
package com.ghost.testbed;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.ghost.chatproject.R;
import com.ghost.services.TestChatService;
import com.ghost.utils.Utils;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-7-11
 */
public class TestActivity3 extends Activity implements OnClickListener {

	public static final String tag = "TestActivity3";

	private TextView tv;

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				Bundle b = msg.getData();
				tv.setText(b.getString("data"));
				break;
			default:
				Utils.alertToast(getBaseContext(), tag, "接收到未定义事件编码:"
						+ msg.what);
				break;
			}
		}

	};

	private ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			rMessenger = new Messenger(service);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			rMessenger = null;
		}

	};

	private Messenger mMessenger;
	private Messenger rMessenger;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat_test);
		initGUI();
		mMessenger = new Messenger(mHandler);
		Log.v(tag, "onCreate");
	}

	private void initGUI() {
		Button btns[] = new Button[6];
		btns[0] = (Button) findViewById(R.id.btn_start);
		btns[1] = (Button) findViewById(R.id.btn_stop);
		btns[2] = (Button) findViewById(R.id.btn_blind);
		btns[3] = (Button) findViewById(R.id.btn_unblind);
		btns[4] = (Button) findViewById(R.id.btn_send);
		btns[5] = (Button) findViewById(R.id.btn_check);

		tv = (TextView) findViewById(R.id.tv_display);

		for (int i = 0; i < btns.length; ++i) {
			btns[i].setOnClickListener(this);
		}

//		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//		Log.v("getDeviceId", tm.getDeviceId());
//		Log.v("getLine1Number", tm.getLine1Number());
//		Log.v("getNetworkType", tm.getNetworkType() + "");
//		Log.v("getPhoneType", tm.getPhoneType() + "");
//		Log.v("getSimCountryIso", tm.getSimCountryIso());
//		Log.v("getSimSerialNumber", tm.getSimSerialNumber());
//		Log.v("getSimState", tm.getSimState() + "");
//		Log.v("getSubscriberId", tm.getSubscriberId());
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.v(tag, "onDestroy");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_start:
			Utils.alertToast(getBaseContext(), tag, "开启Service");
			startService(new Intent(this, TestChatService.class));
			break;
		case R.id.btn_stop:
			Utils.alertToast(getBaseContext(), tag, "销毁Service");
			stopService(new Intent(this, TestChatService.class));
			break;
		case R.id.btn_blind:
			Utils.alertToast(
					getBaseContext(),
					tag,
					"绑定Service:"
							+ bindService(new Intent(this,
									TestChatService.class), serviceConnection,
									BIND_AUTO_CREATE));
			break;
		case R.id.btn_unblind:
			Utils.alertToast(getBaseContext(), tag, "取消绑定Service:");
			unbindService(serviceConnection);
			break;
		case R.id.btn_send:
			Message msg = Message.obtain(null, 0);
			msg.replyTo = mMessenger;
			try {
				rMessenger.send(msg);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			break;
		case R.id.btn_check:
			Utils.alertToast(
					getBaseContext(),
					tag,
					"Service存活状态："
							+ Utils.checkServiceAlive(getBaseContext(),
									"com.ghost.services.TestChatService"));
			break;
		}
	}
}
