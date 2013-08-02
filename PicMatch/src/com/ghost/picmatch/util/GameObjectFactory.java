package com.ghost.picmatch.util;

import com.ghost.picmatch.logic.GameHelp;
import com.ghost.picmatch.logic.GameLoss;
import com.ghost.picmatch.logic.GameMenu;
import com.ghost.picmatch.logic.GameObject;
import com.ghost.picmatch.logic.GamePanel;
import com.ghost.picmatch.logic.GamePause;
import com.ghost.picmatch.logic.GameRank;
import com.ghost.picmatch.logic.GameWin;

/**
 * GameObject的简单工厂，用于得到对应的面板
 * @author ghost
 *
 */
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
