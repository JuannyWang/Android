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
 * @date 2013-3-10
 */
public class NoticeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		setContentView(R.layout.activity_note);
		Button btn_finish = (Button) this.findViewById(R.id.button_finish);
		btn_finish.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finishActivity();
			}
		});
		Button btn_up = (Button) this.findViewById(R.id.button_up);
		btn_up.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				exitActivity();
			}
		});
	}
	
	private void finishActivity() {
		Intent intent = new Intent(NoticeActivity.this,DefectDetailActivity.class);
		intent.putExtra("key", "exit");
		setResult(RESULT_OK, intent);
		NoticeActivity.this.finish();
		overridePendingTransition(R.layout.in_from_right, R.layout.out_to_left);
	}
	
	private void exitActivity() {
		NoticeActivity.this.finish();
		overridePendingTransition(R.layout.in_from_right, R.layout.out_to_left);
	}

	public void onBackPressed() {
		exitActivity();
	}

}
