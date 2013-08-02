package com.ghost.picmatch.logic;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.ghost.picmatch.util.Common;

/**
 * 游戏中的所有面板类的父类
 * @author ghost
 *
 */
public abstract class GameObject implements Common {
	
	/**
	 * 绘制该面板下的背景
	 * @param canvas
	 * @param paint
	 */
	abstract public void drawBackground(Canvas canvas,Paint paint);
	
	/**
	 * 绘制该面板下的功能图像
	 * @param canvas
	 * @param paint
	 */
	abstract public void drawFunc(Canvas canvas,Paint paint);
	
	/**
	 * 绘制该面板下的Logo位置
	 * @param canvas
	 * @param paint
	 */
	abstract public void drawLogo(Canvas canvas,Paint paint);
	
	/**
	 * 绘制该面板下的按钮位置
	 * @param canvas
	 * @param paint
	 */
	abstract public void drawButton(Canvas canvas,Paint paint);

	/**
	 * 进行对应的逻辑处理
	 */
	abstract public void doLogic();
	
	/**
	 * 进行对应的触屏事件的响应
	 * @param x
	 * @param y
	 */
	abstract public void clickAction(int x,int y);
}
