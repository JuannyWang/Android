package com.proxy.activity;

import java.util.Timer;
import java.util.TimerTask;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.manor.util.CameraCallback;
import com.proxy.activity.R;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class ScanActivity extends Activity {

	private SurfaceView cameraview;
	private View focusview;

	private CameraCallback cameracallback;

	private int width;
	private int height;

	private int limitwidth;
	private int limitheight;

	private int tgtLeft = 0, tgtTop, tgtWidth, tgtHeight;
	private String maincode;

	private boolean focusflag = false;// �Ƿ����ڶԽ���ʶ
	private boolean stopable = false;

	private Timer timer;
	private Timetask timertask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scancode);
		setTitle("����/��ά��ʶ��");

		limitwidth = getWindowManager().getDefaultDisplay().getWidth();
		limitheight = getWindowManager().getDefaultDisplay().getHeight();
		System.out.println("display: " + limitwidth + " " + limitheight);

		cameraview = (SurfaceView) findViewById(R.id.camera);
		cameracallback = new CameraCallback(cameraview.getHolder(), limitwidth,
				limitheight, callback);

		// code = (TextView)findViewById(R.id.result);

		// ������ʱ����ÿ��500ms�Զ��Խ�ɨ���ά��
		timer = new Timer();
		timertask = new Timetask();
		timer.schedule(timertask, 1000, 500);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		focusflag = true;
		timer.cancel();
		super.onPause();
	}

	public Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if (!focusflag) {
				stopable = false;
				cameracallback.focus();
			}
		}

	};

	// Ԥ��ͼ��ص��ӿ�
	private PreviewCallback callback = new PreviewCallback() {

		@Override
		public void onPreviewFrame(byte[] data, Camera camera) {
			// TODO Auto-generated method stub
			if (!focusflag) {
				getsize();
				if (data == null)
					return;
				Log.e("length", " " + data.length);

				PlanarYUVLuminanceSource source = null;
				try {
					source = new PlanarYUVLuminanceSource(data, width, height,
							0, 0, width, height, false);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					return;
				}

				BinaryBitmap bbm = new BinaryBitmap(new HybridBinarizer(source));
				MultiFormatReader reader = new MultiFormatReader();
				try {
					Result result = reader.decode(bbm);// ����ͼ��
					maincode = result.getText();
					focusflag = true;
					fillCode();
				} catch (Exception e) {
					// code.setText("ɨ��ʧ��");
				}
				stopable = true;
			}
		}
	};

	// ������Ļ�����ķ�֮������Ϊ��׼ɨ������
	private void getsize() {
		if (tgtLeft == 0) {// ֻ��ֵһ��
			width = cameracallback.width;
			height = cameracallback.height;

			tgtWidth = (cameraview.getRight() - cameraview.getLeft()) * 3 / 4;
			tgtHeight = (cameraview.getBottom() - cameraview.getTop()) * 3 / 4;

			System.out.println("tgt: " + tgtWidth + " " + tgtHeight);
			focusview = (View) findViewById(R.id.focusView);
			LayoutParams params = focusview.getLayoutParams();
			params.width = tgtWidth;
			params.height = tgtHeight;
			focusview.setLayoutParams(params);

			tgtLeft = focusview.getLeft();// ȡ��x������
			tgtTop = focusview.getTop();// ȡ��y������
		}
	}

	private class Timetask extends TimerTask {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (!focusflag)
				cameracallback.focus();// �Խ�
		}

	}

	// �����ݷ��ص���һ������
	private void fillCode() {
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString("code", maincode);
		intent.putExtras(bundle);
		setResult(1, intent);
		ScanActivity.this.finish();
	}
}
