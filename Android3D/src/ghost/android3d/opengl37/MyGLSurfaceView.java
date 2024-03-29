/**
 * 
 */
package ghost.android3d.opengl37;

import ghost.android3d.R;
import ghost.android3d.opengl33.MyCelestial;
import ghost.android3d.opengl33.MyDesert;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.view.MotionEvent;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-1-25
 */
class MyGLSurfaceView extends GLSurfaceView implements Contants {

	static float direction = DIRECTION_INI;// 视线方向
	static float cx = CAMERA_INI_X;// 摄像机x坐标
	static float cy = CAMERA_INI_Y;// 摄像机y坐标
	static float cz = CAMERA_INI_Z;// 摄像机z坐标

	static float tx = (float) (cx - Math.sin(direction) * DISTANCE);// 观察目标点x坐标
	static float ty = CAMERA_INI_Y - 0.4f;// 观察目标点y坐标
	static float tz = (float) (cz - Math.cos(direction) * DISTANCE);// 观察目标点z坐标

	private float mPreviousY;// 上次的触控位置Y坐标
	private float mPreviousX;// 上次的触控位置X坐标

	float ratio;// 投影的比例
	private SceneRenderer mRenderer;

	public MyGLSurfaceView(Context context) {
		super(context);
		mRenderer = new SceneRenderer(); // 创建场景渲染器
		setRenderer(mRenderer); // 设置渲染器
		setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);// 设置渲染模式为主动渲染
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		float y = e.getY();
		float x = e.getX();
		switch (e.getAction()) {
		case MotionEvent.ACTION_MOVE:
			float dx = y - mPreviousY;// 计算触控笔Y位移
			float dy = x - mPreviousX;// 计算触控笔Y位移
			float xOffset = 0;// 此步的X位移
			float zOffset = 0;// 此步的Z位移
			if (Math.abs(dx) > 10) {
				if (dx > 0) {
					xOffset = (float) -Math.sin(direction) * MOVE_SPAN;
					zOffset = (float) -Math.cos(direction) * MOVE_SPAN;
				} else {
					xOffset = (float) Math.sin(direction) * MOVE_SPAN;
					zOffset = (float) Math.cos(direction) * MOVE_SPAN;
				}
				cx = cx + xOffset;
				cz = cz + zOffset;
			}
			if (Math.abs(dy) > 10) {
				if (dy > 0) {
					direction = direction + DEGREE_SPAN;
				} else {
					direction = direction - DEGREE_SPAN;
				}
				// 设置新的观察目标点XZ坐标
				tx = (float) (cx - Math.sin(direction) * DISTANCE);// 观察目标点x坐标
				tz = (float) (cz - Math.cos(direction) * DISTANCE);// 观察目标点z坐标
			}

			// 计算所有仙人掌的朝向
			mRenderer.cg.calculateBillboardDirection();

			// 给仙人掌按照离视点的距离排序
			Collections.sort(mRenderer.cg.al);
			requestRender();// 重绘画面
		}
		mPreviousY = y;// 记录触控笔位置
		mPreviousX = x;// 记录触控笔位置
		return true;
	}

	private class SceneRenderer implements GLSurfaceView.Renderer {
		int desertTextureId;// 沙漠纹理
		int cactusTextureId;// 仙人掌纹理

		MyDesert desert;// 沙漠
		MyCactusGroup cg;// 仙人掌组
		MyCelestial celestialSmall;// 小星星星空半球
		MyCelestial celestialBig;// 大星星星空半球

		public void onDrawFrame(GL10 gl) {
			// 采用平滑着色
			gl.glShadeModel(GL10.GL_SMOOTH);
			// 清除颜色缓存于深度缓存
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
			// 设置当前矩阵为模式矩阵
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			// 设置当前矩阵为单位矩阵
			gl.glLoadIdentity();
			// 设置camera位置
			GLU.gluLookAt(gl, cx, // 人眼位置的X
					cy, // 人眼位置的Y
					cz, // 人眼位置的Z
					tx, // 人眼球看的点X
					ty, // 人眼球看的点Y
					tz, // 人眼球看的点Z
					0, 1, 0);
			// 绘制沙漠
			desert.drawSelf(gl);
			// // 绘制星空
			// celestialSmall.drawSelf(gl);
			// celestialBig.drawSelf(gl);
			// 绘制仙人掌
			cg.drawSelf(gl);
		}

		public void onSurfaceChanged(GL10 gl, int width, int height) {
			// 设置视窗大小及位置
			gl.glViewport(0, 0, width, height);
			// 计算投影的比例
			ratio = (float) width / height;
			// 设置当前矩阵为投影矩阵
			gl.glMatrixMode(GL10.GL_PROJECTION);
			// 设置当前矩阵为单位矩阵
			gl.glLoadIdentity();
			// 调用此方法计算产生透视投影矩阵
			gl.glFrustumf(-ratio * 0.6f, ratio * 0.6f, -1 * 0.6f, 1 * 0.6f, 1,
					100);
		}

		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			// 关闭抗抖动
			gl.glDisable(GL10.GL_DITHER);
			// 设置特定Hint项目的模式，这里为设置为使用快速模式
			gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
			// 设置屏幕背景色黑色RGBA
			gl.glClearColor(0, 0, 0, 0);
			// 启用深度测试
			gl.glEnable(GL10.GL_DEPTH_TEST);
			// 开启混合
			gl.glEnable(GL10.GL_BLEND);
			// 设置混合参数
			gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			// 设置为打开背面剪裁
			gl.glEnable(GL10.GL_CULL_FACE);

			// 初始化纹理
			desertTextureId = initTexture(gl, R.drawable.desert);
			cactusTextureId = initTexture(gl, R.drawable.cactus);

			// 初始化沙漠
			desert = new MyDesert(0, 0, 1.0f, 0, desertTextureId, DESERT_COLS,
					DESERT_ROWS);
			// 初始化仙人掌集合
			cg = new MyCactusGroup(cactusTextureId);
			// 创建星空
			celestialSmall = new MyCelestial(UNIT_SIZE * DESERT_COLS / 2,
					UNIT_SIZE * DESERT_ROWS / 2, 1, 0, 350);
			celestialBig = new MyCelestial(UNIT_SIZE * DESERT_COLS / 2,
					UNIT_SIZE * DESERT_ROWS / 2, 2, 0, 150);

			new Thread() {// 定时转动星空的线程
				public void run() {
					while (true) {
						celestialSmall.yAngle += 0.5;
						if (celestialSmall.yAngle >= 360) {
							celestialSmall.yAngle = 0;
						}
						celestialBig.yAngle += 0.5;
						if (celestialBig.yAngle >= 360) {
							celestialBig.yAngle = 0;
						}
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}.start();
		}
	}

	// 初始化纹理
	public int initTexture(GL10 gl, int drawableId)// textureId
	{
		// 生成纹理ID
		int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0); // 提供 未使用的纹理对象名称
		int currTextureId = textures[0];
		gl.glBindTexture(GL10.GL_TEXTURE_2D, currTextureId);// 创建和使用纹理对象
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_NEAREST);// 指定放大缩小过滤方法
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
				GL10.GL_CLAMP_TO_EDGE);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
				GL10.GL_CLAMP_TO_EDGE);

		InputStream is = this.getResources().openRawResource(drawableId);
		Bitmap bitmapTmp;
		try {
			bitmapTmp = BitmapFactory.decodeStream(is);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmapTmp, 0);
		bitmapTmp.recycle();

		return currTextureId;
	}
}
