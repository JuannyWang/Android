/**
 * 
 */
package ghost.android3d.opengl03;

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
public class ChangeTriangleActivity extends Activity {
	private MyGLSurfaceView mSurfaceView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); // 继承父类方法
		setContentView(R.layout.changetriangle_layout); // 设置布局文件
		mSurfaceView = new MyGLSurfaceView(this); // 创建MySurfaceView对象
		mSurfaceView.requestFocus(); // 获取焦点
		mSurfaceView.setFocusableInTouchMode(true); // 设置为可触控
		LinearLayout ll = (LinearLayout) this.findViewById(R.id.main_liner); // 获得线性布局的引用
		ll.addView(mSurfaceView);//
		ToggleButton tb01 = (ToggleButton) this
				.findViewById(R.id.ToggleButton01); // 获得第一个开关按钮的引用
		tb01.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				mSurfaceView.setBackFlag(!mSurfaceView.isBackFlag());
			}
		}); // 为开关按钮注册监听器
		ToggleButton tb02 = (ToggleButton) this
				.findViewById(R.id.ToggleButton02); // 获得第二个开关按钮的引用
		tb02.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				mSurfaceView.setSmoothFlag(!mSurfaceView.isSmoothFlag());
			}
		});//
		ToggleButton tb03 = (ToggleButton) this
				.findViewById(R.id.ToggleButton03); // 获得第三个开关按钮的引用
		tb03.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				mSurfaceView.setSelfCulling(!mSurfaceView.isSelfCulling());
			}
		});//
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
