/**
 * 
 */
package com.ghost.activitys;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ghost.chatproject.R;
import com.ghost.datas.Common;
import com.ghost.services.ChatService;
import com.ghost.utils.Utils;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-7-11
 */
public class FriendListActivity extends Activity {

	public static final String tag = "FriendListActivity";

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend_list);
		mMessenger = new Messenger(mHandler);
		bindService(new Intent(this, ChatService.class), serviceConnection,
				BIND_AUTO_CREATE);
	}
	
	@Override
	protected void onDestroy() {
		unbindService(serviceConnection);
		super.onDestroy();
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
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int item_id = item.getItemId();
		switch (item_id) {
		case R.id.action_exit:
			Message msg = Message.obtain(null, Common.EXIT);
			try {
				rMessenger.send(msg);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			stopService(new Intent(this, ChatService.class));
			finish();
			break;
		default:
			return false;
		}
		return true;

	}
}
