/**
 * 
 */
package com.ghost.fun;

import android.graphics.Color;
import android.graphics.PointF;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-7-30
 */
public class MyParticle {

	public static double RADIUS = 180;
	
	public static final int NUMS = 35;
	
	public static double RADIUS_SCALE = 1;
	
	public static final double RADIUS_SCALE_MIN = 1;
	
	public static final double RADIUS_SCALE_MAX = 2;

	public PointF position;

	public PointF shift;

	public double size;

	public double angle;

	public double speed;

	public double targetSize;

	public int color;

	public double orbit;

	public MyParticle(int x, int y) {
		position = new PointF(x, y);
		shift = new PointF(x, y);
		size = 1;
		angle = 1;
		speed = 0.01 + Math.random() * 0.04;
		targetSize = 1;
		color = Color.rgb((int) Math.round(Math.random() * 256),
				(int) Math.round(Math.random() * 256),
				(int) Math.round(Math.random() * 256));
		orbit = RADIUS * 0.5 + (RADIUS * .5 * Math.random());
	}

}
