package ghost.picmatch.gui.render;

import ghost.picmatch.data.GameData;
import ghost.picmatch.data.Line;
import ghost.picmatch.logic.GameObject;
import ghost.picmatch.util.Common;
import ghost.picmatch.util.GameObjectFactory;
import ghost.picmatch.util.Tool;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * 渲染类，继承自JPanel，用于实现界面的渲染
 * 
 * @author ghost
 * 
 */
public class MyRender extends JPanel implements Common, MouseListener {

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
	GameObject game;

	/**
	 * 
	 * @param width
	 *            屏幕宽度
	 * @param height
	 *            屏幕高度
	 */
	public MyRender(int width, int height) {
		super();
		GameData.status = LOADING;
		GameData.scaleW = (float) width / (float) GameData.width;
		GameData.scaleH = (float) height / (float) GameData.height;
		update_thread = new Thread(new Update());
		inilization();
		update_thread.start();
		this.addMouseListener(this);
	}

	/**
	 * 初始化游戏需要的参数
	 */
	private void inilization() {
		if (!Tool.loadAllImage()) {
			System.exit(-1);
		}
		GameObjectFactory.inilization();
		GameData.status = MENU;
		GameData.lines = new ArrayList<Line>();
		GameData.flag = true;
	}

	/**
	 * 重构绘制函数，在此作图
	 */
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		drawBackground(g2d);
		drawFunc(g2d);
		drawButtons(g2d);
		drawLogo(g2d);
	}

	/**
	 * 绘制模板
	 * 
	 * @param g2d
	 */
	@SuppressWarnings("unused")
	private void draw(Graphics2D g2d) {
		switch (GameData.status) {
		case LOADING:
			break;
		case MENU:
			break;
		case PAUSE:
			break;
		case RANK:
			break;
		case WIN:
			break;
		case LOSS:
			break;
		case HELP:
			break;
		case RUN:
			break;
		}
	}

	/**
	 * 绘制图块
	 * 
	 * @param g2d
	 */
	private void drawFunc(Graphics2D g2d) {
		game = GameObjectFactory.getInstance(GameData.status);
		game.drawFunc(g2d);
	}

	/**
	 * 绘制logo
	 * 
	 * @param g2d
	 */
	private void drawLogo(Graphics2D g2d) {
		game = GameObjectFactory.getInstance(GameData.status);
		game.drawLogo(g2d);
	}

	/**
	 * 绘制按钮
	 */
	private void drawButtons(Graphics2D g2d) {
		game = GameObjectFactory.getInstance(GameData.status);
		game.drawButton(g2d);
	}

	/**
	 * 绘制背景
	 * 
	 * @param g2d
	 */
	private void drawBackground(Graphics2D g2d) {
		game = GameObjectFactory.getInstance(GameData.status);
		game.drawBackground(g2d);
	}

	/**
	 * 逻辑处理函数
	 */
	private void logic() {
		game = GameObjectFactory.getInstance(GameData.status);
		game.doLogic();

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

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		/**
		 * 将鼠标坐标转换成通用坐标
		 */
		int x = (int) (e.getX() / GameData.scaleW);
		int y = (int) (e.getY() / GameData.scaleH);
		game = GameObjectFactory.getInstance(GameData.status);
		game.clickAction(x, y);

	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
