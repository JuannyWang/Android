package com.ghost.picmatch.data;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Point;

/**
 * 用于记录游戏的状态值,与平台无关
 * 
 * @author ghost
 * 
 */
public class GameData {
	/**
	 * 游戏状态值
	 */
	public static int status;

	/**
	 * 游戏分值
	 */
	public static int score;

	/**
	 * 游戏关卡
	 */
	public static int grade;

	/**
	 * 游戏地图数据
	 */
	public static int map[][] = new int[8][7];

	/**
	 * 地图附加数据
	 */
	public static int mapvalue[][][] = new int[8][7][10];
	
	/**
	 * 记录连线的数据
	 */
	public static ArrayList<Line> lines;

	/**
	 * 游戏进行的时间状态值
	 */
	public static int time;

	/**
	 * 游戏渲染开关
	 */
	public static boolean flag;

	/**
	 * 最大时间限制
	 */
	public static final int max_time = 2000;

	/**
	 * 游戏帧率限制
	 */
	public static final int fps = 50;
	
	/**
	 * 自动适应屏幕大小转换比例
	 */
	public static float scaleW, scaleH;
	
	/**
	 * 储存的点击的两个点
	 */
	public static Point p1, p2;
	
	/**
	 * 绘制屏幕画布大小
	 */
	public static final int width = 480;
	public static final int height = 800;
	
	/**
	 * android 中的系统上下文传递参数
	 */
	public static Context context;

}
