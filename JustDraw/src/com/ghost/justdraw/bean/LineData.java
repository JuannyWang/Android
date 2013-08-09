/**
 * 
 */
package com.ghost.justdraw.bean;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-8-8
 */
public class LineData {

	public static final float TOUCH_TOLERANCE = 1;

	public Paint paint;

	public Path path;

	private float oX, oY;

	public LineData(int size, int color) {
		path = new Path();
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(color);
		paint.setStrokeWidth(size);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeCap(Paint.Cap.ROUND);
	}

	/**
	 * 触摸按下时的响应
	 * 
	 * @param x
	 * @param y
	 */
	public void funcDown(float x, float y) {
		path.moveTo(x, y);
		oX = x;
		oY = y;
	}

	/**
	 * 触摸点移动时的响应
	 * 
	 * @param x
	 * @param y
	 */
	public void funcMove(float x, float y) {
		float dx = Math.abs(x - oX);
		float dy = Math.abs(y - oY);
		if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
			path.quadTo(oX, oY, (x + oX) / 2, (y + oY) / 2);
			oX = x;
			oY = y;
		}
	}

	/**
	 * 触摸点弹起时的响应
	 * 
	 * @param x
	 * @param y
	 */
	public void funcUp(float x, float y) {
		path.lineTo(x, y);
	}
	
	/**
	 * 设置画笔颜色
	 * @param color
	 */
	public void setColor(int color) {
		this.paint.setColor(color);
	}

	/**
	 * 绘制自身
	 * 
	 * @param canvas
	 */
	public void drawSelf(Canvas canvas) {
		if (canvas == null)
			return;
		canvas.drawPath(path, paint);
	}

}
