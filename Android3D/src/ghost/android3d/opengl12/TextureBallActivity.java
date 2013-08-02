/**
 * 
 */
package ghost.android3d.opengl12;

import ghost.android3d.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-1-25
 */
public class TextureBallActivity extends Activity {
	private MyGLSurfaceView mSurfaceView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); // 继承父类方法
		setContentView(R.layout.textureball_layout); // 设置布局文件
		mSurfaceView = new MyGLSurfaceView(this); // 创建MySurfaceView对象
		mSurfaceView.requestFocus(); // 获取焦点
		mSurfaceView.setFocusableInTouchMode(true); // 设置为可触控
		LinearLayout ll = (LinearLayout) this.findViewById(R.id.lla); // 获得线性布局的引用
		ll.addView(mSurfaceView);//
		ToggleButton tb = (ToggleButton) this.findViewById(R.id.ToggleButton01);// 获得按钮引用
		tb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				mSurfaceView.setSmoothFlag(!mSurfaceView.isSmoothFlag());
				mSurfaceView.requestRender();
			}
		}); // 为按钮设置监听器
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
