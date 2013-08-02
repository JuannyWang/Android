/**
 * 
 */
package ghost.android3d.opengl03;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-1-25
 */
public class MyGLRender implements Renderer {

	MyTriangles triangles = new MyTriangles();
	public boolean backFlag;
	public boolean smoothFlag;
	public boolean selfCulling;

	public MyGLRender() {
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		if (backFlag) {
			gl.glEnable(GL10.GL_CULL_FACE); // 设置为打开背面剪裁
		} else {
			gl.glDisable(GL10.GL_CULL_FACE); // 设置为关闭背面剪裁
		}

		if (smoothFlag) {
			gl.glShadeModel(GL10.GL_SMOOTH); // 设置着色模型为平滑着色
		} else {
			gl.glShadeModel(GL10.GL_FLAT); // 设置着色模型为不平滑着色
		}

		if (selfCulling) {
			gl.glFrontFace(GL10.GL_CW); // 设置自定义卷绕顺序为顺时针为正面
		} else {
			gl.glFrontFace(GL10.GL_CCW); // 设置自定义卷绕顺序为逆时针为正面
		}
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);// 清除缓存
		gl.glMatrixMode(GL10.GL_MODELVIEW); // 设置当前矩阵为模式矩阵
		gl.glLoadIdentity(); // 设置当前矩阵为单位矩阵
		gl.glTranslatef(0, 0, -2.0f); //
		triangles.drawSelf(gl); //
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		gl.glViewport(0, 0, width, height); //
		gl.glMatrixMode(GL10.GL_PROJECTION); //
		gl.glLoadIdentity(); //
		float ratio = (float) width / height; //
		gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10); //
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		gl.glDisable(GL10.GL_DITHER); // 关闭抗抖动
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);// 设置特定Hint项目的模式
		gl.glClearColor(0, 0, 0, 0); // 设置屏幕背景色为黑色
		gl.glEnable(GL10.GL_DEPTH_TEST); // 启用深度检测
	}

}
