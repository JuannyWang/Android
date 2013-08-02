package com.ghost.picmatch.logic;

import com.ghost.picmatch.data.GameData;
import com.ghost.picmatch.data.ImageData;
import com.ghost.picmatch.util.Tool;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 帮助面板
 * @author ghost
 *
 */
public class GameHelp extends GameObject {

	@Override
	public void drawBackground(Canvas canvas, Paint paint) {
		Tool.drawImage(ImageData.background[0], 0, 0, GameData.width,
				GameData.height, canvas, paint);

	}

	@Override
	public void drawFunc(Canvas canvas, Paint paint) {
		Tool.drawImage(ImageData.help_text, 40, 200, 400, 457, canvas, paint);

	}

	@Override
	public void drawLogo(Canvas canvas, Paint paint) {
		Tool.drawImage(ImageData.logo, 65, 65, 350, 80, canvas, paint);

	}

	@Override
	public void drawButton(Canvas canvas, Paint paint) {
		Tool.drawImage(ImageData.return_button[0], 270, 690, 180, 50, canvas,
				paint);
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
