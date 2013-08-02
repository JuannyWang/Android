package com.ghost.picmatch.logic;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.ghost.picmatch.data.GameData;
import com.ghost.picmatch.data.ImageData;
import com.ghost.picmatch.util.Tool;

/**
 * 排行榜面板
 * @author ghost
 *
 */
public class GameRank extends GameObject {

	private int list[];

	public void loadScoreList() {
		list = Tool.getScoreList();
	}

	@Override
	public void drawBackground(Canvas canvas,Paint paint) {
		Tool.drawImage(ImageData.background[0], 0, 0, GameData.width,
				GameData.height, canvas,paint);

	}

	@Override
	public void drawFunc(Canvas canvas,Paint paint) {
		Tool.drawImage(ImageData.rank_back, 40, 200, 400, 460, canvas,paint);
		paint.setColor(Color.MAGENTA);
		Tool.drawText("No.1:" + list[0], 170, 300, canvas,paint);
		paint.setColor(Color.RED);
		Tool.drawText("No.2:" + list[1], 170, 340, canvas,paint);
		paint.setColor(Color.GREEN);
		Tool.drawText("No.3:" + list[2], 170, 380, canvas,paint);
		paint.setColor(Color.BLACK);
		Tool.drawText("No.4:" + list[3], 170, 420, canvas,paint);
		paint.setColor(Color.GRAY);
		Tool.drawText("No.5:" + list[4], 170, 460, canvas,paint);
		Tool.drawText("No.6:" + list[5], 170, 500, canvas,paint);
		Tool.drawText("No.7:" + list[6], 170, 540, canvas,paint);
		Tool.drawText("No.8:" + list[7], 170, 580, canvas,paint);
	}

	@Override
	public void drawLogo(Canvas canvas,Paint paint) {
		Tool.drawImage(ImageData.logo, 65, 65, 350, 80, canvas,paint);
	}

	@Override
	public void drawButton(Canvas canvas,Paint paint) {
		Tool.drawImage(ImageData.return_button[0], 270, 690, 180, 50, canvas,paint);

	}

	@Override
	public void clickAction(int x, int y) {
		if (y >= 690 && y <= 740 && x >= 270 && x <= 450) {
			GameData.status = MENU;
		}

	}

	@Override
	public void doLogic() {
		// TODO Auto-generated method stub

	}

}
