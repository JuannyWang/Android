package ghost.picmatch.util;

import ghost.picmatch.logic.GameHelp;
import ghost.picmatch.logic.GameLoss;
import ghost.picmatch.logic.GameMenu;
import ghost.picmatch.logic.GameObject;
import ghost.picmatch.logic.GamePanel;
import ghost.picmatch.logic.GamePause;
import ghost.picmatch.logic.GameRank;
import ghost.picmatch.logic.GameWin;

public class GameObjectFactory implements Common {

	private static GameMenu menu;
	private static GamePanel run;
	private static GamePause pause;
	private static GameWin win;
	private static GameLoss loss;
	private static GameHelp help;
	private static GameRank rank;

	public static void inilization() {
		menu = new GameMenu();
		run = new GamePanel();
		pause = new GamePause();
		win = new GameWin();
		loss = new GameLoss();
		help = new GameHelp();
		rank = new GameRank();
	}

	public static GameObject getInstance(int status) {
		switch (status) {
		case LOADING:
			return null;
		case MENU:
			return menu;
		case PAUSE:
			return pause;
		case RANK:
			return rank;
		case WIN:
			return win;
		case LOSS:
			return loss;
		case HELP:
			return help;
		case RUN:
			return run;
		default:
			return null;
		}
	}
}
