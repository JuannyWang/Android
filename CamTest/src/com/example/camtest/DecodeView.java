/**
 * 
 */
package com.example.camtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-7-17
 */
public class DecodeView extends SurfaceView implements SurfaceHolder.Callback {

	private static SurfaceHolder holder;
	private static Paint paint;
	private static byte[] myByteBuffer;

	private boolean flag;
	private boolean decode_flag;

	public DecodeView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public DecodeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public DecodeView(Context context) {
		super(context);
		init();
	}

	/**
	 * 程序统一初始化
	 */
	private void init() {
		holder = getHolder();// 生成Surface Holder
		holder.addCallback(this);
		paint = new Paint();
	}

	public void surfaceCreated(SurfaceHolder holder) {// Surface生成事件的处理
		flag = true;
		new Thread(new UpdateRunable()).start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {// Surface改变事件的处理
	}

	public void surfaceDestroyed(SurfaceHolder holder) {// Surface销毁时的处理
		flag = false;
		holder = null;
	}

	public static void setBytes(byte[] data) {
		myByteBuffer = data;
	}

	public void startDecode() {
		decode_flag = true;
	}

	public void stopDecode() {
		decode_flag = false;
	}

	public static void decodeYUV420SP(int[] rgb, byte[] yuv420sp, int width,
			int height) {
		final int frameSize = width * height;

		for (int j = 0, yp = 0; j < height; j++) {
			int uvp = frameSize + (j >> 1) * width, u = 0, v = 0;
			for (int i = 0; i < width; i++, yp++) {
				int y = (0xff & ((int) yuv420sp[yp])) - 16;
				if (y < 0)
					y = 0;
				if ((i & 1) == 0) {
					v = (0xff & yuv420sp[uvp++]) - 128;
					u = (0xff & yuv420sp[uvp++]) - 128;
				}

				int y1192 = 1192 * y;
				int r = (y1192 + 1634 * v);
				int g = (y1192 - 833 * v - 400 * u);
				int b = (y1192 + 2066 * u);

				if (r < 0)
					r = 0;
				else if (r > 262143)
					r = 262143;
				if (g < 0)
					g = 0;
				else if (g > 262143)
					g = 262143;
				if (b < 0)
					b = 0;
				else if (b > 262143)
					b = 262143;

				rgb[yp] = 0xff000000 | ((r << 6) & 0xff0000)
						| ((g >> 2) & 0xff00) | ((b >> 10) & 0xff);
			}
		}
	}

	public static Bitmap rawByteArray2RGBABitmap2(byte[] data, int width,
			int height) {
		int frameSize = width * height;
		int[] rgba = new int[frameSize];

		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++) {
				int y = (0xff & ((int) data[i * width + j]));
				int u = (0xff & ((int) data[frameSize + (i >> 1) * width
						+ (j & ~1) + 0]));
				int v = (0xff & ((int) data[frameSize + (i >> 1) * width
						+ (j & ~1) + 1]));
				y = y < 16 ? 16 : y;

				int r = Math.round(1.164f * (y - 16) + 1.596f * (v - 128));
				int g = Math.round(1.164f * (y - 16) - 0.813f * (v - 128)
						- 0.391f * (u - 128));
				int b = Math.round(1.164f * (y - 16) + 2.018f * (u - 128));

				r = r < 0 ? 0 : (r > 255 ? 255 : r);
				g = g < 0 ? 0 : (g > 255 ? 255 : g);
				b = b < 0 ? 0 : (b > 255 ? 255 : b);

				rgba[i * width + j] = 0xff000000 + (b << 16) + (g << 8) + r;
			}

		Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
		bmp.setPixels(rgba, 0, width, 0, 0, width, height);
		return bmp;
	}

	private class UpdateRunable implements Runnable {

		@Override
		public void run() {
			while (flag) {
				if (decode_flag) {
					if (myByteBuffer != null) {
						Canvas canvas = holder.lockCanvas();
						Bitmap bm = BitmapFactory.decodeByteArray(myByteBuffer,
								0, myByteBuffer.length);
						if (bm != null) {
							canvas.drawBitmap(
									bm,
									new Rect(0, 0, bm.getWidth(), bm
											.getHeight()),
									new Rect(0, 0, canvas.getWidth(), canvas
											.getHeight()), paint);
						}
						MainActivity.size = myByteBuffer.length;
						holder.unlockCanvasAndPost(canvas);
					} else {
						Canvas canvas = holder.lockCanvas();
						canvas.drawColor(Color.BLUE);
						holder.unlockCanvasAndPost(canvas);
					}
				}
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

}