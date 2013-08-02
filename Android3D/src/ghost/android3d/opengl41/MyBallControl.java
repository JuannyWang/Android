/**
 * 
 */
package ghost.android3d.opengl41;

import java.util.List;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-2-4
 */
public class MyBallControl extends Thread implements Constants {
	List<MyLogicBall> albfc;

	public MyBallControl(List<MyLogicBall> albfc) {
		this.albfc = albfc;
	}

	public void run() {
		while (THREAD_FLAG) {
			for (MyLogicBall lb : albfc) {// 循环控制每一个球
				if (lb.vy > MIN_SPEED || lb.state == 0 || lb.state == 1) {
					lb.move();
				}
			}
			try {
				sleep(50);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
