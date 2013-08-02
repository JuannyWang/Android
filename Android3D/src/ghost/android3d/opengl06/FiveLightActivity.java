/**
 * 
 */
package ghost.android3d.opengl06;

import ghost.android3d.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.Toast;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-1-25
 */
public class FiveLightActivity extends Activity {
	private MyGLSurfaceView mSurfaceView;
	private Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); // 继承父类方法
		setContentView(R.layout.fivelight_layout); // 设置布局文件
		context = this;
		mSurfaceView = new MyGLSurfaceView(this); // 创建MySurfaceView对象
		mSurfaceView.requestFocus(); // 获取焦点
		mSurfaceView.setFocusableInTouchMode(true); // 设置为可触控
		LinearLayout ll = (LinearLayout) this.findViewById(R.id.lla); // 获得线性布局的引用
		ll.addView(mSurfaceView);//
		RatingBar rb; // 声明拖拉条引用
		rb = (RatingBar) findViewById(R.id.RatingBar01);
		rb.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				if (rating >= 0 && rating <= 1) { // RatingBar的变化来改变开启灯光的数量。
					mSurfaceView.setOpenLightNum(1);
				} else if (rating > 1 && rating <= 2) {
					mSurfaceView.setOpenLightNum(2);
				} else if (rating > 2 && rating <= 3) {
					mSurfaceView.setOpenLightNum(3);
				} else if (rating > 3 && rating <= 4) {
					mSurfaceView.setOpenLightNum(4);
				} else if (rating > 4 && rating <= 5) {
					mSurfaceView.setOpenLightNum(5);
				}
				Toast.makeText(context,
						"开启了" + mSurfaceView.getOpenLightNum() + "栈灯",
						Toast.LENGTH_SHORT).show();
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
