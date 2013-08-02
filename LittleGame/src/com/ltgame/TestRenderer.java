/**
 * 
 */
package com.ltgame;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.ltgame.engine.util.LTUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView.Renderer;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-4-27
 */
public class TestRenderer implements Renderer {

	private Context context;

	FloatBuffer vertices;
	FloatBuffer texture;
	ShortBuffer indices;
	int textureId;

	public TestRenderer(Context context) {
		this.context = context;
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * 2 * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		vertices = byteBuffer.asFloatBuffer();
		vertices.put(new float[] { -80f, -120f, 80f, -120f, -80f, 120f, 80f,
				120f });

		ByteBuffer indicesBuffer = ByteBuffer.allocateDirect(6 * 2);
		indicesBuffer.order(ByteOrder.nativeOrder());
		indices = indicesBuffer.asShortBuffer();
		indices.put(new short[] { 0, 1, 2, 1, 2, 3 });

		ByteBuffer textureBuffer = ByteBuffer.allocateDirect(4 * 2 * 4);
		textureBuffer.order(ByteOrder.nativeOrder());
		texture = textureBuffer.asFloatBuffer();
		texture.put(new float[] { 0, 1f, 1f, 1f, 0f, 0f, 1f, 0f });

		indices.position(0);
		vertices.position(0);
		texture.position(0);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.test);
		textureId = LTUtil.loadTexture(bitmap, gl);
		bitmap.recycle();
		
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glLoadIdentity();

		gl.glEnable(GL10.GL_TEXTURE_2D);
		// 绑定纹理ID
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertices);

		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texture);
		// gl.glRotatef(1, 0, 1, 0);
		gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 6, GL10.GL_UNSIGNED_SHORT,
				indices);

	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		/**
		 * 将窗口匹配到视图
		 */
		gl.glViewport(0, 0, width, height);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		/**
		 * 建立一个480*800的绘制空间
		 */
		gl.glOrthof(0, 480, 0, 800, 1, -1);
		/**
		 * 设置投影矩阵
		 */
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glClearColor(0f, 0f, 0f, 1f);

		
	}

}
