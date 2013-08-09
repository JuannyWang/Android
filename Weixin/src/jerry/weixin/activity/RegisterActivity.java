/**
 * 文件名：RegisterActivity.java
 * 创建时间：2012-10-13  下午2:16:48
 * 作者：JERRY
 * Blog ： http://blog.jerry002.com
 */
package jerry.weixin.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RegisterActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.register);
	}
	
	// 标题栏 返回按钮
	public void back(View v) {
		Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
		startActivity(intent);
		this.finish();
	}
	
}
