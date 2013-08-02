/**
 * 
 */
package ghost.android3d.opengl04;

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
	private float myPreviousX;

	/**
	 * @param context
	 */
	public MyGLSurfaceView(Context context) {
		super(context);
		myRender = new MyGLRender();
		this.setRenderer(myRender);
		this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
	}

	public void setPerspectiveFlag(boolean flag) {
		myRender.isPerspective = flag;
	}

	public boolean isPerspectiveFlag() {
		return myRender.isPerspective;
	}

	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			float dx = x - myPreviousX;
			myRender.xAngle += dx * TOUCH_SCALE_FACTOR;
			requestRender();
			break;
		}
		myPreviousX = x;
		return true;
	}

}
