/**
 * 
 */
package ghost.android3d.opengl43;

import javax.microedition.khronos.opengles.GL10;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-2-5
 */
public class MySingleParticle {
	int particleForDrawIndex;//对应绘制粒子的编号
	float vx;//x轴速度分量
	float vy;//y轴速度分量
	float vz;//z轴速度分量
	float timeSpan=0;//累计时间
	
	public MySingleParticle(int particleForDrawIndex,float vx,float vy,float vz)
	{
		this.particleForDrawIndex=particleForDrawIndex;
		this.vx=vx;
		this.vy=vy;
		this.vz=vz;		
	}
	
	public void drawSelf(GL10 gl)
	{
		gl.glPushMatrix();		
		//根据当前时间戳计算出粒子位置
		float x=vx*timeSpan;
		float z=vz*timeSpan;
		float y=vy*timeSpan-0.5f*timeSpan*timeSpan*1.0f;		
		gl.glTranslatef(x, y, z);
		//绘制粒子
		MyFireWorks.pfdArray[particleForDrawIndex].drawSelf(gl);
		gl.glPopMatrix();
	}
}
