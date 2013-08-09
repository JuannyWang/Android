/**
 * 文件名：MainActively.java
 * 创建时间：2012-10-12  下午9:12:11
 * 作者：JERRY
 * Blog ： http://blog.jerry002.com
 */
package jerry.weixin.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	public void login(View v) {		
		startActivity(new Intent(MainActivity.this, LoginActivity.class));
		this.finish();
	}

	public void register(View v) {
		// 暂时跳到登入页面
		startActivity(new Intent(MainActivity.this, RegisterActivity.class));
		this.finish();
	}

}
