package com.autograph.gui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.autograph.data.PointS;

public class MyRender extends View {

	private Bitmap bmap;
	private Canvas cvs;
	private Path ph;
	private Paint bpt;
	private Paint pt;
	private float dx, dy;
	private final int TOUCH = 4;
	private int ScreenHeight, ScreenWidth;
	private List<PointS> map = new ArrayList<PointS>();

	public MyRender(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		ScreenHeight = 1000;
		ScreenWidth = 800;
		
//		DisplayMetrics metric = new DisplayMetrics();
//        ((WindowManager) this.getWindowToken()).getDefaultDisplay().getMetrics(metric);
//        int width = metric.widthPixels;     // ÆÁÄ»¿í¶È£¨ÏñËØ£©
//        int height = metric.heightPixels;   // ÆÁÄ»¸ß¶È£¨ÏñËØ£©
//        float density = metric.density;      // ÆÁÄ»ÃÜ¶È£¨0.75 / 1.0 / 1.5£©
//        int densityDpi = metric.densityDpi;  // ÆÁÄ»ÃÜ¶ÈDPI£¨120 / 160 / 240£©
//        
//        Log.v(VIEW_LOG_TAG, "width£º" + width);
//        Log.v(VIEW_LOG_TAG, "height£º" + height);
//        Log.v(VIEW_LOG_TAG, "density£º" + density);
//        Log.v(VIEW_LOG_TAG, "densityDpi£º" + densityDpi);

		bmap = Bitmap.createBitmap(ScreenWidth, ScreenHeight,
				Bitmap.Config.ARGB_8888);
		cvs = new Canvas(bmap);
		bpt = new Paint(Paint.DITHER_FLAG);
		pt = new Paint();
		pt.setAntiAlias(true);
		/**
		 * »­±ÊÑÕÉ«
		 */
		pt.setColor(Color.rgb(47, 79, 79));
		pt.setStyle(Paint.Style.STROKE);
		pt.setStrokeJoin(Paint.Join.ROUND);
		pt.setStrokeCap(Paint.Cap.SQUARE);
		/**
		 * »­±Ê´ÖÏ¸
		 */
		pt.setStrokeWidth(8);
	}

	public MyRender(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MyRender(Context context) {
		super(context);
		init();
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		/**
		 * »­°å±³¾°ÑÕÉ«
		 */
		canvas.drawColor(0xFFAAAAAA);
		canvas.drawBitmap(bmap, 0, 0, bpt);
		if (ph != null) {
			canvas.drawPath(ph, pt);
		}
	}

	private void PointStar(float x, float y) {
		ph.moveTo(x, y);
		dx = x;
		dy = y;
	}

	private void PointMove(float x, float y) {
		float Mx = Math.abs(x - dx);
		float My = Math.abs(y - dy);
		if (Mx >= TOUCH || My >= TOUCH) {
			ph.quadTo(dx, dy, (x + dx) / 2, (y + dy) / 2);
			dx = x;
			dy = y;
		}
	}

	private void PointUp() {
		cvs.drawPath(ph, pt);
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {

		float x = e.getX();
		float y = e.getY();

		switch (e.getAction()) {
		case MotionEvent.ACTION_DOWN:
			ph = new Path();
			this.PointStar(x, y);
			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
			addPoint((int) x, (int) y);
			this.PointMove(x, y);
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			this.PointUp();
			invalidate();
			break;
		}

		return true;
	}

	/**
	 * Ôö¼ÓÒ»¸öµã
	 * 
	 * @param x
	 * @param y
	 */
	private void addPoint(int x, int y) {
		map.add(new PointS(x, y));
	}

	/**
	 * ÇåÆÁ
	 */
	public void clearMap() {
		map.clear();
		bmap = Bitmap.createBitmap(ScreenWidth, ScreenHeight,
				Bitmap.Config.ARGB_8888);
		cvs = new Canvas(bmap);
		ph = null;
		invalidate();
	}

	/**
	 * ·µ»ØµØÍ¼Êý¾Ý
	 * 
	 * @return
	 */
	public List<PointS> getMap() {
		return map;
	}

}
