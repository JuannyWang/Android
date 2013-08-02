package com.ghost.picmatch.data;

import com.ghost.picmatch.util.Tool;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 图块连线数据结构
 * 
 * @author ghost
 * 
 */
public class Line {
	public int x, y, w, h;
	public int life = 7;

	public Line(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	/**
	 * 绘制自己
	 * @param canvas
	 * @param paint
	 */
	public void drawSelf(Canvas canvas, Paint paint) {
		if(w<0) {
			w=-w;
		}
		if(h<0) {
			h=-h;
		}
		Tool.drawImage(ImageData.line, x, y, w, h, canvas, paint);
		life--;
	}
}
