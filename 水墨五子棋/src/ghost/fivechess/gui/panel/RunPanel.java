/**
 * 
 */
package ghost.fivechess.gui.panel;


import android.graphics.Point;
import ghost.fivechess.bean.GameData;
import ghost.fivechess.logic.GameFunction;
import ghost.fivechess.util.Tool;

/**
 * 游戏运行界面
 * @author 玄雨
 * @qq 821580467
 * @date 2013-1-18
 */
public class RunPanel extends PanelObject {

	/**
	 * 游戏中的中转状态
	 */
	private int tempStatus;
	/**
	 * 提示点位置
	 */
	private Point p1;

	public RunPanel() {
		super();
		position = new int[2];
		tempPosition = new int[2];
		position[0] = 20;
		position[1] = 550;
		for (int i = 0; i < 2; ++i) {
			tempPosition[i] = GameData.width;
		}
		GameFunction.status = GAME_PRE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ghost.fivechess.gui.panel.PanelObject#drawFunction()
	 */
	@Override
	protected void drawFunction() {
		if (GameFunction.status == GAME_PRE) {
			Tool.drawImage("game_start", tempPosition[1], 40, 210, 50);
		} else {
			Tool.drawImage("game_restart", tempPosition[1], 40, 210, 50);
		}
		if (GameFunction.status == GAME_RUN) {
			Tool.drawImage("game_back", tempPosition[1], 160, 210, 50);
			Tool.drawImage("game_help", tempPosition[1], 280, 210, 50);
		}
		Tool.drawImage("return", tempPosition[1], 400, 210, 50);
		Tool.drawImage("chessboard", tempPosition[0], 0, 480, 480);
		if (status == PANEL_READY) {
			if (GameFunction.status != GAME_PRE
					&& GameFunction.status != GAME_CHOOSE) {
				int map[][] = GameFunction.getMap();
				int tempX = 20, tempY = 2;
				for (int i = 0; i < 15; ++i) {
					for (int j = 0; j < 15; ++j) {
						switch (map[i][j]) {
						case 1:
							Tool.drawImage("white", tempX + i * 32, tempY + j
									* 32, 32, 32);
							break;
						case -1:
							Tool.drawImage("black", tempX + i * 32, tempY + j
									* 32, 32, 32);
							break;
						}
					}
				}
				if(p1!=null) {
					Tool.drawRect(p1.x, p1.y);
				}
			}
			if (GameFunction.status == GAME_WIN) {
				Tool.drawImage("win_text", 180, 70, 140, 35);
			} else if (GameFunction.status == GAME_LOSS) {
				Tool.drawImage("loss_text", 180, 70, 140, 35);
			}
			if (GameFunction.status == GAME_CHOOSE) {
				Tool.drawImage("choose", 200, 130, 400, 300);
			}
		}
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
		if (this.status == PANEL_READY) {
			if (GameFunction.status == GAME_CHOOSE) {
				if (y >= 220 && y <= 390 && x >= 200 && x <= 600) {
					if (x <= 400) {
						GameFunction.newGame(true);
					} else {
						GameFunction.newGame(false);
					}
					p1=null;
					GameFunction.status = GAME_RUN;
					return;
				}
				GameFunction.status = tempStatus;
				return;
			}
			if (y >= 40 && y <= 90 && x >= position[1]
					&& x <= (position[1] + 210)) {
				nextStatus = RUN;
				if (GameFunction.status != GAME_CHOOSE) {
					tempStatus = GameFunction.status;
					GameFunction.status = GAME_CHOOSE;
				}
			} else if (y >= 160 && y <= 210 && x >= position[1]
					&& x <= (position[1] + 200)) {
				nextStatus = RUN;
				if (GameFunction.status == GAME_RUN) {
					GameFunction.pop();
					p1=null;
				}
			} else if (y >= 280 && y <= 330 && x >= position[1]
					&& x <= (position[1] + 200)) {
				nextStatus = RUN;
				if (GameFunction.status == GAME_RUN) {
					Point helpPoint=GameFunction.getComputerDone(1);
					int m=helpPoint.x;
					int n=helpPoint.y;
					GameFunction.doClick(m, n);
					if (GameFunction.checkWhoWin(m, n) == 1) {
						GameFunction.status = GAME_WIN;
						return;
					}
					Point computerDown = GameFunction.getComputerDone(-1);
					m = computerDown.x;
					n = computerDown.y;
					GameFunction.doComputerClick(m, n);
					p1=new Point(m,n);
					if (GameFunction.checkWhoWin(m, n) == -1) {
						GameFunction.status = GAME_LOSS;
						return;
					}
				}
			} else if (y >= 400 && y <= 450 && x >= position[1]
					&& x <= (position[1] + 200)) {
				nextStatus = MENU;
				this.status = PANEL_CLOSE;
			} else if (y >= 0 && y <= 480 && x >= 20 && x <= 500
					&& GameFunction.status == GAME_RUN) {
				int tempX = x - 20;
				int tempY = y - 2;
				int m = tempX / 32;
				int n = tempY / 32;
				if (GameFunction.doClick(m, n)) {
					if (GameFunction.checkWhoWin(m, n) == 1) {
						GameFunction.status = GAME_WIN;
						return;
					}
					Point computerDown = GameFunction.getComputerDone(-1);
					m = computerDown.x;
					n = computerDown.y;
					GameFunction.doComputerClick(m, n);
					p1=new Point(m,n);
					if (GameFunction.checkWhoWin(m, n) == -1) {
						GameFunction.status = GAME_LOSS;
						return;
					}
				}
			}
		}
	}

}
