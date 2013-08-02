package ghost.compass;

import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.app.Activity;
import android.content.pm.ActivityInfo;

@SuppressWarnings("deprecation")
public class MainActivity extends Activity {

	/**
	 * 指南真绘制面板
	 */
	private CompassView compass;

	// SensorManager对象引用
	private SensorManager mySensorManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		compass = new CompassView(this);
		setContentView(compass);
		// 获得SensorManager对象
		mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	}

	@Override
	protected void onPause() {
		mySensorManager.unregisterListener(mySensorListener); // 取消注册监听器
		super.onPause();
	}

	@Override
	protected void onResume() {
		mySensorManager.registerListener( // 注册监听器
				mySensorListener, // 监听器对象
				SensorManager.SENSOR_ORIENTATION, // 传感器类型
				SensorManager.SENSOR_DELAY_UI // 传感器事件传递的频度
				);
		super.onResume();
	}
	

	@Override
	public void onBackPressed() {
		this.finish();
	}


	// 开发实现了SensorEventListener接口的传感器监听器
	private SensorListener mySensorListener = new SensorListener() {
		@Override
		public void onAccuracyChanged(int sensor, int accuracy) {
		}

		@Override
		public void onSensorChanged(int sensor, float[] values) {
			if (sensor == SensorManager.SENSOR_ORIENTATION) {
				compass.degree = -values[0] ;
			}
		}
	};

}
