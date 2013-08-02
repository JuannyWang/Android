package com.example.audiotest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	private LinkedList<byte[]> recordDataList;
	private LinkedList<byte[]> receiveDataList;
	private AudioRecord audioRecord;
	private int minbuffersize;
	private boolean flag;
	private String ip = "127.0.0.1";
	private int port = 6688;

	private AudioTrack audioTrack;

	private AsyncTask<Void, Integer, Void> recordTask, playTask, sendTask,
			receiveTask;

	private TextView tv_real, tv_gzip, tv_zip, tv_bzip2;

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle b = msg.getData();
			switch (msg.what) {
			case 0:
				tv_real.setText(b.getInt("data") + "");
				break;
			case 1:
				tv_gzip.setText(b.getInt("data") + "");
				break;
			case 2:
				tv_zip.setText(b.getInt("data") + "");
				break;
			case 3:
				tv_bzip2.setText(b.getInt("data") + "");
				break;
			}
		}

	};

	private Messenger mMessenger;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button start = (Button) this.findViewById(R.id.btn_start);
		start.setOnClickListener(this);
		Button stop = (Button) this.findViewById(R.id.btn_stop);
		stop.setOnClickListener(this);
		tv_real = (TextView) this.findViewById(R.id.et_real);
		tv_gzip = (TextView) this.findViewById(R.id.et_gzip);
		tv_zip = (TextView) this.findViewById(R.id.et_zip);
		tv_bzip2 = (TextView) this.findViewById(R.id.et_bzip2);
		mMessenger = new Messenger(mHandler);
		recordDataList = new LinkedList<byte[]>();
		receiveDataList = new LinkedList<byte[]>();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
				socket = new Socket(ip, port);
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
					/*
					Message msg2 = Message.obtain(null, 1);
					Bundle b2 = new Bundle();
					b2.putInt("data", Utils.gZip(tmp).length);
					msg2.setData(b2);
					try {
						mMessenger.send(msg2);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
					
					Message msg3 = Message.obtain(null, 2);
					Bundle b3 = new Bundle();
					b3.putInt("data", Utils.zip(tmp).length);
					msg3.setData(b3);
					try {
						mMessenger.send(msg3);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
					
					Message msg4 = Message.obtain(null, 3);
					Bundle b4 = new Bundle();
					b4.putInt("data", Utils.bZip2(tmp).length);
					msg4.setData(b4);
					try {
						mMessenger.send(msg4);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
					*/
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_start:
			recordTask = new MyRecordTask();
			playTask = new MyPlayTask();
			receiveTask = new MyReceiveTask();
			sendTask = new MySendTask();
			flag = true;
			recordTask.execute();
			playTask.execute();
			receiveTask.execute();
			sendTask.execute();
			break;
		case R.id.btn_stop:
			flag = false;
			break;
		}
	}

}
