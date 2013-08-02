package com.ghost.maze;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

@SuppressWarnings("deprecation")
public class MainActivity extends RajawaliExampleActivity implements
		SensorEventListener {

	private float scaleW;

	private float scaleH;

	private MyRender myRender;

	private final float ALPHA = 0.8f;
	private final int SENSITIVITY = 5;

	private SensorManager mSensorManager;
	private float mGravity[];

	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		scaleW = (float) dm.widthPixels / (float) 480;
		scaleH = (float) dm.heightPixels / (float) 800;
		super.onCreate(savedInstanceState);
		myRender = new MyRender(this);
		myRender.setSurfaceView(mSurfaceView);
		super.setRenderer(myRender);
		MyView rl = new MyView(this);
		mLayout.addView(rl);

		initLoader();

		mGravity = new float[3];
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mSensorManager.registerListener(this,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_FASTEST);
	}

	private class MyView extends AbsoluteLayout {

		private final int btn_width = 90;
		private final int btn_height = 90;

		/**
		 * @param context
		 */
		public MyView(Context context) {
			super(context);

			ImageView image = new ImageView(context);
			image.setImageResource(R.drawable.logo);
			AbsoluteLayout.LayoutParams params = new AbsoluteLayout.LayoutParams(
					getW(480), getH(110), getW(5), getH(0));
			addView(image, params);

			addButton(40, 600, "↑").setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					myRender.moveCamera(4);
					return true;
				}
			});

			addButton(40, 700, "↓").setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					myRender.moveCamera(5);
					return true;
				}
			});
			addButton(250, 660, "←").setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					myRender.moveCamera(2);
					return true;
				}
			});
			addButton(370, 660, "→").setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					myRender.moveCamera(3);
					return true;
				}
			});

			addButton(0, 0, "←").setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					myRender.moveHouse(0);
					return true;
				}
			});
			addButton(390, 0, "→").setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					myRender.moveHouse(1);
					return true;
				}
			});
			addButton(150, 600, "+").setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					myRender.moveCamera(0);
					return true;
				}
			});
			addButton(150, 700, "-").setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					myRender.moveCamera(1);
					return true;
				}
			});

		}

		/**
		 * 向界面中添加一个button并且返回这个button，button大小由常量值设定
		 * 
		 * @param x
		 * @param y
		 * @param text
		 * @return
		 */
		private Button addButton(int x, int y, String text) {
			Button btn = new Button(MainActivity.this);
			btn.setText(text);
			btn.getBackground().setAlpha(100);
			AbsoluteLayout.LayoutParams params = new AbsoluteLayout.LayoutParams(
					getW(btn_width), getH(btn_height), getW(x), getH(y));
			addView(btn, params);
			return btn;
		}

		private int getW(int w) {
			return (int) (w * scaleW);
		}

		private int getH(int h) {
			return (int) (h * scaleH);
		}

	}

	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			mGravity[0] = ALPHA * mGravity[0] + (1 - ALPHA) * event.values[0];
			mGravity[1] = ALPHA * mGravity[1] + (1 - ALPHA) * event.values[1];
			mGravity[2] = ALPHA * mGravity[2] + (1 - ALPHA) * event.values[2];

			myRender.setAccelerometerValues(event.values[1] - mGravity[1]
					* SENSITIVITY, event.values[0] - mGravity[0] * SENSITIVITY,
					0);
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

}
