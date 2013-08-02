/**
 * 
 */
package ghost.android3d.opengl02;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-1-25
 */
public class MyPoints {
	private IntBuffer myVertexBuffer;
	private IntBuffer myColorBuffer;
	private ByteBuffer myIndexBuffer;
	private int vCount = 0;
	private int iCount = 0;
	public float yAngle = 0;
	public float zAngle = 0;
	private final int UNIT_SIZE = 10000;
	private final int one = 65535;

	public MyPoints() {
		vCount = 4;
		int vertices[] = new int[] { -2 * UNIT_SIZE, 3 * UNIT_SIZE, 0,//
				1 * UNIT_SIZE, 1 * UNIT_SIZE, 0,//
				-1 * UNIT_SIZE, -2 * UNIT_SIZE, 0,//
				2 * UNIT_SIZE, -3 * UNIT_SIZE, 0 };
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		myVertexBuffer = vbb.asIntBuffer();
		myVertexBuffer.put(vertices);
		myVertexBuffer.position(0);
		int colors[] = new int[] { one, one, one, 0,//
				one, one, one, 0,//
				one, one, one, 0,//
				one, one, one, 0 };
		ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
		cbb.order(ByteOrder.nativeOrder());
		myColorBuffer = cbb.asIntBuffer();
		myColorBuffer.put(colors);
		myColorBuffer.position(0);
		iCount = 4;
		byte[] indice = new byte[] { 0, 3, 2, 1 };
		myIndexBuffer = ByteBuffer.allocateDirect(indice.length);
		myIndexBuffer.put(indice);
		myIndexBuffer.position(0);
	}

	public void drawSelf(GL10 gl) {
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		gl.glRotatef(yAngle, 0, 1, 0);
		gl.glRotatef(zAngle, 0, 0, 1);
		gl.glVertexPointer(3, GL10.GL_FIXED, 0, myVertexBuffer);
		gl.glColorPointer(4, GL10.GL_FIXED, 0, myColorBuffer);
		gl.glDrawElements(GL10.GL_POINTS, iCount, GL10.GL_UNSIGNED_BYTE,
				myIndexBuffer);
	}
}
