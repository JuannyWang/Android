package com.ghost.justdraw;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ghost.justdraw.gui.ColorPickerDialog;
import com.ghost.justdraw.gui.DrawView;
import com.ghost.justdraw.gui.LineSizePickerDialog;

/**
 * 主界面
 * 
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
	private ColorPickerDialog colorDialog;

	/**
	 * 线条粗细选择器
	 */
	private LineSizePickerDialog sizeDialog;

	/**
	 * 文件名称输入框
	 */
	private EditText tempET;

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
		colorDialog = new ColorPickerDialog(this, drawView.getBackColor(),
				"选择背景颜色", new ColorPickerDialog.OnColorChangedListener() {

					@Override
					public void colorChanged(int color) {
						drawView.setBackColor(color);
					}
				});
		colorDialog.show();
	}

	/**
	 * 选择线条颜色
	 * 
	 * @param v
	 */
	public void setLineColor(View v) {
		colorDialog = new ColorPickerDialog(this, drawView.getColor(),
				"选择线条颜色", new ColorPickerDialog.OnColorChangedListener() {

					@Override
					public void colorChanged(int color) {
						drawView.setColor(color);
					}
				});
		colorDialog.show();
	}

	/**
	 * 设置线条粗细
	 * 
	 * @param v
	 */
	public void setLineSize(View v) {
		sizeDialog = new LineSizePickerDialog(this, drawView.getSize(),
				new LineSizePickerDialog.onConfirmClick() {

					@Override
					public void onConfirm(int value) {
						drawView.setSize(value);
					}
				});
		sizeDialog.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		/*
		 * 
		 * add()方法的四个参数，依次是：
		 * 
		 * 1、组别，如果不分组的话就写Menu.NONE,
		 * 
		 * 2、Id，这个很重要，Android根据这个Id来确定不同的菜单
		 * 
		 * 3、顺序，那个菜单现在在前面由这个参数的大小决定
		 * 
		 * 4、文本，菜单的显示文本
		 */

		// setIcon()方法为菜单设置图标，这里使用的是系统自带的图标，同学们留意一下,以

		// android.R开头的资源是系统提供的，我们自己提供的资源是以R开头的

		menu.add(Menu.NONE, Menu.FIRST + 1, 1, "保存").setIcon(
				android.R.drawable.ic_menu_edit);

		menu.add(Menu.NONE, Menu.FIRST + 2, 2, "关于").setIcon(
				android.R.drawable.ic_menu_info_details);

		menu.add(Menu.NONE, Menu.FIRST + 3, 3, "退出").setIcon(
				android.R.drawable.ic_menu_close_clear_cancel);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case Menu.FIRST + 1:// 保存
			save();
			break;

		case Menu.FIRST + 2:// 关于
			showInfo();
			break;

		case Menu.FIRST + 3:// 退出
			exitConfirm();
			break;

		default:
			Toast.makeText(this, "出错啦..." + item.getItemId(),
					Toast.LENGTH_SHORT).show();
			break;

		}

		return false;

	}

	/**
	 * 显示软件信息
	 */
	private void showInfo() {
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.info_dialog, null);
		new AlertDialog.Builder(this).setTitle("软件说明").setView(layout)
				.setPositiveButton("确定", null)
				.show();
	}

	/**
	 * 保存
	 */
	private void save() {
		tempET = new EditText(this);
		new AlertDialog.Builder(this).setTitle("请输入文件名")
				.setIcon(android.R.drawable.ic_dialog_info).setView(tempET)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						String fileNmae = tempET.getText().toString();
						saveToFile(fileNmae);
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 点击“返回”后的操作,这里不设置没有任何操作
					}
				}).show();
	}

	/**
	 * 保存图片到本地
	 */
	private void saveToFile(String fileName) {
		fileName = fileName + ".png";
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			// 创建一个文件夹对象，赋值为外部存储器的目录
			File sdcardDir = Environment.getExternalStorageDirectory();
			// 得到一个路径，内容是sdcard的文件夹路径和名字
			String path = sdcardDir.getPath() + "/JustDrawData";
			File path1 = new File(path);
			if (!path1.exists()) {
				// 若不存在，创建目录，可以在应用启动的时候创建
				path1.mkdirs();
				setTitle("paht ok,path:" + path);
			}
			File image = new File(path1.getPath() + "/" + fileName);
			if (image.exists()) {
				Toast.makeText(this, "名称已存在", Toast.LENGTH_SHORT).show();
			} else {
				try {
					image.createNewFile();
					FileOutputStream fos = new FileOutputStream(image);
					drawView.getBitmapData().compress(
							Bitmap.CompressFormat.PNG, 100, fos);
					fos.flush();
					Toast.makeText(this, "保存" + image.getPath() + "成功",
							Toast.LENGTH_SHORT).show();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			Toast.makeText(this, "相关权限不足", Toast.LENGTH_SHORT).show();
			return;

		}
	}

	@Override
	public void onBackPressed() {
		exitConfirm();
	}

	private void exitConfirm() {
		new AlertDialog.Builder(this).setTitle("确认退出吗？")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 点击“确认”后的操作
						MainActivity.this.finish();

					}
				})
				.setNegativeButton("返回", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 点击“返回”后的操作,这里不设置没有任何操作
					}
				}).show();
	}

	@Override
	public void onOptionsMenuClosed(Menu menu) {
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
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
