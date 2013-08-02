/**
 * 
 */
package ghost.android3d.opengl11;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-1-25
 */
class MyGLSurfaceView extends GLSurfaceView {

	private MyGLRender myRender;
	private float mPreviousY;// 上次的触控位置Y坐标
	private float mPreviousX;// 上次的触控位置X坐标
	private float TOUCH_SCALE_FACTOR = 180.0f / 320;;// 角度缩放比例

	/**
	 * @param context
	 */
	public MyGLSurfaceView(Context context) {
		super(context);
		myRender = new MyGLRender(context);
		this.setRenderer(myRender);
		this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		float y = e.getY();
		float x = e.getX();
		switch (e.getAction()) {
		case MotionEvent.ACTION_MOVE:
			float dy = y - mPreviousY;// 计算触控笔Y位移
			float dx = x - mPreviousX;// 计算触控笔Y位移
			myRender.texTri.mAngleY += dy * TOUCH_SCALE_FACTOR;// 设置沿x轴旋转角度
			myRender.texTri.mAngleZ += dx * TOUCH_SCALE_FACTOR;// 设置沿z轴旋转角度
			requestRender();// 重绘画面
		}
		mPreviousY = y;// 记录触控笔位置
		mPreviousX = x;// 记录触控笔位置
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.opengl.GLSurfaceView#onPause()
	 */
	@Override
	public void onPause() {
		super.onPause();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.opengl.GLSurfaceView#onResume()
	 */
	@Override
	public void onResume() {
		super.onResume();
	}

}
