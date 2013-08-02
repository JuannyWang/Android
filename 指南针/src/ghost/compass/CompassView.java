/**
 * 
 */
package ghost.compass;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-2-3
 */
public class CompassView extends SurfaceView implements Callback {

	/**
	 * 绘制句柄
	 */
	private SurfaceHolder sfh;
	private Paint paint;

	/**
	 * 转动度数
	 */
	float degree;

	/**
	 * 界面大小
	 */
	private int width, height;

	/**
	 * 自动适应屏幕缩放比例
	 */
	private float scaleW, scaleH;

	/**
	 * activity状态值
	 */
	private boolean flag;

	/**
	 * 指针图像
	 */
	private Bitmap pointer;
	/**
	 * 背景图片
	 */
	private Bitmap background;

	/**
	 * 界面刷星线程
	 */
	private Thread update_thread;

	/**
	 * 上下文句柄
	 */
	private Context context;

	/**
	 * @param context
	 */
	public CompassView(Context context) {
		super(context);
		this.context = context;
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setColor(Color.GREEN);
		paint.setAntiAlias(true);
	}

	private void myDraw() {
		Canvas canvas = sfh.lockCanvas();
		canvas.drawColor(Color.BLACK);
		if (background != null) {
			canvas.drawBitmap(background, (int) (0f * scaleW),
					(int) (0f * scaleH), paint);
		}
		canvas.save();
		canvas.rotate(degree, width / 2, height / 2);
		if (pointer != null) {
			canvas.drawBitmap(pointer, (int) (230f * scaleW),
					(int) (300f * scaleH), paint);
		} else {
			canvas.drawRect((230f * scaleW),
					(300f * scaleH),
					(230f * scaleW)
							+ (20f * scaleW),
					(300f * scaleH)
							+ (200f * scaleH),
					paint);
		}
		canvas.restore();
		sfh.unlockCanvasAndPost(canvas);
	}

	private class UpdateSurface implements Runnable {

		public void run() {
			while (flag) {
				long start = System.currentTimeMillis();
				myDraw();
				long end = System.currentTimeMillis();
				if (end - start < 30) {
					try {
						Thread.sleep(30 - (end - start));
					} catch (InterruptedException e) {
					}
				}
			}
		}

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		this.width = width;
		scaleW = (float) width / 480f;
		this.height = height;
		scaleH = (float) height / 800f;
		pointer = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
				context.getResources(), R.drawable.pointer),
				(int) (20 * scaleW), (int) (200 * scaleH), true);
		background = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
				context.getResources(), R.drawable.background),
				(int) (480 * scaleW), (int) (800 * scaleH), true);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		flag = true;
		update_thread = new Thread(new UpdateSurface());
		update_thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		flag = false;
		update_thread = null;
	}

}
