/**
 * 
 */
package ghost.android3d.opengl31;

import ghost.android3d.R;
import ghost.android3d.opengl13.MyCelestial;
import ghost.android3d.opengl13.MyFullTextureBall;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLUtils;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-1-26
 */
public class MyGLRender implements Renderer {
	private Context context;
	MyFullTextureBall earth;
	MyFullTextureBall moon;
	MyCelestial celestialSmall;// 小星星星空半球
	MyCelestial celestialBig;// 大星星星空半球
	private int earthTextureId;// 纹理名称ID
	private int moonTextureId;// 纹理名称ID
	private float TOUCH_SCALE_FACTOR = 180.0f / 320;// 角度缩放比例
	private float rotateA;

	MyGLRender(Context context) {
		this.context = context;
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		// 清除颜色缓存
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// 设置当前矩阵为模式矩阵
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		// 设置当前矩阵为单位矩阵
		gl.glLoadIdentity();

		gl.glTranslatef(0, 0f, -3.6f);

		gl.glEnable(GL10.GL_LIGHTING);// 允许光照
		gl.glRotatef(rotateA, 0, 1f, 0);
		gl.glPushMatrix();// 保护变换矩阵现场
		gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 3.5f);
		earth.drawSelf(gl);// 绘制地球
		gl.glPopMatrix();
		
		gl.glPushMatrix();// 保护变换矩阵现场
		gl.glTranslatef(0, 0f, 1.5f);
		gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 1.0f);
		moon.drawSelf(gl);// 绘制月球
		gl.glPopMatrix();// 恢复变换矩阵现场
		gl.glDisable(GL10.GL_LIGHTING);// 不允许光照

		// 绘制星空
		gl.glPushMatrix();// 保护变换矩阵现场
		gl.glTranslatef(0, -8.0f, 0.0f);
		celestialSmall.drawSelf(gl);
		celestialBig.drawSelf(gl);
		gl.glPopMatrix();// 恢复变换矩阵现场
		
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// 设置视窗大小及位置
		gl.glViewport(0, 0, width, height);
		// 设置当前矩阵为投影矩阵
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// 设置当前矩阵为单位矩阵
		gl.glLoadIdentity();
		// 计算透视投影的比例
		float ratio = (float) width / height;
		// 调用此方法计算产生透视投影矩阵
		gl.glFrustumf(-ratio * 0.5f, ratio * 0.5f, -0.5f, 0.5f, 1, 100);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// 关闭抗抖动
		gl.glDisable(GL10.GL_DITHER);
		// 设置特定Hint项目的模式，这里为设置为使用快速模式
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
		// 设置屏幕背景色黑色RGBA
		gl.glClearColor(0, 0, 0, 0);
		// 设置着色模型为平滑着色
		gl.glShadeModel(GL10.GL_SMOOTH);
		// 启用深度测试
		gl.glEnable(GL10.GL_DEPTH_TEST);
		// 设置为打开背面剪裁
		gl.glEnable(GL10.GL_CULL_FACE);

		gl.glEnable(GL10.GL_LIGHTING);// 允许光照
		initSunLight(gl);// 初始化太阳光源
		initMaterial(gl);// 初始化材质

		// 初始化纹理
		earthTextureId = initTexture(gl, R.drawable.earth);
		moonTextureId = initTexture(gl, R.drawable.moon);

		earth = new MyFullTextureBall(6, earthTextureId);
		moon = new MyFullTextureBall(2, moonTextureId);

		// 创建星空
		celestialSmall = new MyCelestial(0, 0, 1, 0, 750);
		celestialBig = new MyCelestial(0, 0, 3, 0, 200);

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(1000);// 休息1000ms再开始绘制
				} catch (Exception e) {
					e.printStackTrace();
				}
				while (true) {
					celestialSmall.yAngle -= 0.15;
					if (celestialSmall.yAngle <= 0) {
						celestialSmall.yAngle = 360;
					}
					celestialBig.yAngle -= 0.25;
					if (celestialBig.yAngle <= 0) {
						celestialBig.yAngle = 360;
					}
					rotateA += TOUCH_SCALE_FACTOR;
					if (rotateA >= 360) {
						rotateA = 0;
					}
					earth.mAngleY += 2 * TOUCH_SCALE_FACTOR;// 球沿Y轴转动
					moon.mAngleY += 4 * TOUCH_SCALE_FACTOR;// 球沿Y轴转动
					try {
						Thread.sleep(50);// 休息10ms再重绘
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}).start();
	}

	// 初始化太阳光源
	private void initSunLight(GL10 gl) {
		gl.glEnable(GL10.GL_LIGHT0);// 打开0号灯

		// 环境光设置
		float[] ambientParams = { 0.05f, 0.05f, 0.025f, 1.0f };// 光参数 RGBA
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, ambientParams, 0);

		// 散射光设置
		float[] diffuseParams = { 1f, 1f, 0.5f, 1.0f };// 光参数 RGBA
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffuseParams, 0);

		// 反射光设置
		float[] specularParams = { 1f, 1f, 0.5f, 1.0f };// 光参数 RGBA
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, specularParams, 0);

		// 设定光源的位置
		float[] positionParamsGreen = { -20.14f, 10.28f, 6f, 0 };// 最后的0表示使用定向光
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, positionParamsGreen, 0);

	}

	// 初始化材质
	private void initMaterial(GL10 gl) {// 材质为白色时什么颜色的光照在上面就将体现出什么颜色
										// 环境光为白色材质
		float ambientMaterial[] = { 1.0f, 1.0f, 1.0f, 1.0f };
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT,
				ambientMaterial, 0);
		// 散射光为白色材质
		float diffuseMaterial[] = { 1.0f, 1.0f, 1.0f, 1.0f };
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE,
				diffuseMaterial, 0);
		// 高光材质为白色
		float specularMaterial[] = { 1f, 1f, 1f, 1.0f };
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR,
				specularMaterial, 0);
		gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 100.0f);

	}

	private int initTexture(GL10 gl, int textureId)// textureId
	{
		int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);
		int currTextureId = textures[0];
		gl.glBindTexture(GL10.GL_TEXTURE_2D, currTextureId);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
				GL10.GL_REPEAT);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
				GL10.GL_REPEAT);

		InputStream is = context.getResources().openRawResource(textureId);
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
