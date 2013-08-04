package com.example.chatface02;

import com.example.data.Common;
import com.example.data.LocalDataFactory;
import com.example.servers.ChatService;
import com.example.utils.ChatUtils;

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
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

public class VideoTalkInviteActivity extends Activity {
	private TextView contactorName;
	public static final String tag = "InviteActivity";
	private Bundle reciverBundle, bundle;
	private String ID_From, IP_from;
	private int Communcation_classify;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_invitate);
		reciverBundle = getIntent().getExtras();
//		mMessenger = new Messenger(mHandler);
		ID_From = reciverBundle.getString("ID");
		IP_from = reciverBundle.getString("IP");
		Communcation_classify = reciverBundle.getInt("Common");
		contactorName = (TextView) findViewById(R.id.invitateText);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		switch (Communcation_classify) {
		case Common.VOICE_COMMUNCATION:
			contactorName.setText(ID_From + "向你发起语音电话");
			break;
		case Common.VIDEO_COMMUNCATION:
			contactorName.setText(ID_From + "向你发起视频电话");
			break;
		default:
			ChatUtils
					.alertToast(getBaseContext(), tag, "接收到未定义事件码：" + Communcation_classify);
			break;
		}
	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			reciverBundle = msg.getData();

			Log.v("Invate", ID_From);
			Communcation_classify = msg.what;
			switch (msg.what) {
			case Common.MESSAGE_TEST:

				break;
			case Common.VOICE_COMMUNCATION:
				contactorName.setText(ID_From + "向你发起语音电话");
				break;
			case Common.VIDEO_COMMUNCATION:
				contactorName.setText(ID_From + "向你发起视频电话");
				break;
			default:
				ChatUtils.alertToast(getBaseContext(), tag, "接收到未定义事件码："
						+ msg.what);
				break;
			}
		}

	};

//	/**
//	 * 自己的消息对象
//	 */
//	private Messenger mMessenger;
//
//	/**
//	 * 交互目标的消息对象
//	 */
//	private Messenger rMessenger;
//
//	private ServiceConnection serviceConnection = new ServiceConnection() {
//
//		@Override
//		public void onServiceConnected(ComponentName name, IBinder service) {
//			rMessenger = new Messenger(service);
//			Message msg = Message.obtain(null, Common.BLIND);
//			msg.replyTo = mMessenger;
//			try {
//				rMessenger.send(msg);
//			} catch (RemoteException e) {
//				e.printStackTrace();
//			}
//		}
//
//		@Override
//		public void onServiceDisconnected(ComponentName name) {
//			Message msg = Message.obtain(null, Common.BLIND);
//			msg.replyTo = null;
//			try {
//				rMessenger.send(msg);
//			} catch (RemoteException e) {
//				e.printStackTrace();
//			}
//			mMessenger = null;
//		}
//	};
//
//	@Override
//	protected void onDestroy() {
//		unbindService(serviceConnection);
//		super.onDestroy();
//	}

	public void inviteOnclick(View view) {
		switch (view.getId()) {
		case R.id.yesInvite:
			Intent intent = new Intent();
			// IP_from = reciverBundle.getString("CallNewPhone");
			Log.v("invate", IP_from);
			Bundle typeBundle = new Bundle();
			typeBundle.putString("CallNewPhone", ID_From);
			typeBundle.putString("IP", IP_from);

			if (Communcation_classify == Common.VOICE_COMMUNCATION) {
				typeBundle.putInt("Common", Common.VOICE_ACCEPT);
				intent.setClass(this, VoiceTalkActivity.class);
				Log.v("invate", IP_from);

			} else if (Communcation_classify == Common.VIDEO_COMMUNCATION) {
				typeBundle.putInt("Common", Common.VIDEO_ACCEPT);
				intent.setClass(this, VideoTalkActivity.class);
			}
			intent.putExtras(typeBundle);
			startActivity(intent);
			break;
		case R.id.noInvite:
			Intent receiveIntent = new Intent();
			receiveIntent.setClass(this, MainActivity.class);
			startActivity(receiveIntent);
			break;
		default:
			this.finish();
			break;
		}
	}
}
