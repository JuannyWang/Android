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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.data.Common;
import com.example.data.LocalDataFactory;
import com.example.servers.ChatService;
import com.example.utils.ChatUtils;

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

	private AudioTrack audioTrack;

	private AsyncTask<Void, Integer, Void> recordTask, playTask, sendTask,
			receiveTask;

	private TextView tv_real, tv_gzip, tv_zip, tv_bzip2;
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
		ID = bundle.getString("ID");
		if (common_code == 0) {
//			mMessenger = new Messenger(mHandler);
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
			receiveTask = new MyReceiveTask();
			playTask = new MyPlayTask();
			playTask.execute();
			receiveTask.execute();

			sendTask = new MySendTask();

		} else if (common_code == 10){
//			mMessenger = new Messenger(mHandler);
			recordTask = new MyRecordTask();
			playTask = new MyPlayTask();
			receiveTask = new MyReceiveTask();
			sendTask = new MySendTask();
			flag = true;
			recordTask.execute();
			playTask.execute();
			receiveTask.execute();
			sendTask.execute();
			contatcorName_voice.setText(ID);
		}
			ID = bundle.getString("CallNewPhone");


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

	private class MyRecordTask extends AsyncTask<Void, Integer, Void> {

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

			ServerSocket server;
			Socket socket;
			DataInputStream in;

			try {
				server = new ServerSocket(port);
				
				socket = server.accept();
				IP_from = socket.getInetAddress().toString();
				in = new DataInputStream(socket.getInputStream());
			} catch (UnknownHostException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}

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

			Socket socket;
			DataOutputStream out;

			try {
				socket = new Socket(IP_from, port);
				out = new DataOutputStream(socket.getOutputStream());
			} catch (UnknownHostException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}

			while (flag) {
				if (recordDataList.size() > 1) {
					byte[] tmp = recordDataList.removeFirst();

					Message msg1 = Message.obtain(null, 0);
					Bundle b1 = new Bundle();
					b1.putInt("data", tmp.length);
					msg1.setData(b1);
					try {
						mMessenger.send(msg1);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
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
