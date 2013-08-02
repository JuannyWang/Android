/**
 * 
 */
package ghost.patrol;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-3-9
 */
public class GPSLocationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		setContentView(R.layout.activity_gps);
		Button btn_action = (Button) this.findViewById(R.id.button1);
		btn_action.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startTourItem();
			}
		});
	}

	private void startTourItem() {
		Intent intent = new Intent(GPSLocationActivity.this,
				TourItemActivity.class);
		startActivityForResult(intent, 0);
		GPSLocationActivity.this.finish();
		overridePendingTransition(R.layout.in_from_right, R.layout.out_to_left);
	}

	private void exitActivity() {
		GPSLocationActivity.this.finish();
		overridePendingTransition(R.layout.in_from_right, R.layout.out_to_left);
	}

	public void onBackPressed() {
		exitActivity();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			Bundle bundle = data.getExtras();
			if ("exit".equals(bundle.getString("key"))) {
				exitActivity();
			}
		}
	}

}
