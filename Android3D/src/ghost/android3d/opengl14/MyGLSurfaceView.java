/**
 * 
 */
package ghost.android3d.opengl14;

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
		switch (e.getAction()) {
		case MotionEvent.ACTION_MOVE:
			requestRender();// 重绘画面
		}
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
