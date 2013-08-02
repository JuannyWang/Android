/**
 * 
 */
package ghost.android3d.senser;

import ghost.android3d.R;
import android.app.Activity;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-2-3
 */
@SuppressWarnings("deprecation")
public class SenserActivity extends Activity {

	// SensorManager对象引用
	private SensorManager mySensorManager;

	private TextView tv[];

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.senser_layout);
		tv = new TextView[6];
		tv[0] = (TextView) this.findViewById(R.id.tvX);
		tv[1] = (TextView) this.findViewById(R.id.tvY);
		tv[2] = (TextView) this.findViewById(R.id.tvZ);
		tv[3] = (TextView) this.findViewById(R.id.tvPitch);
		tv[4] = (TextView) this.findViewById(R.id.tvYaw);
		tv[5] = (TextView) this.findViewById(R.id.tvRoll);

		// 获得SensorManager对象
		mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		mySensorManager.unregisterListener(mySensorListener); // 取消注册监听器
		super.onPause();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		mySensorManager.registerListener( // 注册监听器
				mySensorListener, // 监听器对象
				SensorManager.SENSOR_ACCELEROMETER, // 传感器类型
				SensorManager.SENSOR_DELAY_UI // 传感器事件传递的频度
				);
		mySensorManager.registerListener( // 注册监听器
				mySensorListener, // 监听器对象
				SensorManager.SENSOR_ORIENTATION, // 传感器类型
				SensorManager.SENSOR_DELAY_UI // 传感器事件传递的频度
				);
		super.onResume();
	}

	// 开发实现了SensorEventListener接口的传感器监听器
	private SensorListener mySensorListener = new SensorListener() {
		@Override
		public void onAccuracyChanged(int sensor, int accuracy) {
		}

		@Override
		public void onSensorChanged(int sensor, float[] values) {
			if (sensor == SensorManager.SENSOR_ACCELEROMETER) {// 判断是否为加速度传感器变化产生的数据
																// 将提取的数据显示到TextView
				tv[0].setText("x轴加速度：" + values[0]);
				tv[1].setText("y轴加速度：" + values[1]);
				tv[2].setText("z轴加速度：" + values[2]);
			}
			if (sensor == SensorManager.SENSOR_ORIENTATION) {
				tv[3].setText("Pitch轴旋转角度：" + values[0]);
				tv[4].setText("Yaw轴旋转角度：" + values[1]);
				tv[5].setText("Roll轴旋转角度：" + values[2]);
			}
		}
	};

}
