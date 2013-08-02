/**
 * 
 */
package ghost.android3d.opengl10;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-1-25
 */
public class IndexActivity extends Activity {
	private MyGLSurfaceView mSurfaceView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); // 继承父类方法
		mSurfaceView = new MyGLSurfaceView(this); // 创建MySurfaceView对象
		mSurfaceView.requestFocus(); // 获取焦点
		mSurfaceView.setFocusableInTouchMode(true); // 设置为可触控
		this.setContentView(mSurfaceView);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mSurfaceView.onPause();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mSurfaceView.onResume();
	}
}
