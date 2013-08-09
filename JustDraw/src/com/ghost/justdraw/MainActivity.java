package com.ghost.justdraw;

import com.ghost.justdraw.gui.ColorPickerDialog;
import com.ghost.justdraw.gui.DrawView;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;

/**
 * 主界面
 * @author 玄雨
 * @qq 821580467
 * @date 2013-8-9
 */
public class MainActivity extends Activity {

	/**
	 * 绘制面板
	 */
	private DrawView drawView;

	/**
	 * 按钮触摸反馈
	 */
	private OnTouchListener tl;

	/**
	 * 颜色选择器
	 */
	private ColorPickerDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.draw_layout);
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		tl = new MyTouchListener();
		drawView = DrawView.getInstance();

		addEffect(R.id.btn_backColor);
		addEffect(R.id.btn_clear);
		addEffect(R.id.btn_color);
		addEffect(R.id.btn_goBack);
		addEffect(R.id.btn_size);
	}

	/**
	 * 为指定ID按钮增加效果
	 * 
	 * @param id
	 */
	private void addEffect(int id) {
		ImageButton tempBtn = (ImageButton) this.findViewById(id);
		tempBtn.setOnTouchListener(tl);
	}

	/**
	 * 重置画板
	 * 
	 * @param v
	 */
	public void doReset(View v) {
		drawView.resetView();
	}

	/**
	 * 撤销上一步
	 */
	public void doBack(View v) {
		drawView.removeLast();
	}

	/**
	 * 选择背景颜色
	 * 
	 * @param v
	 */
	public void setBackColor(View v) {
		dialog = new ColorPickerDialog(this, drawView.getBackColor(), "选择背景颜色",
				new ColorPickerDialog.OnColorChangedListener() {

					@Override
					public void colorChanged(int color) {
						drawView.setBackColor(color);
					}
				});
		dialog.show();
	}

	/**
	 * 选择线条颜色
	 * 
	 * @param v
	 */
	public void setLineColor(View v) {
		dialog = new ColorPickerDialog(this, drawView.getColor(), "选择线条颜色",
				new ColorPickerDialog.OnColorChangedListener() {

					@Override
					public void colorChanged(int color) {
						drawView.setColor(color);
					}
				});
		dialog.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * 触摸时变透明
	 * 
	 * @author 玄雨
	 * @qq 821580467
	 * @date 2013-8-8
	 */
	private class MyTouchListener implements View.OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				((ImageButton) v).getDrawable().setAlpha(150);
				((ImageButton) v).invalidate();
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				((ImageButton) v).getDrawable().setAlpha(255);
				((ImageButton) v).invalidate();
			}
			return false;
		}
	}

}
