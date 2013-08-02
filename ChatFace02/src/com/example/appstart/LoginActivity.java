/**
 * 
 */
package com.example.appstart;

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

import com.example.bean.PackageType;
import com.example.chatface02.ContactorsListActivity;
import com.example.chatface02.R;
import com.example.data.Common;
import com.example.data.LocalDataFactory;
import com.example.servers.ChatService;
import com.example.utils.ChatUtils;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-7-11
 */
public class LoginActivity extends Activity implements OnClickListener {
	
	public static final String tag = "LoadActivity";
	
	private EditText et_account,et_password;
	
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what) {
			case Common.LOGIN_SUCCESS:
				gotoFriendListActivity();
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mMessenger = new Messenger(mHandler);
		bindService(new Intent(this, ChatService.class), serviceConnection,
				BIND_AUTO_CREATE);
		initGUI();
	}
	
	@Override
	protected void onDestroy() {
		unbindService(serviceConnection);
		super.onDestroy();
	}
	
	@Override
	public void onBackPressed() {
		stopService(new Intent(this,ChatService.class));
		super.onBackPressed();
	}

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
			Message startMessage = Message.obtain(null, Common.CON);
			try {
				rMessenger.send(startMessage);
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
	
	/**
	 * 初始化界面以及绑定事件监听器
	 */
	private void initGUI() {
		Button btns[] = new Button[2];
		btns[0] = (Button) findViewById(R.id.btn_login);
		btns[1] = (Button) findViewById(R.id.btn_regist);
		et_account = (EditText) findViewById(R.id.et_account);
		et_password = (EditText) findViewById(R.id.et_password);
		for(int i=0;i<btns.length;++i) {
			btns[i].setOnClickListener(this);
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		Message msg;
		Bundle b;
		switch(v.getId()) {
		case R.id.btn_login:
			LocalDataFactory.getInstance(this).setAccount(et_account.getText().toString());
			intent = new Intent(this,ChatService.class);
			startService(intent);
			msg = Message.obtain(null, Common.LOGIN);
			b = new Bundle();
			b.putString("account", et_account.getText().toString());
			b.putString("password", et_password.getText().toString());
			msg.setData(b);
			try {
				rMessenger.send(msg);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			break;
		case R.id.btn_regist:
			LocalDataFactory.getInstance(this).setAccount(et_account.getText().toString());
			intent = new Intent(this,ChatService.class);
			startService(intent);
			msg = Message.obtain(null, Common.REGIST);
			b = new Bundle();
			b.putString("account", et_account.getText().toString());
			b.putString("password", et_password.getText().toString());
			msg.setData(b);
			try {
				rMessenger.send(msg);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			break;
		}
	}
	
	private void gotoFriendListActivity() {
		Intent intent = new Intent(this,ContactorsListActivity.class);
		startActivity(intent);
		finish();
	}

}
