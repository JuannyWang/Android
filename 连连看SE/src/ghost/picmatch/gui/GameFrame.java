package ghost.picmatch.gui;

import java.awt.BorderLayout;

import ghost.picmatch.gui.render.MyRender;

import javax.swing.JFrame;

/**
 * 桌面框架类,继承自JFrame，用于创建一个3:5的窗口，用于模拟手机屏幕
 * @author ghost
 *
 */
public class GameFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 屏幕长和宽
	 */
	private final int width=360;
	private final int height=600;
	
	/**
	 * 渲染器
	 */
	private MyRender render;
	
	public GameFrame() {
		super("连连看————by：玄雨");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(width, height);
		this.setLocation(200, 100);
		this.setResizable(false);
		render=new MyRender(width,height);
		this.add(render,BorderLayout.CENTER);
		this.setVisible(true);
	}
}
