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
public class CreateViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		setContentView(R.layout.activity_create_view);
		Button btn_next = (Button) this.findViewById(R.id.button1);
		btn_next.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startDefect();
			}
		});
		Button btn_cancel = (Button) this.findViewById(R.id.button2);
		btn_cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				cancelActivity();
			}
		});
	}
	
	private void startDefect() {
		Intent intent = new Intent(CreateViewActivity.this,
				DefectDetailActivity.class);
		intent.putExtra("name", "null");
		startActivityForResult(intent, 0);
		overridePendingTransition(R.layout.in_from_right, R.layout.out_to_left);
	}

	private void cancelActivity() {
		Intent intent = new Intent(CreateViewActivity.this,GPSLocationActivity.class);
		intent.putExtra("key", "exit");
		setResult(RESULT_OK, intent);
		CreateViewActivity.this.finish();
	}

	private void exitActivity() {
		CreateViewActivity.this.finish();
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
					cancelActivity();
				}
		}
	}

}
