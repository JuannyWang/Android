/**
 * 
 */
package ghost.android3d.opengl04;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-1-25
 */
public class MyGLRender implements Renderer {
	MyHexagon[] ha = new MyHexagon[] { // 六边形数组
	new MyHexagon(0),//
			new MyHexagon(-2),//
			new MyHexagon(-4),//
			new MyHexagon(-6),//
			new MyHexagon(-8),//
			new MyHexagon(-10),//
			new MyHexagon(-12), };
	boolean isPerspective;
	float xAngle;

	public MyGLRender() {
	} // 渲染器构造类

	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glMatrixMode(GL10.GL_PROJECTION); // 设置当前矩阵为投影矩阵
		gl.glLoadIdentity(); // 设置当前矩阵为单位矩阵
		float ratio = (float) 320 / 480; // 计算透视投影的比例
		if (isPerspective) {
			gl.glFrustumf(-ratio, ratio, -1, 1, 1f, 10);// 调用此方法计算产生透视投影矩阵
		} else {
			gl.glOrthof(-ratio, ratio, -1, 1, 1, 10);// 调用此方法计算产生正交投影矩阵
		}
		gl.glEnable(GL10.GL_CULL_FACE); // 设置为打开背面剪裁
		gl.glShadeModel(GL10.GL_SMOOTH); // 设置着色模型为平滑着色
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);// 清除缓存
		gl.glMatrixMode(GL10.GL_MODELVIEW); // 设置当前矩阵为模式矩阵
		gl.glLoadIdentity(); // 设置当前矩阵为单位矩阵

		gl.glTranslatef(0, 0f, -1.4f); // 沿z轴向远处推
		gl.glRotatef(xAngle, 1, 0, 0); // 绕x轴旋转制定角度

		for (MyHexagon th : ha) {
			th.drawSelf(gl); // 循环绘制六边形数组中的每个六边形
		}
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height); // 设置视窗大小及位置
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glDisable(GL10.GL_DITHER); // 关闭抗抖动
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);// 设置Hint模式
		gl.glClearColor(0, 0, 0, 0); // 设置屏幕背景色黑色
		gl.glEnable(GL10.GL_DEPTH_TEST); // 启用深度测试
	}
}
