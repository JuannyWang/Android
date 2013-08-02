/**
 * 
 */
package ghost.fivechess.gui.panel;

import ghost.fivechess.bean.Common;
import ghost.fivechess.bean.GameData;
import ghost.fivechess.util.Tool;

/**
 * 所有面板类的父类（需要平台无关性）
 * 
 * @author 玄雨
 * @qq 821580467
 * @date 2013-1-18
 */
abstract public class PanelObject implements Common {
	
	protected int nextStatus;
	
	/**
	 * 为了制造动画效果而创建的点
	 */
	protected int position[];
	
	protected int tempPosition[];
	
	PanelObject() {
		status=PANEL_START;
	}
	
	/**
	 * 面板记录日志名称
	 */
	protected String myTag = "PanelObject";
	
	/**
	 * 面板的独立状态
	 */
	protected int status;

	/**
	 * 绘制当前面板的功能
	 */
	abstract protected void drawFunction();

	/**
	 * 绘制函数
	 */
	public void drawSelf() {
		/**
		 * 绘制背景
		 */
		Tool.drawImage("background", 0, 0, GameData.width, GameData.height);
		drawFunction();
	}
	
	/**
	 * 处理鼠标事件响应
	 * @param x 
	 * @param y
	 */
	abstract public void clickAction(int x,int y) ;
}
