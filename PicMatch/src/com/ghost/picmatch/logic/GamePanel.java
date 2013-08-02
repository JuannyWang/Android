package com.ghost.picmatch.logic;

import com.ghost.picmatch.data.GameData;
import com.ghost.picmatch.data.ImageData;
import com.ghost.picmatch.data.Line;
import com.ghost.picmatch.util.Tool;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * 游戏面板
 * @author ghost
 *
 */
public class GamePanel extends GameObject {

	@Override
	public void drawBackground(Canvas canvas, Paint paint) {
		Tool.drawImage(ImageData.background[0], 0, 0, GameData.width,
				GameData.height, canvas, paint);

	}

	@Override
	public void drawFunc(Canvas canvas, Paint paint) {
		paint.setColor(Color.WHITE);
		Tool.drawText("时间:", 45, 130, canvas, paint);
		Tool.drawImage(ImageData.red, 110, 120, 320, 10, canvas, paint);
		Tool.drawImage(
				ImageData.green,
				110,
				120,
				(int) (320 * (float) GameData.time / (float) GameData.max_time) > 0 ? ((int) (320 * (float) GameData.time / (float) GameData.max_time))
						: 1, 10, canvas, paint);
		int x = 30, y = 150;
		int key = -1;
		int degree = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 7; ++j) {
				key = GameData.map[i][j];
				if (key != -1) {
					degree = GameData.mapvalue[i][j][0];
					canvas.save();
					canvas.translate((x + j * 60 + 30) * GameData.scaleW, (y
							+ i * 60 + 30)
							* GameData.scaleH);
					canvas.rotate((float) degree);
					if (GameData.mapvalue[i][j][2] == 1) {
						Tool.drawImage(ImageData.black, -30, -30, 55, 55,
								canvas, paint);
					}
					Tool.drawImage(ImageData.pics[key], -30, -30, 55, 55,
							canvas, paint);
					canvas.restore();
				}
			}
		}
		Tool.drawImage(ImageData.score_display, 140, 640, 200, 40, canvas,
				paint);
		paint.setColor(Color.GRAY);
		Tool.drawText("" + GameData.grade, 245, 666, canvas, paint);
		Tool.drawText("" + GameData.score, 295, 666, canvas, paint);
		for (int i = 0; i < GameData.lines.size(); ++i) {
			Line temp = GameData.lines.get(i);
			if (temp.life > 0) {
				temp.drawSelf(canvas, paint);
			} else {
				GameData.lines.remove(temp);
			}
		}
	}

	@Override
	public void drawLogo(Canvas canvas, Paint paint) {
		Tool.drawImage(ImageData.logo, 65, 10, 350, 80, canvas, paint);

	}

	@Override
	public void drawButton(Canvas canvas, Paint paint) {
		Tool.drawImage(ImageData.pause_button[0], 30, 690, 180, 50, canvas,
				paint);
		Tool.drawImage(ImageData.return_button[0], 270, 690, 180, 50, canvas,
				paint);

	}

	@Override
	public void clickAction(int x, int y) {
		if (y >= 690 && y <= 740) {
			if (x >= 30 && x <= 210) {
				GameData.status = PAUSE;
			} else if (x >= 270 && x <= 450) {
				Tool.updateScoreList(GameData.score);
				GameData.status = MENU;
			}
		} else if (x > 30 && x < 450 && y > 150 && y < 630) {
			int m, n;
			m = (x - 30) / 60;
			n = (y - 150) / 60;
			GameLogic.picMatch(n, m);
		}

	}

	@Override
	public void doLogic() {
		GameLogic.rolatePics();
		GameData.time--;
		if (GameData.time <= 0) {
			GameData.time = 0;
			GameData.status = LOSS;
		}

	}

}
