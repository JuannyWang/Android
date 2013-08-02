/**
 * 
 */
package com.ghost.fun;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-7-30
 */
public class MySurfaceView extends SurfaceView implements
		SurfaceHolder.Callback {

	private SurfaceHolder sfh;

	private Paint paint;

	private boolean flag;

	private List<MyParticle> particles;

	private int mouseX, mouseY;

	private boolean mouseDown;

	private int width;

	private int height;

	private final int fps = 60;

	private final int changeStep = 10 * fps;

	private int step;

	private int maxSize;

	private AsyncTask<Void, Integer, Void> updateTask;

	public MySurfaceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public MySurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MySurfaceView(Context context) {
		super(context);
		init();
	}

	private void init() {
		DisplayMetrics dm = getResources().getDisplayMetrics();
		int widthPixels = dm.widthPixels;
		mouseX = widthPixels/2;
		int heightPixels = dm.heightPixels;
		mouseY = heightPixels/2;
		float density = dm.density;
		int ppi = (int) (Math.sqrt(widthPixels * widthPixels + heightPixels
				* heightPixels) / density);
		MyParticle.RADIUS = Math.min(widthPixels, heightPixels) / 4;
		maxSize = ppi / 25;
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setColor(Color.GREEN);
		paint.setAntiAlias(true);
		particles = new ArrayList<MyParticle>();
		for (int i = 0; i < MyParticle.NUMS; ++i) {
			particles.add(new MyParticle(mouseX, mouseY));
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		updateTask = new UpdateTask();
		flag = true;
		updateTask.execute();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		updateTask.cancel(true);
		flag = false;
	}

	private class UpdateTask extends AsyncTask<Void, Integer, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			while (flag) {
				long start = System.currentTimeMillis();

				Canvas canvas = sfh.lockCanvas();

				if (mouseDown) {
					MyParticle.RADIUS_SCALE += (MyParticle.RADIUS_SCALE_MAX - MyParticle.RADIUS_SCALE) * (0.02);
				} else {
					MyParticle.RADIUS_SCALE -= (MyParticle.RADIUS_SCALE - MyParticle.RADIUS_SCALE_MIN) * (0.02);
				}

				MyParticle.RADIUS_SCALE = Math.min(MyParticle.RADIUS_SCALE,
						MyParticle.RADIUS_SCALE_MAX);

				int backColor = Color.argb(10, 0, 0, 0);

				canvas.drawColor(backColor);

				for (int i = 0; i < particles.size(); ++i) {

					MyParticle particle = particles.get(i);

					PointF lp = particle.position;

					particle.angle += particle.speed;

					particle.shift.x += (mouseX - particle.shift.x)
							* (particle.speed);
					particle.shift.y += (mouseY - particle.shift.y)
							* (particle.speed);

					particle.position.x = (float) (particle.shift.x + Math
							.cos(i + particle.angle)
							* (particle.orbit * MyParticle.RADIUS_SCALE));
					particle.position.y = (float) (particle.shift.y + Math
							.sin(i + particle.angle)
							* (particle.orbit * MyParticle.RADIUS_SCALE));

					particle.position.x = particle.position.x;
					particle.position.y = particle.position.y;

					particle.size += (particle.targetSize - particle.size) * 0.05;

					if (Math.round(particle.size) == Math
							.round(particle.targetSize)) {
						particle.targetSize = 1 + Math.random() * maxSize;
					}

					paint.setColor(particle.color);

					paint.setStrokeWidth((float) particle.size);

					canvas.drawLine(lp.x, lp.y, particle.position.x,
							particle.position.y, paint);

					canvas.drawCircle((float) particle.position.x
							- (float) particle.size / 2,
							(float) particle.position.y - (float) particle.size
									/ 2, (float) particle.size / 2, paint);
				}

				sfh.unlockCanvasAndPost(canvas);

				step++;

				if (step == changeStep) {
					mouseX = (int) ((int) (Math.random() * (width - MyParticle.RADIUS)) + MyParticle.RADIUS);
					mouseY = (int) ((int) (Math.random() * (height - MyParticle.RADIUS)) + MyParticle.RADIUS);
					step = 0;
				} else if (step == changeStep / 2) {
					mouseDown = !mouseDown;
				}

				long end = System.currentTimeMillis();
				if (end - start < (1000 / fps)) {
					try {
						Thread.sleep((1000 / fps) - (end - start));
					} catch (InterruptedException e) {
					}
				}

			}
			return null;
		}

	}

}
