/**
 * 
 */
package ghost.android3d.opengl15;

import javax.microedition.khronos.opengles.GL10;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-1-28
 */
public class MyColorCube {
	// 用于绘制各个面的颜色矩形
	MyColorRect cr = new MyColorRect(MyConstant.SCALE, MyConstant.SCALE);

	public void drawSelf(GL10 gl) {
		// 总绘制思想：通过把一个颜色矩形旋转移位到立方体每个面的位置
		// 绘制立方体的每个面

		gl.glPushMatrix();

		// 绘制前小面
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, MyConstant.UNIT_SIZE * MyConstant.SCALE);
		cr.drawSelf(gl);
		gl.glPopMatrix();

		// 绘制后小面
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, -MyConstant.UNIT_SIZE * MyConstant.SCALE);
		gl.glRotatef(180, 0, 1, 0);
		cr.drawSelf(gl);
		gl.glPopMatrix();

		// 绘制上大面
		gl.glPushMatrix();
		gl.glTranslatef(0, MyConstant.UNIT_SIZE * MyConstant.SCALE, 0);
		gl.glRotatef(-90, 1, 0, 0);
		cr.drawSelf(gl);
		gl.glPopMatrix();

		// 绘制下大面
		gl.glPushMatrix();
		gl.glTranslatef(0, -MyConstant.UNIT_SIZE * MyConstant.SCALE, 0);
		gl.glRotatef(90, 1, 0, 0);
		cr.drawSelf(gl);
		gl.glPopMatrix();

		// 绘制左大面
		gl.glPushMatrix();
		gl.glTranslatef(MyConstant.UNIT_SIZE * MyConstant.SCALE, 0, 0);
		gl.glRotatef(-90, 1, 0, 0);
		gl.glRotatef(90, 0, 1, 0);
		cr.drawSelf(gl);
		gl.glPopMatrix();

		// 绘制右大面
		gl.glPushMatrix();
		gl.glTranslatef(-MyConstant.UNIT_SIZE * MyConstant.SCALE, 0, 0);
		gl.glRotatef(90, 1, 0, 0);
		gl.glRotatef(-90, 0, 1, 0);
		cr.drawSelf(gl);
		gl.glPopMatrix();

		gl.glPopMatrix();
	}
}
