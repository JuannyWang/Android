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
 * @date 2013-3-7
 */
public class LoginActivity extends Activity {

	/**
	 * 用于储存身份列表数据
	 */
	private String data_list[] = new String[] { " 管理员", " 演示", " 巡查员", };
	/**
	 * spinner的适配器
	 */
	private ArrayAdapter<String> adapter;

	/**
	 * 下拉选择框
	 */
	private Spinner spinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		setContentView(R.layout.activity_login);
		spinner = (Spinner) this.findViewById(R.id.spinner);
		adapter = new ArrayAdapter<String>(this, R.layout.activity_spinner,
				data_list);
		spinner.setAdapter(adapter);
		Button btn_back = (Button) this.findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				exitActivity();
			}
		});
		Button btn_login = (Button) this.findViewById(R.id.btn_login);
		btn_login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startTable();
			}
		});
	}

	private void startTable() {
		Intent intent = new Intent(LoginActivity.this, TaskTableActivity.class);
		intent.putExtra("name",
				adapter.getItem(spinner.getSelectedItemPosition()));
		startActivity(intent);
		overridePendingTransition(R.layout.in_from_right, R.layout.out_to_left);
	}

	private void exitActivity() {
		LoginActivity.this.finish();
		overridePendingTransition(R.layout.in_from_right, R.layout.out_to_left);
	}

	public void onBackPressed() {
		exitActivity();
	}

}
