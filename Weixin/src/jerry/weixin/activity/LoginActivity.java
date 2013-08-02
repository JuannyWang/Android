/**
 * 文件名：LoginActivity.java
 * 创建时间：2012-10-13  下午2:16:25
 * 作者：JERRY
 * Blog ： http://blog.jerry002.com
 */
package jerry.weixin.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText usernameBut = null;
	private EditText passwordBut = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		usernameBut = (EditText) findViewById(R.id.username);
		passwordBut = (EditText) findViewById(R.id.password);

	}

	// 登入按钮
	public void login(View v) {
		if (usernameBut.getText().toString().equals("")
				&& passwordBut.getText().toString().equals("")) {
			loginAlertDialog("登入错误", "用户名密码不能为空");
		} else {
			Toast.makeText(getApplicationContext(), "登入成功", Toast.LENGTH_SHORT)
					.show();
			// 暂时跳到上级页面
			startActivity(new Intent(LoginActivity.this, MainActivety.class));
		}
	}

	// 标题栏 返回按钮
	public void back(View v) {
		this.finish();
	}

	// 忘记密码
	public void forgetPassword(View v) {
		Toast.makeText(getApplicationContext(), "你忘记密码了，我也不知道怎么办",
				Toast.LENGTH_SHORT).show();
	}

	public void loginAlertDialog(String title, String message) {
		new AlertDialog.Builder(LoginActivity.this)
				.setIcon(
						getResources().getDrawable(R.drawable.login_error_icon))
				.setTitle(title).setMessage(message).create().show();
	}
}
