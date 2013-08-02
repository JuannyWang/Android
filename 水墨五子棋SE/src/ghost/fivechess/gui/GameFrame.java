/**
 * 
 */
package ghost.fivechess.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-1-18
 */
public class GameFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public GameFrame() {
		super("水墨五子棋————玄雨");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 480);
		this.setLocation(100, 100);
		this.add(new GamePanel(794,451),BorderLayout.CENTER);
		this.setResizable(false);
		this.setVisible(true);
	}

}
