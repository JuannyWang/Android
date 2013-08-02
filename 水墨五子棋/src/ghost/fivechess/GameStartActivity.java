package ghost.fivechess;

import ghost.fivechess.gui.GamePanel;
import android.os.Bundle;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.Window;
import android.view.WindowManager;

public class GameStartActivity extends Activity {
	
	GamePanel panel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		panel=new GamePanel(this);
		setContentView(panel);
	}

	@Override
	public void onBackPressed() {
		panel.backPress();
	}
	
	

}
