/**
 * 
 */
package ghost.android3d.opengl43;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-2-5
 */
public class MyFireWorks {
	//用于绘制的各个颜色粒子组成的数组
    static MyPointParticle[] pfdArray=new MyPointParticle[]
    {
   	 new MyPointParticle(2,0.9882f,0.9882f,0.8784f,0),
   	 new MyPointParticle(2,0.9216f,0.2784f,0.2392f,0),
   	 new MyPointParticle(2,1.0f,0.3686f,0.2824f,0),
   	 new MyPointParticle(2,0.8157f,0.9882f,0.6863f,0),
   	 new MyPointParticle(2,0.9922f,0.7843f,0.9882f,0),
   	 new MyPointParticle(2,0.1f,1,0.3f,0)    	 
    };
    
    //所有焰火粒子的列表
    ArrayList<MySingleParticle> al=new ArrayList<MySingleParticle>();
    //定时运动所有焰火粒子的线程 
    MyFireWorkThread fwt;
    public MyFireWorks()
    {
   	 //初始化定时运动所有焰火粒子的线程并启动
   	 fwt=new MyFireWorkThread(this);
   	 fwt.start();
    }
    
    public void drawSelf(GL10 gl)
    {
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
				e.printStackTrace();
	    	 }
   	 } 
    }
}
