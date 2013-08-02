/**
 * 
 */
package test;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * @author ÐþÓê
 * @qq 821580467
 * @date 2013-7-19
 */
public class MoveImage extends JFrame {

	private static final long serialVersionUID = 1L;

	private JLabel label;

	public MoveImage() {
		super("ÒÆ¶¯µÄÍ¼±ê");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 200, 500, 500);
		label = new JLabel(new ImageIcon("logo.png"));
		label.setBounds(10, 100, 80, 80);
		this.setLayout(null);
		this.add(label);
		this.setResizable(false);
		this.setVisible(true);
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					label.setBounds(
							(label.getBounds().x + 1 < 420) ? (label
									.getBounds().x + 1) : 0,
							label.getBounds().y, label.getBounds().width, label
									.getBounds().height);
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		}).start();
	}

	public static void main(String[] args) {
		new MoveImage();
	}

}
