/**
 * 
 */
package ghost.android3d.opengl44;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-2-8
 */
public class MyFireWorks {
	//用于绘制的各个纹理粒子组成的数组
    static MyTextureParticle[] pfdArray;
    
    //所有焰火粒子的列表 
    ArrayList<MySingleParticle> al=new ArrayList<MySingleParticle>();
    //定时运动所有焰火粒子的线程
    MyFireWorksThread fwt;
    public MyFireWorks(int[] texId)
    {
   	 //对用于绘制的各个纹理粒子进行初始化
   	 pfdArray=new MyTextureParticle[]
	     {
	     	 new MyTextureParticle(texId[0],0.05f),
	     	 new MyTextureParticle(texId[1],0.05f),
	     	 new MyTextureParticle(texId[2],0.05f),
	     	 new MyTextureParticle(texId[3],0.05f),
	     	 new MyTextureParticle(texId[4],0.05f),
	     	 new MyTextureParticle(texId[5],0.05f)    	 
	     };
   	 
   	 
   	 //初始化定时运动所有焰火粒子的线程并启动
   	 fwt=new MyFireWorksThread(this);
   	 fwt.start();
    }
    
    public void drawSelf(GL10 gl)
    {
   	 //开启混合
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA,GL10.GL_ONE_MINUS_SRC_ALPHA);
   	 
		 int size=al.size();
		 //循环扫描所有焰火粒子的列表并绘制各个粒子
		 for(int i=0;i<size;i++)
   	 {
			 try
	    	 {
   		   al.get(i).drawSelf(gl);
	    	 }
			 catch(Exception e)
	    	 {
	    	 }
   	 } 
		 
		 //关闭混合
		 gl.glDisable(GL10.GL_BLEND);
    }
}
