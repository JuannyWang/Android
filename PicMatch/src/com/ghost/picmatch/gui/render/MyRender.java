package com.ghost.picmatch.gui.render;

import java.util.ArrayList;

import com.ghost.picmatch.data.GameData;
import com.ghost.picmatch.data.Line;
import com.ghost.picmatch.logic.GameObject;
import com.ghost.picmatch.util.Common;
import com.ghost.picmatch.util.GameObjectFactory;
import com.ghost.picmatch.util.Tool;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

/**
 * 游戏渲染类，负责所有可视化显示
 * @author 玄雨
 * @qq 821580467
 * @date 2013-6-15
 */
public class MyRender extends SurfaceView implements Callback, Common {

	private SurfaceHolder sfh;
	private Paint paint;

	/**
	 * 界面刷星线程
	 */
	private Thread update_thread;

	/**
	 * 游戏对象
	 */
	private GameObject game;

	/**
	 * 触屏是否可用
	 */
	private boolean clickaviliable;

	public MyRender(Context context) {
		super(context);
		GameData.status = LOADING;
		GameData.context = context;
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setColor(Color.GREEN);
		paint.setAntiAlias(true);
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Canvas canvas = sfh.lockCanvas();
		GameData.scaleW = (float) canvas.getWidth() / (float) GameData.width;
		GameData.scaleH = (float) canvas.getHeight() / (float) GameData.height;
		sfh.unlockCanvasAndPost(canvas);

	}

	public void surfaceCreated(SurfaceHolder holder) {
		Canvas canvas = sfh.lockCanvas();
		GameData.scaleW = (float) canvas.getWidth() / (float) GameData.width;
		GameData.scaleH = (float) canvas.getHeight() / (float) GameData.height;
		sfh.unlockCanvasAndPost(canvas);
		update_thread = new Thread(new UpdateSurface());
		inilization();
		update_thread.start();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		GameData.flag = false;
		update_thread = null;

	}

	public boolean onTouchEvent(MotionEvent event) {
		if (clickaviliable) {
			int x = (int) ((int) event.getX() / GameData.scaleW);
			int y = (int) ((int) event.getY() / GameData.scaleH);
			game = GameObjectFactory.getInstance(GameData.status);
			game.clickAction(x, y);
			disableTouch();
		}
		return true;
	}

	private void inilization() {
		if (!Tool.loadAllImage()) {
			System.exit(-1);
		}
		GameObjectFactory.inilization();
		GameData.status = MENU;
		GameData.lines = new ArrayList<Line>();
		GameData.flag = true;
		clickaviliable = true;
	}

	private void myDraw() {
		Canvas canvas = sfh.lockCanvas();
		canvas.drawColor(Color.BLACK);
		drawBackground(canvas, paint);
		drawFunc(canvas, paint);
		drawButtons(canvas, paint);
		drawLogo(canvas, paint);
		sfh.unlockCanvasAndPost(canvas);
	}

	private void drawLogo(Canvas canvas, Paint paint2) {
		game = GameObjectFactory.getInstance(GameData.status);
		game.drawLogo(canvas, paint2);

	}

	private void drawButtons(Canvas canvas, Paint paint2) {
		game = GameObjectFactory.getInstance(GameData.status);
		game.drawButton(canvas, paint2);

	}

	private void drawFunc(Canvas canvas, Paint paint2) {
		game = GameObjectFactory.getInstance(GameData.status);
		game.drawFunc(canvas, paint2);
	}

	private void drawBackground(Canvas canvas, Paint paint2) {
		game = GameObjectFactory.getInstance(GameData.status);
		game.drawBackground(canvas, paint2);

	}

	private void myLogic() {
		game = GameObjectFactory.getInstance(GameData.status);
		game.doLogic();
	}

	/**
	 * 暂时禁用触屏点击
	 */
	private void disableTouch() {
		if (clickaviliable) {
			new Thread(new DisableTouch()).start();
		}
	}

	/**
	 * 暂时禁用触屏点击
	 * 
	 * @param time
	 */
	@SuppressWarnings("unused")
	private void disableTouch(long time) {
		if (clickaviliable) {
			new Thread(new DisableTouch(time)).start();
		}
	}

	private class DisableTouch implements Runnable {

		private long time;

		public DisableTouch() {
			this.time = 200;
		}

		public DisableTouch(long time) {
			this.time = time;
		}

		public void run() {
			// TODO Auto-generated method stub
			clickaviliable = false;
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			clickaviliable = true;
			;
		}

	}

	private class UpdateSurface implements Runnable {

		public void run() {
			while (GameData.flag) {
				long start = System.currentTimeMillis();
				myDraw();
				myLogic();
				long end = System.currentTimeMillis();
				if (end - start < GameData.fps) {
					try {
						Thread.sleep(GameData.fps - (end - start));
					} catch (InterruptedException e) {
					}
				}
			}
		}

	}

}
