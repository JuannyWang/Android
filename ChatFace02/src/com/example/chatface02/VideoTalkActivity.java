package com.example.chatface02;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import android.annotation.SuppressLint;
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
import android.provider.MediaStore.Images;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.example.data.Common;
import com.example.data.LocalDataFactory;
import com.example.servers.ChatService;
import com.example.view.CameraView;
import com.example.view.DecodeView;

public class VideoTalkActivity extends Activity implements OnClickListener {

	private ServerSocket server;
	private Images image;
	private byte[] imageData;
	
	private ImageButton fullScreenButton = null;
	private ImageButton swithButton = null;
	private ImageButton hideSelfButton = null;
	private ImageButton overButton = null;
	public static int size = 0;
	private Bundle bundle;
	private String ID, IP_from,IP_target;
	private int common_code;
	
	private SeekBar seekBar;
	private SeekBar seekSkip;
	private TextView tv_ratio;
	private TextView tv_size;
	private TextView tv_skip;
	private CameraView collect_view;
	private DecodeView decode_view;
	
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				tv_size.setText("" + size);
				break;
			}
		}

	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_video);
		bindService(new Intent(this, ChatService.class), serviceConnection,
				BIND_AUTO_CREATE);
		fullScreenButton = (ImageButton)findViewById(R.id.fullScreenButotn);
		swithButton = (ImageButton)findViewById(R.id.vedioSwithButton);
		hideSelfButton = (ImageButton)findViewById(R.id.hideselfButton);
		overButton = (ImageButton)findViewById(R.id.overButton);
		
		fullScreenButton.setOnClickListener(this);
		swithButton.setOnClickListener(this);
		hideSelfButton.setOnClickListener(this);
		overButton.setOnClickListener(this);
		seekBar = (SeekBar) findViewById(R.id.seek);
		seekBar.setMax(100);
		tv_ratio = (TextView) findViewById(R.id.ratio);
		tv_size = (TextView) findViewById(R.id.size);
		tv_skip = (TextView) findViewById(R.id.tv_skip);
		seekSkip = (SeekBar) findViewById(R.id.skip);
		seekSkip.setMax(30);
		seekSkip.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				CameraView.setSkipFrame(progress);
				tv_skip.setText(progress + "");
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}

		});
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				CameraView.setRatio(progress);
				tv_ratio.setText(progress + "");
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

		});
		collect_view = (CameraView) findViewById(R.id.camera_view);
		decode_view = (DecodeView) findViewById(R.id.decode_view);
		mMessenger = new Messenger(mHandler);
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						Message msg = Message.obtain(null,0);
						try {
							mMessenger.send(msg);
						} catch (RemoteException e) {
							e.printStackTrace();
						}
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		}).start();
	}
	
	public void VideoCommuncation(){
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
			Message send_video = Message.obtain(null, Common.VIDEO_COMMUNCATION);
			send_video.setData(typeBundle);
			try {
				rMessenger.send(send_video);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			receiverVideo();
			decode_view.startDecode();
			collect_view.setIp(IP_target);
			collect_view.startView();
			
			
		} else if (common_code == 11){
//			mMessenger = new Messenger(mHandler);
			collect_view.setIp(IP_from);
			collect_view.startView();
			receiverVideo();
			decode_view.startDecode();

		}
			ID = bundle.getString("CallNewPhone");

	}
	
	/**
	 * 数据接收
	 */
	public void receiverVideo(){
		try {
			server = new ServerSocket(8215);
		} catch (IOException e) {
			e.printStackTrace();
		}
//		this.setPreferredSize(new Dimension(500, 500));
		new Thread(new Runnable() {

			@Override
			public void run() {

				Socket socket;
				DataInputStream in = null;
				try {
					socket = server.accept();
					System.out.println("接收到："
							+ socket.getInetAddress().toString());
					IP_target = socket.getInetAddress().toString();
					in = new DataInputStream(socket.getInputStream());
				} catch (IOException e) {
					e.printStackTrace();
				}
				while (true) {
					try {
						int n_len = in.readInt();
						byte[] data = new byte[n_len];
						decode_view.setBytes(data);
						int r_len = 0;
						while (r_len != n_len)
							r_len += in.read(data, r_len, n_len - r_len);
						System.out.println("需要的的数据包长度为:" + n_len);
						System.out.println("接收到的数据包长度为:" + r_len);
						System.out.println("开始解包");
						
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}
			}
		}).start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
//					repaint();
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		}).start();
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

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.fullScreenButotn:
			this.setContentView(R.layout.activity_fullscreen);
			
			break;
		case R.id.vedioSwithButton:
			
			break;
		case R.id.hideselfButton:
			
			break;
		case R.id.overButton:
			collect_view.stopView();
			decode_view.stopDecode();
			break;
		}
	}

}
