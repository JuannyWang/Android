/**
 * 
 */
package ghost.patrol;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-3-8
 */
public class DisplayUserActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		setContentView(R.layout.activity_display);
		Button btn_back = (Button) this.findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				exitActivity();
			}
		});
	}

	private void exitActivity() {
		DisplayUserActivity.this.finish();
		overridePendingTransition(R.layout.in_from_right, R.layout.out_to_left);
	}

	public void onBackPressed() {
		exitActivity();
	}

}
