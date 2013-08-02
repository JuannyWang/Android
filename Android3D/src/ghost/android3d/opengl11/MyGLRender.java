/**
 * 
 */
package ghost.android3d.opengl11;

import ghost.android3d.R;

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
	MyTextureTriangle texTri;
	private int textureId;
	private Context context;

	MyGLRender(Context context) {
		this.context = context;
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		// 清除颜色缓存
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// 设置当前矩阵为模式矩阵
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		// 设置当前矩阵为单位矩阵
		gl.glLoadIdentity();
		gl.glTranslatef(0, 0f, -2.5f);
		texTri.drawSelf(gl);
	}

	@Override
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
		gl.glFrustumf(-ratio, ratio, -1, 1, 1, 20);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		// 关闭抗抖动
		gl.glDisable(GL10.GL_DITHER);
		// 设置特定Hint项目的模式，这里为设置为使用快速模式
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
		// 设置屏幕背景色黑色RGBA
		gl.glClearColor(0, 0, 0, 0);
		// 打开背面剪裁
		// gl.glEnable(GL10.GL_CULL_FACE);
		// 设置着色模型为平滑着色
		gl.glShadeModel(GL10.GL_SMOOTH);// GL10.GL_SMOOTH GL10.GL_FLAT
		// 启用深度测试
		gl.glEnable(GL10.GL_DEPTH_TEST);
		// 初始化纹理
		textureId = initTexture(gl, R.drawable.duke);
		texTri = new MyTextureTriangle(textureId);
	}

	public int initTexture(GL10 gl, int textureId)// textureId
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
