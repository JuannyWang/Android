package ghost.patrol;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ghost.patrol.dbhelper.DBUserTool;
import ghost.patrol.dbhelper.SQLServerHelper;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class StartActivity extends Activity {

	/**
	 * 用户名选择下拉框
	 */
	private Spinner spinner;
	/**
	 * 下拉框适配器
	 */
	private ArrayAdapter<String> adapter;

	private boolean isFirst;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		setContentView(R.layout.login_n);
		spinner = (Spinner) this.findViewById(R.id.user_list);
		updateUI();
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setVisibility(View.VISIBLE);
	}

	private void updateUI() {
		// DBUserTool.open(this);
		// Cursor cursor = DBUserTool.search();
		// if (cursor.getCount() > 0) {
		// ArrayList<String> user_list = new ArrayList<String>();
		// while (cursor.moveToNext()) {
		// String name = cursor.getString(cursor
		// .getColumnIndex("username"));
		// user_list.add(name);
		// }
		// adapter = new ArrayAdapter<String>(this,
		// android.R.layout.simple_spinner_item, user_list);
		// isFirst = false;
		// } else {
		// adapter = new ArrayAdapter<String>(this,
		// android.R.layout.simple_spinner_item,
		// new String[] { "尚无数据" });
		// EditText password = (EditText) this.findViewById(R.id.password);
		// password.setHint("请先同步数据库信息");
		// password.setEnabled(false);
		// isFirst = true;
		// }

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, new String[] { " 管理员", " 演示", " 巡查员" });
		EditText password = (EditText) this.findViewById(R.id.password);
		password.setHint("请输入密码");
		password.setEnabled(true);
	}

	public void login(View v) {
		EditText password = (EditText) this.findViewById(R.id.password);
		String str = password.getText().toString();
		if (str.equals("")) {
			loginAlertDialog("警告", "密码不能为空");
		} else {
//			String username = adapter
//					.getItem(spinner.getSelectedItemPosition());
//			Cursor cursor = DBUserTool.search();
//			String str2 = "";
//			while (cursor.moveToNext()) {
//				if (cursor.getString(cursor.getColumnIndex("username")).equals(
//						username)) {
//					str2 = cursor.getString(cursor.getColumnIndex("password"));
//				}
//			}
//			if (str.equals(str2)) {
//				Intent intent = new Intent(StartActivity.this,
//						TaskTableActivity.class);
//				intent.putExtra("name",
//						adapter.getItem(spinner.getSelectedItemPosition()));
//				startActivity(intent);
//				overridePendingTransition(R.layout.in_from_right,
//						R.layout.out_to_left);
//			} else {
//				loginAlertDialog("警告", "密码错误");
//			}
			
			if (str.equals("123")) {
				Intent intent = new Intent(StartActivity.this,
						TaskTableActivity.class);
				intent.putExtra("name",
						adapter.getItem(spinner.getSelectedItemPosition()));
				startActivity(intent);
				overridePendingTransition(R.layout.in_from_right,
						R.layout.out_to_left);
			} else {
				loginAlertDialog("警告", "密码错误");
			}
		}
	}

	private void loginAlertDialog(String title, String message) {
		new AlertDialog.Builder(StartActivity.this)
				.setIcon(
						getResources().getDrawable(R.drawable.login_error_icon))
				.setTitle(title + ":" + message).create().show();
	}

	public void synData() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				SQLServerHelper.connect();
				if (isFirst) {
					ResultSet result = SQLServerHelper
							.doSql("SELECT * FROM dbo.UserTable");
					try {
						while (result.next()) {
							int UserID = result.getInt("UserID");
							String username = result.getString("username");
							String password = result.getString("password");
							String name = result.getString("name");
							String sex = result.getString("sex");
							int age = result.getInt("age");
							int purview = result.getInt("purview");
							String telephone = result.getString("telephone");
							String department = result.getString("department");
							String team = result.getString("team");
							String monitor = result.getString("monitor");
							String passForFinder = result
									.getString("passForFinder");
							String passForManager = result
									.getString("passForManager");
							DBUserTool.insert(UserID, username, password, name,
									sex, age, purview, telephone, department,
									team, monitor, passForFinder,
									passForManager);
						}

					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {

				}
				Intent intent = getIntent();
				overridePendingTransition(0, 0);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				finish();
				overridePendingTransition(0, 0);
				startActivity(intent);
			}
		}).start();

	}

	/**
	 * 退出前的确认对话框
	 */
	public void exitActivity() {
		new AlertDialog.Builder(StartActivity.this).setTitle("确认退出吗？")
				.setIcon(R.drawable.login_error_icon)
				.setPositiveButton("确定", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						StartActivity.this.finish();
					}
				}).setNegativeButton("取消", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_syn:
			synData();
			break;
		case R.id.menu_exit:
			exitActivity();
			break;
		}
		return true;
	}

	public void onBackPressed() {
		exitActivity();
	}

}
