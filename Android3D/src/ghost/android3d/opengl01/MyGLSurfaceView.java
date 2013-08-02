/**
 * 
 */
package ghost.android3d.opengl01;

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
	private float myPreviousY;
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

	public boolean onTouchEvent(MotionEvent event) {
		float y = event.getY();
		float x = event.getX();
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			float dy = y - myPreviousY;
			float dx = x - myPreviousX;
			myRender.tr.yAngle += dx * TOUCH_SCALE_FACTOR;
			myRender.tr.zAngle += dy * TOUCH_SCALE_FACTOR;
			requestRender();
			break;
		}
		myPreviousX = x;
		myPreviousY = y;
		return true;
	}

}
