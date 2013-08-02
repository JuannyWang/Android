/**
 * 
 */
package ghost.android3d.opengl44;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-2-8
 */
public class MyFireWorksThread extends Thread {
	static final float SPEED_SPAN = 1.9f;// 粒子初速度
	static final float SPEED = 0.08f;// 粒子移动每一步的模拟时延，也就是时间戳间隔
	MyFireWorks fw;
	boolean flag = true;

	public MyFireWorksThread(MyFireWorks fw) {
		this.fw = fw;
		this.setName("FWT");
	}

	public void run() {
		while (flag) {
			// 随机添加粒子
			for (int i = 0; i < 12; i++) {
				// 随机选择粒子的绘制者
				int index = (int) (MyFireWorks.pfdArray.length * Math.random());
				// 随机产生粒子的方位角及仰角
				double elevation = Math.random() * Math.PI / 12 + Math.PI * 5
						/ 12;// 仰角
				double direction = Math.random() * Math.PI * 2;// 方位角
				// 计算出粒子在XYZ轴方向的速度分量
				float vy = (float) (SPEED_SPAN * Math.sin(elevation));
				float vx = (float) (SPEED_SPAN * Math.cos(elevation) * Math
						.cos(direction));
				float vz = (float) (SPEED_SPAN * Math.cos(elevation) * Math
						.sin(direction));
				// 创建粒子对像并添加进粒子列表
				fw.al.add(new MySingleParticle(index, vx, vy, vz));
			}

			// 按时间运动粒子并标识过期粒子
			ArrayList<MySingleParticle> alDel = new ArrayList<MySingleParticle>();
			for (MySingleParticle sp : fw.al) {// 扫描粒子列表，并修改粒子时间戳
				sp.timeSpan = sp.timeSpan + SPEED;
				if (sp.timeSpan > 3f) {// 若时间戳超过时限则将粒子添加进删除列表已备删除
					alDel.add(sp);
				}
			}

			// 删除过期粒子
			for (MySingleParticle sp : alDel) {
				fw.al.remove(sp);
			}

			// 给粒子根据离视点（摄像机）的距离排序，保证先绘制远的粒子，后绘制近的粒子
			// 这样才能保证混合工作正常
			try {
				Collections.sort(fw.al);
			} catch (Exception e) {
			}

			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
