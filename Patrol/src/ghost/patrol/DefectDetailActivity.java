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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-3-10
 */
public class DefectDetailActivity extends Activity {

	/**
	 * 用于临时储存列表数据
	 */
	private String data_list[] = new String[] { "选项1", "选项2", "选项3", };

	/**
	 * spinner的适配器
	 */
	private ArrayAdapter<String> adapter1, adapter2, adapter3;

	/**
	 * 下拉选择框
	 */
	private Spinner spinner1, spinner2, spinner3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		setContentView(R.layout.activity_defet);
		spinner1 = (Spinner) this.findViewById(R.id.spinner1);
		adapter1 = new ArrayAdapter<String>(this, R.layout.activity_spinner2,
				data_list);
		spinner1.setAdapter(adapter1);
		spinner2 = (Spinner) this.findViewById(R.id.spinner2);
		adapter2 = new ArrayAdapter<String>(this, R.layout.activity_spinner2,
				data_list);
		spinner2.setAdapter(adapter2);
		spinner3 = (Spinner) this.findViewById(R.id.spinner3);
		adapter3 = new ArrayAdapter<String>(this, R.layout.activity_spinner2,
				data_list);
		spinner3.setAdapter(adapter3);
		Button btn_next = (Button) this.findViewById(R.id.button_next);
		btn_next.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startNotice();
			}
		});
		Button btn_above = (Button) this.findViewById(R.id.button_up);
		btn_above.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				exitActivity();
			}
		});
	}

	private void startNotice() {
		Intent intent = new Intent(DefectDetailActivity.this,
				NoticeActivity.class);
		intent.putExtra("name", "null");
		startActivityForResult(intent, 0);
		overridePendingTransition(R.layout.in_from_right, R.layout.out_to_left);
	}

	private void exitActivity() {
		DefectDetailActivity.this.finish();
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
					Intent intent = new Intent(DefectDetailActivity.this,CreateViewActivity.class);
					intent.putExtra("key", "exit");
					setResult(RESULT_OK, intent);
					DefectDetailActivity.this.finish();
				}
		}
	}

}
