/**
 * 
 */
package com.ltgame.engine.util;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.opengl.GLUtils;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-4-27
 */
public class LTUtil {

	public static int loadTexture(Bitmap bitmap, GL10 gl) {
		int textureIds[] = new int[1];
		gl.glGenTextures(1, textureIds, 0);
		int textureId = textureIds[0];
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_NEAREST);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
		return textureId;
	}

}
