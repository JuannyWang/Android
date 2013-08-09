/**
 * 
 */
package com.ghost.justdraw.gui;

import java.util.LinkedList;
import java.util.List;

import com.ghost.justdraw.bean.LineData;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-8-8
 */
public class DrawView extends View {

	private static DrawView thisObject;

	/**
	 * 返回已经实例化的View对象，必须在实例化之后才能正常使用
	 * 
	 * @return
	 */
	public static DrawView getInstance() {
		return thisObject;
	}

	/**
	 * 当前线条
	 */
	private LineData workLine;

	/**
	 * 绘制的线条列表，用于绘制和回退
	 */
	private List<LineData> lines;

	/**
	 * 新线条的参数
	 */
	private int size, color;

	/**
	 * 桌布的背景色
	 */
	private int backColor;

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public DrawView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initData();
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public DrawView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initData();
	}

	/**
	 * 
	 * @param context
	 */
	public DrawView(Context context) {
		super(context);
		initData();
	}

	/**
	 * 数据初始化
	 */
	private void initData() {
		backColor = Color.WHITE;
		size = 5;
		color = Color.BLACK;
		workLine = new LineData(size, color);
		lines = new LinkedList<LineData>();

		/**
		 * 为单例模型赋值
		 */
		thisObject = this;
	}

	/**
	 * 移除掉最后一条线(该方法会自动重绘)
	 */
	public void removeLast() {
		if (lines.size() > 0) {
			lines.remove(lines.size() - 1);
			invalidate();
		}
	}

	/**
	 * 设置背景色(该方法会自动重绘)
	 * 
	 * @param color
	 */
	public void setBackColor(int color) {
		this.backColor = color;
		invalidate();
	}

	/**
	 * 得到当前背景色
	 * 
	 * @return
	 */
	public int getBackColor() {
		return this.backColor;
	}

	/**
	 * 得到当前线条的颜色
	 * 
	 * @return
	 */
	public int getColor() {
		return this.color;
	}

	/**
	 * 设置画笔颜色(该方法不会自动重绘)
	 * 
	 * @param color
	 */
	public void setColor(int color) {
		this.color = color;
		workLine = new LineData(size, color);
	}

	/**
	 * 设置画笔粗细(该方法不会自动重绘)
	 * 
	 * @param size
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * 清楚所有线条(该方法会自动重绘)
	 */
	public void resetView() {
		workLine = new LineData(size, color);
		lines.clear();
		invalidate();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			workLine.funcDown(x, y);
			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
			workLine.funcMove(x, y);
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			workLine.funcUp(x, y);
			invalidate();
			lines.add(workLine);
			workLine = new LineData(size, color);
			break;
		}

		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(backColor);
		for (int i = 0; i < lines.size(); ++i) {
			lines.get(i).drawSelf(canvas);
		}
		workLine.drawSelf(canvas);
	}
}