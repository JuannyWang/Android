package ghost.picwall;

import android.os.Bundle;

public class MainActivity extends RajawaliExampleActivity {
	private Rajawali2000PlanesRenderer mRenderer;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRenderer = new Rajawali2000PlanesRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
		initLoader();
	}
}
