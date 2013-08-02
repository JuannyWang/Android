/**
 * 
 */
package ghost.android3d.opengl02;

import ghost.android3d.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-1-25
 */
public class PointAndLineActivity extends Activity {
	private LinearLayout ll;
	private MyGLSurfaceView surfaceView;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.firsttriangle_layout);
		ll = (LinearLayout) this.findViewById(R.id.LinearLayout);
		surfaceView = new MyGLSurfaceView(this);
		surfaceView.requestFocus();
		surfaceView.setFocusableInTouchMode(true);
		ll.addView(surfaceView);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
		surfaceView.onPause();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		surfaceView.onResume();
	}
}
