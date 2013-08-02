/**
 * 
 */
package ghost.fivechess.bean;

import ghost.fivechess.gui.panel.HelpPanel;
import ghost.fivechess.gui.panel.MenuPanel;
import ghost.fivechess.gui.panel.PanelObject;
import ghost.fivechess.gui.panel.RunPanel;

/**
 * 面板工厂类，采用动态初始化
 * 
 * @author 玄雨
 * @qq 821580467
 * @date 2013-1-19
 */
abstract public class PanelFactory implements Common {
	private static MenuPanel menu;
	private static HelpPanel help;
	private static RunPanel run;

	public static PanelObject getInstance(int status) {
		PanelObject panel = null;
		switch (status) {
		case MENU:
			if (menu == null)
				menu = new MenuPanel();
			panel = menu;
			break;
		case RUN:
			if (run == null)
				run = new RunPanel();
			panel = run;
			break;
		case HELP:
			if (help == null)
				help = new HelpPanel();
			panel = help;
			break;
		}
		return panel;
	}
}
