package com.example.chatface02;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.AsyncTask;
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
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.data.Common;
import com.example.servers.ChatService;

public class VoiceTalkActivity extends Activity implements OnClickListener {

	private TableRow receiveLayout = null;
	private TableRow talkMenuLayout = null;

	private RelativeLayout relativeLayout = null;

	private ImageButton receiveButton = null;
	private ImageButton hangupButton = null;
	private TextView contatcorName_voice;
	private LinkedList<byte[]> recordDataList;
	private LinkedList<byte[]> receiveDataList;
	private AudioRecord audioRecord;
	private int minbuffersize;
	private boolean flag;
	private int port = 6688;
	Bundle bundle;
	private String ID, IP_from;
	private int common_code;

	ServerSocket server;
	Socket socket;
	DataInputStream in;
	DataOutputStream out;

	private AudioTrack audioTrack;

	private AsyncTask<Void, Integer, Void> recordTask, playTask, sendTask,
			receiveTask, myConnectionTask;

	public static final String tag = "VoiceCommuncationActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_voice);
		initView();
		bindService(new Intent(this, ChatService.class), serviceConnection,
				BIND_AUTO_CREATE);
		CommuncationStart();
	}

	public void CommuncationStart() {
		// TODO Auto-generated method stub

		bundle = getIntent().getExtras();
		common_code = bundle.getInt("Common");
		IP_from = bundle.getString("IP");
		ID = bundle.getString("CallNewPhone");
		Log.v("voiceTalkActivity", ID);
		recordTask = new MyRecordTask();
		receiveTask = new MyReceiveTask();
		playTask = new MyPlayTask();
		sendTask = new MySendTask();
		myConnectionTask = new MyBuildConnectionTask();
		myConnectionTask.execute();
		ID = bundle.getString("CallNewPhone");
		contatcorName_voice.setText(ID);
	}

	private void initView() {
		// TODO Auto-generated method stub
		receiveLayout = (TableRow) findViewById(R.id.receiveCallLayout_voice);
		talkMenuLayout = (TableRow) findViewById(R.id.talkMenuLayout_voice);

		relativeLayout = (RelativeLayout) findViewById(R.id.control_TimeLayout);
		contatcorName_voice = (TextView) findViewById(R.id.contatcorName_voice);
		receiveButton = (ImageButton) findViewById(R.id.receiveCallButton_voice);
		hangupButton = (ImageButton) findViewById(R.id.hangupImageButton_voice);

		receiveButton.setOnClickListener(this);
	}

	// private void
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.receiveCallButton_voice:
			receiveLayout.setVisibility(View.GONE);
			talkMenuLayout.setVisibility(View.VISIBLE);
			relativeLayout.setVisibility(View.VISIBLE);
			hangupButton.setImageDrawable(this.getResources().getDrawable(
					R.drawable.hangupimg));
			break;
		case R.id.hangupImageButton_voice:
			flag = false;
			break;
		case R.id.contactorsButton_voice:
			Intent contactorIntent = new Intent();
			contactorIntent.setClass(this, ContactorsListActivity.class);
			startActivity(contactorIntent);
			this.finish();
			break;
		case R.id.msgButton_voice:
			Intent receiveIntent = new Intent();
			receiveIntent.setClass(this, SMSListActivity.class);
			startActivity(receiveIntent);
			this.finish();
			break;
		}
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

	@Override
	protected void onDestroy() {
		unbindService(serviceConnection);
		super.onDestroy();
	}

	private class MyBuildConnectionTask extends AsyncTask<Void, Integer, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			if (common_code == 0) {
				try {
					Log.v(tag, "开启server:" + port);
					server = new ServerSocket(port);
					Log.v(tag, "开启server成功");
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					Log.v(tag, "开始等待客户端连接");
					socket = server.accept();
					out = new DataOutputStream(socket.getOutputStream());
					in = new DataInputStream(socket.getInputStream());
					Log.v(tag, "建立连接成功");
					flag = true;
					recordTask.execute();
					sendTask.execute();
					receiveTask.execute();
					playTask.execute();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					Log.v(tag, "开始尝试建立建立" + IP_from + ":" + port);
					socket = new Socket(IP_from, port);
					out = new DataOutputStream(socket.getOutputStream());
					in = new DataInputStream(socket.getInputStream());
					Log.v(tag, "建立连接成功");
					flag = true;
					recordTask.execute();
					sendTask.execute();
					receiveTask.execute();
					playTask.execute();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

	}

	private class MyRecordTask extends AsyncTask<Void, Integer, Void> {

		@SuppressWarnings("deprecation")
		@Override
		protected Void doInBackground(Void... params) {
			minbuffersize = AudioRecord.getMinBufferSize(8000,
					AudioFormat.CHANNEL_CONFIGURATION_MONO,
					AudioFormat.ENCODING_PCM_16BIT);
			audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, 8000,
					AudioFormat.CHANNEL_CONFIGURATION_MONO,
					AudioFormat.ENCODING_PCM_16BIT, minbuffersize);
			audioRecord.startRecording();
			while (flag) {
				byte[] audioData = new byte[minbuffersize];
				audioRecord.read(audioData, 0, minbuffersize);
				recordDataList.add(audioData);
			}
			audioRecord.stop();
			return null;
		}
	}

	private class MyReceiveTask extends AsyncTask<Void, Integer, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			while (flag) {
				try {
					int len = in.readInt();
					byte[] tmp = new byte[len];
					int r_len = 0;
					while (r_len != len)
						r_len += in.read(tmp, r_len, len - r_len);
					receiveDataList.add(tmp);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				in.close();
				socket.close();
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

	}

	private class MySendTask extends AsyncTask<Void, Integer, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			while (flag) {
				if (recordDataList.size() > 1) {
					byte[] tmp = recordDataList.removeFirst();
					try {
						out.writeInt(tmp.length);
						out.write(tmp);
						out.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			try {
				out.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

	}

	private class MyPlayTask extends AsyncTask<Void, Integer, Void> {

		@SuppressWarnings("deprecation")
		@Override
		protected Void doInBackground(Void... params) {
			minbuffersize = AudioTrack.getMinBufferSize(8000,
					AudioFormat.CHANNEL_CONFIGURATION_MONO,
					AudioFormat.ENCODING_PCM_16BIT);
			audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 8000,
					AudioFormat.CHANNEL_CONFIGURATION_MONO,
					AudioFormat.ENCODING_PCM_16BIT, minbuffersize,
					AudioTrack.MODE_STREAM);
			audioTrack.play();
			while (flag) {
				if (receiveDataList.size() > 1) {
					byte[] tmp = receiveDataList.removeFirst();
					audioTrack.write(tmp, 0, tmp.length);
				}

			}
			audioTrack.stop();
			return null;
		}

	}
}
