package com.ghost.picmatch.logic;

import com.ghost.picmatch.data.GameData;
import com.ghost.picmatch.data.ImageData;
import com.ghost.picmatch.util.Tool;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * 胜利面板
 * @author ghost
 *
 */
public class GameWin extends GameObject {

	@Override
	public void drawBackground(Canvas canvas,Paint paint) {
		Tool.drawImage(ImageData.background[3], 0, 0, GameData.width,
				GameData.height, canvas,paint);

	}

	@Override
	public void drawFunc(Canvas canvas,Paint paint) {
		Tool.drawImage(ImageData.score_display, 140, 600, 200, 40, canvas,paint);
		paint.setColor(Color.GRAY);
		Tool.drawText(GameData.grade + "", 245, 626, canvas,paint);
		Tool.drawText(GameData.score + "", 295, 626, canvas,paint);

	}

	@Override
	public void drawLogo(Canvas canvas,Paint paint) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawButton(Canvas canvas,Paint paint) {
		Tool.drawImage(ImageData.resume_button[0], 30, 690, 180, 50, canvas,paint);
		Tool.drawImage(ImageData.return_button[0], 270, 690, 180, 50, canvas,paint);

	}

	@Override
	public void clickAction(int x, int y) {
		if (y >= 690 && y <= 740) {
			if (x >= 30 && x <= 210) {
				GameData.score += (GameData.time / 10 + 10) * GameData.grade;
				GameData.grade++;
				if (GameData.grade > 10) {
					GameData.grade = 9;
				}
				GameLogic.prepareNewGame();
				GameData.status = RUN;
			} else if (x >= 270 && x <= 450) {
				Tool.updateScoreList(GameData.score);
				GameData.status = MENU;
			}
		}

	}

	@Override
	public void doLogic() {
		// TODO Auto-generated method stub

	}

}
