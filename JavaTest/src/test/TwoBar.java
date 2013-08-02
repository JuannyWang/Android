/**
 * 
 */
package test;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-7-19
 */
public class TwoBar extends JFrame {

	private static final long serialVersionUID = 1L;

	private JProgressBar jp1, jp2;
	private Thread t1, t2;

	public TwoBar() {
		super("两个进度条");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 200, 500, 500);
		jp1 = new JProgressBar(0, 100);
		jp1.setStringPainted(true);
		jp2 = new JProgressBar(0, 100);
		jp2.setStringPainted(true);
		this.add(jp1,BorderLayout.NORTH);
		this.add(jp2,BorderLayout.SOUTH);
		t1 = new Thread(new Runnable() {
			public void run() {
				while (jp1.getValue() < 100) {
					try {
						jp1.setValue(jp1.getValue() + 1);
						t2.join();
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		t2 = new Thread(new Runnable() {
			public void run() {
				while (jp2.getValue() < 100) {
					try {
						jp2.setValue(jp2.getValue() + 1);
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		this.setVisible(true);
		t1.start();
		t2.start();
		
	}

	public static void main(String[] args) {
		new TwoBar();
	}

}
