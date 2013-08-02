/**
 * 
 */
package ghost.android3d.opengl33;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-1-31
 */
public class MyCelestial {
	final float UNIT_SIZE = 6.0f;// 天球半径
	private FloatBuffer mVertexBuffer;// 顶点坐标数据缓冲
	private IntBuffer mColorBuffer;// 顶点着色数据缓冲
	int vCount = 0;// 星星数量
	public float yAngle;// 天球延Y轴旋转的角度
	public int xOffset;// x平移量
	public int zOffset;// z平移量
	float scale;// 星星尺寸

	public MyCelestial(int xOffset, int zOffset, float scale, float yAngle,
			int vCount) {
		this.xOffset = xOffset;
		this.zOffset = zOffset;
		this.yAngle = yAngle;
		this.scale = scale;
		this.vCount = vCount;

		// 顶点坐标数据的初始化================begin=======================================
		float vertices[] = new float[vCount * 3];// 每一个点用XYZ坐标三个数表示
		for (int i = 0; i < vCount; i++)// 随机产生vCount个位于球面上的点
		{
			// 随机产生每个星星的xyz坐标
			double angleTempJD = Math.PI * 2 * Math.random();// 经度上0~360度内随机度数
			double angleTempWD = Math.PI / 2 * Math.random();// 纬度上0~90度内随机度数
			vertices[i * 3] = (float) (UNIT_SIZE * Math.cos(angleTempWD) * Math
					.sin(angleTempJD));// 通过球公式计算球面上对应经纬度上的点的坐标
			vertices[i * 3 + 1] = (float) (UNIT_SIZE * Math.sin(angleTempWD));
			vertices[i * 3 + 2] = (float) (UNIT_SIZE * Math.cos(angleTempWD) * Math
					.cos(angleTempJD));
		}

		// 创建顶点坐标数据缓冲
		// vertices.length*4是因为一个Float四个字节
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());// 设置字节顺序
		mVertexBuffer = vbb.asFloatBuffer();// 转换为int型缓冲
		mVertexBuffer.put(vertices);// 向缓冲区中放入顶点坐标数据
		mVertexBuffer.position(0);// 设置缓冲区起始位置
		// 特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
		// 转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
		// 顶点坐标数据的初始化================end============================

		// 顶点着色数据的初始化================begin============================
		final int one = 65535;
		int colors[] = new int[vCount * 4];// 顶点颜色值数组，每个顶点4个色彩值RGBA
		for (int i = 0; i < vCount; i++)// 将所有点的颜色设置成白色。
		{
			colors[i * 4] = one;
			colors[i * 4 + 1] = one;
			colors[i * 4 + 2] = one;
			colors[i * 4 + 3] = 0;
		}

		// 创建顶点着色数据缓冲
		// vertices.length*4是因为一个int型整数四个字节
		ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
		cbb.order(ByteOrder.nativeOrder());// 设置字节顺序
		mColorBuffer = cbb.asIntBuffer();// 转换为int型缓冲
		mColorBuffer.put(colors);// 向缓冲区中放入顶点着色数据
		mColorBuffer.position(0);// 设置缓冲区起始位置
		// 特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
		// 转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
		// 顶点着色数据的初始化================end============================
	}

	/**
	 * @param f
	 * @param g
	 * @param scale2
	 * @param yAngle2
	 * @param vCount2
	 */
	public MyCelestial(float f, float g, int scale2, int yAngle2, int vCount2) {
		this((int) f, (int) g, (float) scale2, (float) yAngle2, vCount2);
	}

	public void drawSelf(GL10 gl) {
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);// 启用顶点坐标数组
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);// 启用顶点颜色数组

		gl.glDisable(GL10.GL_LIGHTING);// 不允许光照
		gl.glPointSize(scale);// 设置星星尺寸
		gl.glPushMatrix();// 保护变换矩阵
		gl.glTranslatef(xOffset * UNIT_SIZE, 0, 0);// x向偏移
		gl.glTranslatef(0, 0, zOffset * UNIT_SIZE);// y向偏移
		gl.glRotatef(yAngle, 0, 1, 0);// y轴旋转

		// 为画笔指定顶点坐标数据
		gl.glVertexPointer(3, // 每个顶点的坐标数量为3 xyz
				GL10.GL_FLOAT, // 顶点坐标值的类型为 GL_FIXED
				0, // 连续顶点坐标数据之间的间隔
				mVertexBuffer // 顶点坐标数据
		);

		// 为画笔指定顶点着色数据
		gl.glColorPointer(4, // 设置颜色的组成成分，必须为4—RGBA
				GL10.GL_FIXED, // 顶点颜色值的类型为 GL_FIXED
				0, // 连续顶点着色数据之间的间隔
				mColorBuffer // 顶点着色数据
		);

		// 绘制 点
		gl.glDrawArrays(GL10.GL_POINTS, // 以点方式填充
				0, // 开始点编号
				vCount // 顶点的数量
		);

		gl.glPopMatrix();// 恢复变换矩阵
		gl.glPointSize(1);// 恢复像素尺寸
		gl.glEnable(GL10.GL_LIGHTING);// 允许光照
	}
}
