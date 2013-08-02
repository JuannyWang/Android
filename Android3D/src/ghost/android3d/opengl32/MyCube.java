/**
 * 
 */
package ghost.android3d.opengl32;

import javax.microedition.khronos.opengles.GL10;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-1-30
 */
public class MyCube {
	public static final float UNIT_SIZE = 1.0f;
	MyTextureRect trSmall;// 小面纹理矩形
	MyTextureRect trBig;// 大面纹理矩形
	float scale;// 尺寸缩放系数
	float unitLocalSize;// 实际单位尺寸——小面的半边长
	float xOffset = 0;// x位置
	float zOffset = 0;// Z位置

	public MyCube(float scale, int cubeSmallTexId, int cubeBigTexId) {
		this.scale = scale;
		// 创建大小面纹理矩形对象
		trSmall = new MyTextureRect(scale, scale, cubeSmallTexId);
		trBig = new MyTextureRect(scale, 2 * scale, cubeBigTexId);
		// 小面的半边长
		unitLocalSize = UNIT_SIZE * scale;
	}

	public void drawSelf(GL10 gl) {
		gl.glPushMatrix();
		// 移动到指定的XZ位置绘制Cube
		gl.glTranslatef(xOffset, 0, zOffset);

		// 绘制前小面
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 2 * UNIT_SIZE * scale);
		trSmall.drawSelf(gl);
		gl.glPopMatrix();

		// 绘制后小面
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, -2 * UNIT_SIZE * scale);
		gl.glRotatef(180, 0, 1, 0);
		trSmall.drawSelf(gl);
		gl.glPopMatrix();

		// 绘制上大面
		gl.glPushMatrix();
		gl.glTranslatef(0, UNIT_SIZE * scale, 0);
		gl.glRotatef(-90, 1, 0, 0);
		trBig.drawSelf(gl);
		gl.glPopMatrix();

		// 绘制下大面
		gl.glPushMatrix();
		gl.glTranslatef(0, -UNIT_SIZE * scale, 0);
		gl.glRotatef(90, 1, 0, 0);
		trBig.drawSelf(gl);
		gl.glPopMatrix();

		// 绘制左大面
		gl.glPushMatrix();
		gl.glTranslatef(UNIT_SIZE * scale, 0, 0);
		gl.glRotatef(-90, 1, 0, 0);
		gl.glRotatef(90, 0, 1, 0);
		trBig.drawSelf(gl);
		gl.glPopMatrix();

		// 绘制右大面
		gl.glPushMatrix();
		gl.glTranslatef(-UNIT_SIZE * scale, 0, 0);
		gl.glRotatef(90, 1, 0, 0);
		gl.glRotatef(-90, 0, 1, 0);
		trBig.drawSelf(gl);
		gl.glPopMatrix();

		gl.glPopMatrix();
	}
}
