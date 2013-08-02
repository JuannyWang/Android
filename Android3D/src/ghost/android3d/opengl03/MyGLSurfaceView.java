/**
 * 
 */
package ghost.android3d.opengl03;

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

	public void setBackFlag(boolean flag) { //
		myRender.backFlag = flag;
	}

	public boolean isBackFlag() { //
		return myRender.backFlag;
	}

	public void setSmoothFlag(boolean flag) { //
		myRender.smoothFlag = flag;
	}

	public boolean isSmoothFlag() { //
		return myRender.smoothFlag;
	}

	public void setSelfCulling(boolean flag) { //
		myRender.selfCulling = flag;
	}

	public boolean isSelfCulling() { //
		return myRender.selfCulling;
	}

	public boolean onTouchEvent(MotionEvent event) {
		float y = event.getY();
		float x = event.getX();
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			float dy = y - myPreviousY;
			float dx = x - myPreviousX;
			myRender.triangles.yAngle += dx * TOUCH_SCALE_FACTOR;
			myRender.triangles.zAngle += dy * TOUCH_SCALE_FACTOR;
			requestRender();
			break;
		}
		myPreviousX = x;
		myPreviousY = y;
		return true;
	}

}
