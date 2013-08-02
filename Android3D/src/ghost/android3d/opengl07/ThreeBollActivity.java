/**
 * 
 */
package ghost.android3d.opengl07;

import ghost.android3d.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.SeekBar;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-1-25
 */
public class ThreeBollActivity extends Activity {
	private MyGLSurfaceView mSurfaceView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); // 继承父类方法
		setContentView(R.layout.threeboll_layout); // 设置布局文件
		mSurfaceView = new MyGLSurfaceView(this); // 创建MySurfaceView对象
		mSurfaceView.requestFocus(); // 获取焦点
		mSurfaceView.setFocusableInTouchMode(true); // 设置为可触控
		LinearLayout ll = (LinearLayout) this.findViewById(R.id.lla); // 获得线性布局的引用
		ll.addView(mSurfaceView);//
		SeekBar sb; // 声明拖拉条引用
		sb = (SeekBar) findViewById(R.id.SeekBar01);
		sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) { // 通过SeekBar来改变定向光的方向
				System.out.println(progress);
				if (progress < 50) { // 光线在左边
					mSurfaceView.setLight0PositionX(-(progress / 5));
				} else if (progress >= 50) { // 光线在右边
					mSurfaceView.setLight0PositionX((progress - 50) / 5);
				}
			}
		});
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
