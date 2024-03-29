/**
 * 
 */
package ghost.android3d.opengl10;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-1-26
 */
public class MyGLRender implements Renderer {
	MyIndexCube cubeVertex;// 声明顶点法立方体
	public float lightAngleGreen = 0;// 绿灯的旋转度数
	public float lightAngleRed = 0;// 红灯的旋转度数
	float cx = 0;// 摄像机x位置
	float cy = 3;// 摄像机y位置
	float cz = 40;// 摄像机z位置

	float tx = 0;// //目标点x位置
	float ty = 0;// 目标点y位置
	float tz = 0;// 目标点z位置

	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		// 采用平滑着色
		gl.glShadeModel(GL10.GL_SMOOTH);
		// 设置为打开背面剪裁
		gl.glEnable(GL10.GL_CULL_FACE);
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

		gl.glPushMatrix();// 获取坐标系
		gl.glRotatef(45, 0, 1, 0);// 绕Y轴旋转45度
		gl.glRotatef(45, 1, 0, 0);// 绕X轴旋转45度
		cubeVertex.drawSelf(gl);// 绘制立方体
		gl.glPopMatrix();// 恢复坐标系
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		// 设置视窗大小及位置
		gl.glViewport(0, 0, width, height);
		// 设置当前矩阵为投影矩阵
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// 设置当前矩阵为单位矩阵
		gl.glLoadIdentity();
		// 计算透视投影的比例
		float ratio = (float) width / height;
		// 调用此方法计算产生透视投影矩阵
		gl.glFrustumf(-ratio, ratio, -1.0f, 1.0f, 8, 100);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
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
		// 允许使用光照
		gl.glEnable(GL10.GL_LIGHTING);
		// 初始化光源
		initLight(gl);
		// 初始化材质光源
		initMaterial(gl);

		cubeVertex = new MyIndexCube(2.5f, 2.5f, 2.5f);

		new Thread() {
			public void run() {
				while (true) {
					try {
						sleep(100);// 睡眠0.1秒
					} catch (Exception e) {
						e.printStackTrace();
					}
					cubeVertex.mOffsetY += 2.0f;// 每次旋转
				}

			}
		}.start();
	}

	public void initLight(GL10 gl) {
		// TODO Auto-generated method stub
		gl.glEnable(GL10.GL_LIGHT0);// 打开0号光源

		// 环境光设置
		float[] ambientParams = { 0.46f, 0.21f, 0.05f, 1.0f };// 光参数 RGBA
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, ambientParams, 0);

		// 散射光设置
		float[] diffuseParams = { 0.46f, 0.21f, 0.05f, 1.0f };
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffuseParams, 0);

		// 镜面光设置
		float[] specularParams = { 0.46f, 0.21f, 0.05f, 1.0f };
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, specularParams, 0);

		// 指定光源位置
		float[] directionParams = { -1f, 1f, 1f, 0 };// 定向光
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, directionParams, 0);
	}

	private void initMaterial(GL10 gl) {// 材质为白色时什么颜色的光照在上面就将体现出什么颜色
		// TODO Auto-generated method stub
		// 环境光为白色材质
		float ambientMaterial[] = { 0.6f, 0.6f, 0.6f, 1.0f };
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT,
				ambientMaterial, 0);
		// 散射光为白色材质
		float diffuseMaterial[] = { 1.0f, 1.0f, 1.0f, 1.0f };
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE,
				diffuseMaterial, 0);
		// 高光材质为白色
		float specularMaterial[] = { 1f, 1.0f, 1f, 1.0f };
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR,
				specularMaterial, 0);
	}
}
