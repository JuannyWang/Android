package com.ghost.picmatch.logic;

import com.ghost.picmatch.data.GameData;
import com.ghost.picmatch.data.ImageData;
import com.ghost.picmatch.util.GameObjectFactory;
import com.ghost.picmatch.util.Tool;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 主菜单面板
 * @author ghost
 *
 */
public class GameMenu extends GameObject {

	@Override
	public void drawBackground(Canvas canvas, Paint paint) {
		Tool.drawImage(ImageData.background[0], 0, 0, GameData.width,
				GameData.height, canvas,paint);

	}

	@Override
	public void drawFunc(Canvas canvas, Paint paint) {
		Tool.drawImage(ImageData.ad_button, 40, 170, 400, 125, canvas,paint);

	}

	@Override
	public void drawLogo(Canvas canvas, Paint paint) {
		Tool.drawImage(ImageData.logo, 65, 65, 350, 80, canvas,paint);

	}

	@Override
	public void drawButton(Canvas canvas, Paint paint) {
		Tool.drawImage(ImageData.start_button[0], 140, 350, 200, 44, canvas,paint);
		Tool.drawImage(ImageData.help_button[0], 140, 430, 200, 44, canvas,paint);
		Tool.drawImage(ImageData.rank_button[0], 140, 510, 200, 44, canvas,paint);
		Tool.drawImage(ImageData.exit_button[0], 140, 590, 200, 44, canvas,paint);

	}

	@Override
	public void clickAction(int x, int y) {
		if (x >= 140 && x <= 340) {
			if (y >= 350 && y <= 394) {
				GameLogic.prepareNewGame();
				GameData.status = RUN;
			} else if (y >= 430 && y <= 474) {
				GameData.status = HELP;
			} else if (y >= 510 && y <= 554) {
				((GameRank) GameObjectFactory.getInstance(RANK))
						.loadScoreList();
				GameData.status = RANK;
			} else if (y >= 590 && y <= 634) {
				System.exit(0);
			}
		}
	}

	@Override
	public void doLogic() {
		// TODO Auto-generated method stub

	}

}
