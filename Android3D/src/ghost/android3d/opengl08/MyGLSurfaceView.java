/**
 * 
 */
package ghost.android3d.opengl08;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-1-25
 */
class MyGLSurfaceView extends GLSurfaceView {

	private final float TOUCH_SCALE_FACTOR = 180.0F / 320;
	private MyGLRender myRender;
	private float myPreviousY;// 上次的触控位置Y坐标
	private float myPreviousX;// 上次的触控位置Y坐标
	private Thread updateThread;// 更新线程

	/**
	 * @param context
	 */
	public MyGLSurfaceView(Context context) {
		super(context);
		myRender = new MyGLRender();
		this.setRenderer(myRender);
		this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
		updateThread = new Thread(new UpdateThread());
		updateThread.start();
	}

	public boolean onTouchEvent(MotionEvent event) {
		float y = event.getY();
		float x = event.getX();
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			float dy = y - myPreviousY;// 计算触控笔Y位移
			float dx = x - myPreviousX;// 计算触控笔Y位移
			myRender.ball.mAngleX += dy * TOUCH_SCALE_FACTOR;// 设置沿x轴旋转角度
			myRender.ball.mAngleZ += dx * TOUCH_SCALE_FACTOR;// 设置沿z轴旋转角度
			requestRender();
			break;
		}
		myPreviousY = y;// 记录触控笔位置
		myPreviousX = x;// 记录触控笔位置
		return true;
	}

	private class UpdateThread implements Runnable {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
			while (true) {
				myRender.lightAngleGreen += 5;
				myRender.lightAngleRed += 5;
				requestRender();// 重绘画面
				try {
					Thread.sleep(50);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
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
