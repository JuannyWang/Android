package com.autograph;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.autograph.gui.MyRender;
import com.autograph.logic.HyperEdgeFactory;

public class MainActivity extends Activity {

	private MyRender render;
	private EditText et;
	private String word;
	private HyperEdgeFactory hyperEdgeFactory_;
	
	private MyTouchListener myTouchListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myTouchListener = new MyTouchListener();
		render = (MyRender) this.findViewById(R.id.display_panel);
		hyperEdgeFactory_ = new HyperEdgeFactory();
		hyperEdgeFactory_.check();
		hyperEdgeFactory_.initialize();
		word = "请输入";

		/**
		 * 清屏
		 */
		ImageButton clear = (ImageButton) this.findViewById(R.id.btn_clear);
		clear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				render.clearMap();
			}
		});
		clear.setOnTouchListener(myTouchListener);

		/**
		 * 退出
		 */
		ImageButton exit = (ImageButton) this.findViewById(R.id.btn_exit);
		exit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				exit();
			}
		});
		exit.setOnTouchListener(myTouchListener);

		/**
		 * 格式化
		 */
		ImageButton format = (ImageButton) this.findViewById(R.id.btn_delete);
		format.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				alertDelete();
			}
		});
		format.setOnTouchListener(myTouchListener);

		/**
		 * 学习面板
		 */
		ImageButton learn = (ImageButton) this.findViewById(R.id.btn_learn);
		learn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				alertEdit();
			}
		});
		learn.setOnTouchListener(myTouchListener);

		/**
		 * 识别面板
		 */
		ImageButton distinguish = (ImageButton) this.findViewById(R.id.btn_distinguish);
		distinguish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				alertView(word);
			}
		});
		distinguish.setOnTouchListener(myTouchListener);
		
		/**
		 * 训练
		 */
		ImageButton train = (ImageButton) this.findViewById(R.id.btn_train);
		train.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(MainActivity.this, "训练中...", Toast.LENGTH_SHORT).show();
				hyperEdgeFactory_.train();
				Toast.makeText(MainActivity.this, "训练完成，可以开始识别", Toast.LENGTH_SHORT).show();
			}
		});
		train.setOnTouchListener(myTouchListener);
	}

	private void alertView(String message) {
		new AlertDialog.Builder(this).setTitle("确认")
				.setMessage(hyperEdgeFactory_.recog(render.getMap()))
				.setPositiveButton("是", null).setNegativeButton("否", null)
				.show();
	}

	private void alertDelete() {
		et = new EditText(this);
		et.setText(word);
		new AlertDialog.Builder(this).setTitle("要删除谁")
				.setIcon(android.R.drawable.ic_dialog_info).setView(et)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						word = et.getText().toString();
						hyperEdgeFactory_.delete(word);
					}
				}).setNegativeButton("取消", null).show();
	}

	private void alertEdit() {
		et = new EditText(this);
		et.setText(word);
		if ( render.getMap().size() < 1 )
			return;
		new AlertDialog.Builder(this).setTitle("这是谁写的")
				.setIcon(android.R.drawable.ic_dialog_info).setView(et)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						word = et.getText().toString();
						hyperEdgeFactory_.learn(render.getMap(), word);
					}
				}).setNegativeButton("取消", null).show();
	}

	@Override
	public void onBackPressed() {
		exit();
	}

	private void exit() {
		this.finish();
	}
	
	private class MyTouchListener implements View.OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if(event.getAction()==MotionEvent.ACTION_DOWN){
				((ImageButton)v).getDrawable().setAlpha(150);
				((ImageButton)v).invalidate();
			} else if(event.getAction()==MotionEvent.ACTION_UP){
				((ImageButton)v).getDrawable().setAlpha(255);
				((ImageButton)v).invalidate();
			}
			return false;
		}
	}

}
