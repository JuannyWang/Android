/**
 * 
 */
package ghost.android3d.opengl42;

import java.util.ArrayList;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-2-4
 */
public class MyBallControl extends Thread implements Constants {
	ArrayList<MyLogicBall> albfc;
	
	public MyBallControl(ArrayList<MyLogicBall> albfc)
	{	
		this.albfc=albfc;
	}
	
	public void run(){
		while(THREAD_FLAG)
		{
			for(MyLogicBall lb:albfc)
			{//循环控制每一个球
				lb.move(albfc);
			}
			
			try{
				sleep(50);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
}
