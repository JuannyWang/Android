/**
 * 
 */
package test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-7-19
 */
public class Problem3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new MyJFrame();
	}

}

class MyJPanel extends JPanel implements KeyListener {

	private static final long serialVersionUID = 1L;

	private final int width = 800, height = 480;

	private Font font;

	private Random rand;

	private ArrayList<GameWord> wordList;
	private ArrayList<GameBoom> boomList;
	
	private int omit,hit;

	public MyJPanel() {
		super();
		this.setPreferredSize(new Dimension(width, height));
		font = new Font("Aria", Font.BOLD, 30);
		rand = new Random();
		wordList = new ArrayList<GameWord>();
		boomList = new ArrayList<GameBoom>();
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					wordList.add(new GameWord((char) (rand.nextInt(24) + 65),
							rand.nextInt(width - 30), 30));
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		}).start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					repaint();
				}
			}

		}).start();
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setFont(font);
		g2d.setColor(Color.BLUE);
		g2d.fillRect(0, 0, width, height);
		g2d.setColor(Color.RED);
		for (int i = 0; i < wordList.size(); ++i) {
			GameWord w = wordList.get(i);
			g2d.drawString(w.data + "", w.x, w.y);
			w.y++;
			if (w.y > height) {
				wordList.remove(w);
				omit++;
			}
		}
		
		for (int i = 0; i < boomList.size(); ++i) {
			GameBoom b = boomList.get(i);
			g2d.setColor(Color.YELLOW);
			g2d.drawString(b.word.data + "", b.word.x, b.word.y);
			g2d.setColor(Color.BLACK);
			g2d.fillRect(b.word.x + 5, b.y, 10, 20);
			b.word.y++;
			b.y -= 10;
			if (b.y <= b.word.y) {
				boomList.remove(b);
			}
		}
		
		g2d.setFont(new Font("Aria", Font.BOLD, 16));
		g2d.setColor(Color.GRAY);
		g2d.drawString("当前击中:" + hit + "个 遗漏:" + omit +"个", 10 , 50);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		char command = e.getKeyChar();
		for (int i = 0; i < wordList.size(); ++i) {
			GameWord w = wordList.get(i);
			if (w.data == command) {
				boomList.add(new GameBoom(w, height));
				wordList.remove(w);
				hit++;
				break;
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}

class GameWord {
	public char data;
	public int x, y;

	public GameWord(char data, int x, int y) {
		this.x = x;
		this.y = y;
		this.data = data;
	}
}

class GameBoom {
	public GameWord word;
	public int y;

	public GameBoom(GameWord word, int y) {
		this.word = word;
		this.y = y;
	}
}

class MyJFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private MyJPanel panel;

	public MyJFrame() {
		super("打字游戏");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(100, 200);
		panel = new MyJPanel();
		this.addKeyListener(panel);
		this.add(panel);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
	}
}
