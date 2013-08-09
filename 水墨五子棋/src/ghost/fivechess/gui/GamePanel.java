/**
 * 
 */
package ghost.fivechess.gui;

import ghost.fivechess.bean.Common;
import ghost.fivechess.bean.GameData;
import ghost.fivechess.bean.ImageFactory;
import ghost.fivechess.bean.PanelFactory;
import ghost.fivechess.gui.panel.PanelObject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

/**
 * 游戏渲染类
 * 
 * @author 玄雨
 * @qq 821580467
 * @date 2013-1-19
 */
public class GamePanel extends SurfaceView implements Callback, Common {
	private SurfaceHolder sfh;
	private Paint paint;

	/**
	 * 界面刷星线程
	 */
	private Thread update_thread;

	/**
	 * 游戏对象
	 */
	private PanelObject game;

	/**
	 * 触屏是否可用
	 */
	private boolean clickaviliable;

	public GamePanel(Context context) {
		super(context);
		GameData.status = MENU;
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
		GameData.scaleWidth = (float) canvas.getWidth()
				/ (float) GameData.width;
		GameData.scaleHeight = (float) canvas.getHeight()
				/ (float) GameData.height;
		sfh.unlockCanvasAndPost(canvas);

	}

	public void surfaceCreated(SurfaceHolder holder) {
		Canvas canvas = sfh.lockCanvas();
		GameData.scaleWidth = (float) canvas.getWidth()
				/ (float) GameData.width;
		GameData.scaleHeight = (float) canvas.getHeight()
				/ (float) GameData.height;
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
			int x = (int) ((int) event.getX() / GameData.scaleWidth);
			int y = (int) ((int) event.getY() / GameData.scaleHeight);
			game = PanelFactory.getInstance(GameData.status);
			game.clickAction(x, y);
			disableTouch();
		}
		return true;
	}

	/**
	 * 游戏初始化，包括各种图片资源的加载，状态值的初始化
	 */
	private void inilization() {
		ImageFactory.loadImageFromAssets("image/background/background.png",
				"background");
		ImageFactory.loadImageFromAssets("image/logo/logo.png", "logo");
		ImageFactory.loadImageFromAssets("image/button/start.png", "start");
		ImageFactory.loadImageFromAssets("image/button/help.png", "help");
		ImageFactory.loadImageFromAssets("image/button/exit.png", "exit");
		ImageFactory.loadImageFromAssets("image/button/return.png", "return");
		ImageFactory.loadImageFromAssets("image/button/gamestart.png",
				"game_start");
		ImageFactory.loadImageFromAssets("image/button/gamerestart.png",
				"game_restart");
		ImageFactory.loadImageFromAssets("image/button/gamehelp.png",
				"game_help");
		ImageFactory.loadImageFromAssets("image/button/gameback.png",
				"game_back");
		ImageFactory.loadImageFromAssets("image/func/help.png", "help_text");
		ImageFactory.loadImageFromAssets("image/func/win.png", "win_text");
		ImageFactory.loadImageFromAssets("image/func/loss.png", "loss_text");
		ImageFactory.loadImageFromAssets("image/func/chessboard.jpg",
				"chessboard");
		ImageFactory.loadImageFromAssets("image/func/white.png", "white");
		ImageFactory.loadImageFromAssets("image/func/black.png", "black");
		ImageFactory.loadImageFromAssets("image/func/choose.png", "choose");
		GameData.flag = true;
		GameData.debugModel = false;
		GameData.fps = 1000 / 60;
		GameData.moveSpeedIn = 12;
		GameData.moveSpeedOut = 20;
		clickaviliable = true;
	}

	private void myDraw() {
		Canvas canvas = sfh.lockCanvas();
		GameData.canvas = canvas;
		GameData.paint = paint;
		if (canvas == null)
			return;
		canvas.drawColor(Color.BLACK);
		game = PanelFactory.getInstance(GameData.status);
		game.drawSelf();
		sfh.unlockCanvasAndPost(canvas);
	}

	private void myLogic() {
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
		}

	}

	public void backPress() {
		if (clickaviliable) {
			game = PanelFactory.getInstance(GameData.status);
			game.clickAction(560, 420);
			disableTouch();
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
