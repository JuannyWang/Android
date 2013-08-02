/**
 * 
 */
package com.ghost.activitys;

import com.ghost.R;
import com.ghost.utils.Constants;
import com.ghost.utils.LocalMessage;
import com.ghost.utils.SharePreferenceUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-7-4
 */
public class LoginActivity extends Activity implements OnClickListener{
	
	private View mMoreView;// “更多登录选项”的view
	private ImageView mMoreImage;// “更多登录选项”的箭头图片
	private View mMoreMenuView;// “更多登录选项”中的内容view
	private Button mBtnLogin;
	private boolean mShowMenu = false;// “更多登录选项”的内容是否显示
	
	private SharePreferenceUtil util;
	
	private EditText mAccounts, mPassword;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		util = new SharePreferenceUtil(
				LoginActivity.this, Constants.SAVE_USER);
		
		mMoreView = findViewById(R.id.more);
		mMoreMenuView = findViewById(R.id.moremenu);
		mMoreImage = (ImageView) findViewById(R.id.more_image);
		mAccounts = (EditText) findViewById(R.id.lgoin_accounts);
		mPassword = (EditText) findViewById(R.id.login_password);
		mBtnLogin = (Button) findViewById(R.id.login_btn);
		mBtnLogin.setOnClickListener(this);
		mMoreView.setOnClickListener(this);
	}
	
	/**
	 * “更多登录选项”内容的显示方法
	 * 
	 * @param bShow
	 *            是否显示
	 */
	public void showMoreView(boolean bShow) {
		if (bShow) {
			mMoreMenuView.setVisibility(View.GONE);
			mMoreImage.setImageResource(R.drawable.login_more_up);
			mShowMenu = true;
		} else {
			mMoreMenuView.setVisibility(View.VISIBLE);
			mMoreImage.setImageResource(R.drawable.login_more);
			mShowMenu = false;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.more:
			showMoreView(!mShowMenu);
			break;
		case R.id.regist_btn:
			break;
		case R.id.login_btn:
			submit();
			break;
		default:
			break;
		}
	}
	
	public void submit() {
		util.setId(mAccounts.getText().toString());
		util.setPasswd(mPassword.getText().toString());
		//util.setIp(LocalMessage.getLocalIP(this));
		
		/**
		 * 向服务器发送消息
		 */
		
		/**
		 * 从服务器接受消息并判断验证
		 */
		
		//暂用
		util.setIsFirst(false);
		
		Intent intent = new Intent(this, TempActivity.class);
		startActivity(intent);
		finish();
	}
	
}
