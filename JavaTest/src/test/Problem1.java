/**
 * 
 */
package test;

import java.util.Date;

/**
 * @author аўгъ
 * @qq 821580467
 * @date 2013-7-19
 */
public class Problem1 {

	public static void main(String[] args) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while(true) {
					System.out.println(new Date());
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
		}).start();
	}

}
