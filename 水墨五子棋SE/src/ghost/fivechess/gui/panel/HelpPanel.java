/**
 * 
 */
package ghost.fivechess.gui.panel;

import ghost.fivechess.bean.GameData;
import ghost.fivechess.util.Tool;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-1-18
 */
public class HelpPanel extends PanelObject {

	public HelpPanel() {
		super();
		position = new int[2];
		tempPosition = new int[2];
		position[0] = 20;
		position[1] = 550;
		for (int i = 0; i < 2; ++i) {
			tempPosition[i] = GameData.width;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ghost.fivechess.gui.panel.PanelObject#drawFunction()
	 */
	@Override
	protected void drawFunction() {
		Tool.drawImage("return", tempPosition[1], 410, 210, 50);
		Tool.drawImage("help_text", tempPosition[0], 0, 500, 480);
		boolean tempKey = true;
		switch (status) {
		case PANEL_START:
			tempKey = true;
			for (int i = 0; i < 2; ++i) {
				if (tempPosition[i] != position[i]) {
					tempKey = false;
					break;
				}
			}
			if (tempKey) {
				this.status = PANEL_READY;
			} else {
				for (int i = 0; i < 2; ++i) {
					if (tempPosition[i] > position[i]) {
						tempPosition[i] -= GameData.moveSpeedIn;
						if (tempPosition[i] < position[i]) {
							tempPosition[i] = position[i];
						}
					}
				}
			}
			break;
		case PANEL_READY:
			break;
		case PANEL_CLOSE:
			tempKey = true;
			for (int i = 0; i < 2; ++i) {
				if (tempPosition[i] < GameData.width) {
					tempKey = false;
					break;
				}
			}
			if (tempKey) {
				this.status = PANEL_START;
				GameData.status = nextStatus;
			} else {
				for (int i = 0; i < 2; ++i) {
					if (tempPosition[i] < GameData.width) {
						tempPosition[i] += GameData.moveSpeedOut;
						if (tempPosition[i] > GameData.width) {
							tempPosition[i] = GameData.width;
						}
					}
				}
			}
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ghost.fivechess.gui.panel.PanelObject#clickAction(int, int)
	 */
	@Override
	public void clickAction(int x, int y) {
		if (status == PANEL_READY) {
			if (y >= 410 && y <= 460 && x >= position[1]
					&& x <= (position[1] + 210)) {
				nextStatus = MENU;
				this.status = PANEL_CLOSE;
			}
		}
	}

}
