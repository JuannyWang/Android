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

	private boolean focusflag = false;// 是否正在对焦标识
	private boolean stopable = false;

	private Timer timer;
	private Timetask timertask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scancode);
		setTitle("条码/二维码识别");

		limitwidth = getWindowManager().getDefaultDisplay().getWidth();
		limitheight = getWindowManager().getDefaultDisplay().getHeight();
		System.out.println("display: " + limitwidth + " " + limitheight);

		cameraview = (SurfaceView) findViewById(R.id.camera);
		cameracallback = new CameraCallback(cameraview.getHolder(), limitwidth,
				limitheight, callback);

		// code = (TextView)findViewById(R.id.result);

		// 启动定时器，每隔500ms自动对焦扫描二维码
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

	// 预览图像回调接口
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
					Result result = reader.decode(bbm);// 解析图像
					maincode = result.getText();
					focusflag = true;
					fillCode();
				} catch (Exception e) {
					// code.setText("扫描失败");
				}
				stopable = true;
			}
		}
	};

	// 设置屏幕中心四分之三区域为对准扫描区域
	private void getsize() {
		if (tgtLeft == 0) {// 只赋值一次
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

			tgtLeft = focusview.getLeft();// 取得x轴坐标
			tgtTop = focusview.getTop();// 取得y轴坐标
		}
	}

	private class Timetask extends TimerTask {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (!focusflag)
				cameracallback.focus();// 对焦
		}

	}

	// 将数据返回到上一个界面
	private void fillCode() {
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString("code", maincode);
		intent.putExtras(bundle);
		setResult(1, intent);
		ScanActivity.this.finish();
	}
}
