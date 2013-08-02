package com.example.camtest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	public static int size = 0;

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

	private Messenger mMessenger;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.layout_main); // 设置View
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
		Button start_collect = (Button) findViewById(R.id.start_collect);
		Button stop_collect = (Button) findViewById(R.id.stop_collect);
		Button start_decode = (Button) findViewById(R.id.start_decode);
		Button stop_decode = (Button) findViewById(R.id.stop_decode);
		start_collect.setOnClickListener(this);
		stop_collect.setOnClickListener(this);
		start_decode.setOnClickListener(this);
		stop_decode.setOnClickListener(this);
		collect_view = (CameraView) findViewById(R.id.camera_view);
		decode_view = (DecodeView) findViewById(R.id.decode_view);
		mMessenger = new Messenger(mHandler);
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						Message msg = Message.obtain(null, 0);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.start_collect:
			collect_view.startView();
			break;
		case R.id.stop_collect:
			collect_view.stopView();
			break;
		case R.id.start_decode:
			decode_view.startDecode();
			break;
		case R.id.stop_decode:
			decode_view.stopDecode();
			break;
		}
	}

}
