/**
 * 
 */
package ghost.fivechess.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import ghost.fivechess.bean.Common;
import ghost.fivechess.bean.GameData;
import ghost.fivechess.bean.ImageFactory;
import ghost.fivechess.bean.PanelFactory;
import ghost.fivechess.gui.panel.PanelObject;

import javax.swing.JPanel;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-1-18
 */
public class GamePanel extends JPanel implements Common, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 界面刷星线程
	 */
	private Thread update_thread;

	/**
	 * 游戏对象
	 */
	private PanelObject game;

	/**
	 * 
	 * @param width
	 *            屏幕宽度
	 * @param height
	 *            屏幕高度
	 */
	public GamePanel(int width, int height) {
		super();
		GameData.status = MENU;
		GameData.scaleWidth = (float) width / (float) GameData.width;
		GameData.scaleHeight = (float) height / (float) GameData.height;
		inilization();
		update_thread = new Thread(new Update());
		this.addMouseListener(this);
		update_thread.start();
	}

	private void inilization() {
		ImageFactory.loadImageFromAssets("image/background/background.png",
				"background");
		ImageFactory.loadImageFromAssets("image/logo/logo.png", "logo");
		ImageFactory.loadImageFromAssets("image/button/start.png", "start");
		ImageFactory.loadImageFromAssets("image/button/help.png", "help");
		ImageFactory.loadImageFromAssets("image/button/exit.png", "exit");
		ImageFactory.loadImageFromAssets("image/button/return.png", "return");
		ImageFactory.loadImageFromAssets("image/button/gamestart.png",
				"game_start");
		ImageFactory.loadImageFromAssets("image/button/gamerestart.png",
				"game_restart");
		ImageFactory.loadImageFromAssets("image/button/gamehelp.png",
				"game_help");
		ImageFactory.loadImageFromAssets("image/button/gameback.png",
				"game_back");
		ImageFactory.loadImageFromAssets("image/func/help.png", "help_text");
		ImageFactory.loadImageFromAssets("image/func/win.png", "win_text");
		ImageFactory.loadImageFromAssets("image/func/loss.png", "loss_text");
		ImageFactory.loadImageFromAssets("image/func/chessboard.jpg",
				"chessboard");
		ImageFactory.loadImageFromAssets("image/func/white.png",
				"white");
		ImageFactory.loadImageFromAssets("image/func/black.png",
				"black");
		ImageFactory.loadImageFromAssets("image/func/choose.png",
				"choose");
		GameData.flag = true;
		GameData.debugModel = true;
		GameData.fps = 15;
		GameData.moveSpeedIn=10;
		GameData.moveSpeedOut=20;
	}

	private void logic() {

	}

	/**
	 * 界面刷新类
	 * 
	 * @author ghost
	 * 
	 */
	private class Update implements Runnable {

		@Override
		public void run() {
			while (GameData.flag) {
				long start = System.currentTimeMillis();
				repaint();
				logic();
				long end = System.currentTimeMillis();
				if (end - start < GameData.fps) {
					try {
						Thread.sleep(GameData.fps - (end - start));
					} catch (InterruptedException e) {
					}
				}
			}
		}
	}

	public void paint(Graphics g) {
		super.paint(g);
		GameData.g2d = (Graphics2D) g;
		game = PanelFactory.getInstance(GameData.status);
		game.drawSelf();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		/**
		 * 将鼠标坐标转换成通用坐标
		 */
		int x = (int) (e.getX() / GameData.scaleWidth);
		int y = (int) (e.getY() / GameData.scaleHeight);
		game = PanelFactory.getInstance(GameData.status);
		game.clickAction(x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
