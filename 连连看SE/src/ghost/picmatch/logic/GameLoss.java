package ghost.picmatch.logic;

import ghost.picmatch.data.GameData;
import ghost.picmatch.data.ImageData;
import ghost.picmatch.util.Tool;

import java.awt.Color;
import java.awt.Graphics2D;

public class GameLoss extends GameObject {

	@Override
	public void drawBackground(Graphics2D g2d) {
		Tool.drawImage(ImageData.background[4], 0, 0, GameData.width,
				GameData.height, g2d);
		
	}

	@Override
	public void drawFunc(Graphics2D g2d) {
		Tool.drawImage(ImageData.score_display, 140, 600, 200, 40, g2d);
		g2d.setColor(Color.gray);
		Tool.drawText(GameData.grade + "", 245, 626, g2d);
		Tool.drawText(GameData.score + "", 295, 626, g2d);
		
	}

	@Override
	public void drawLogo(Graphics2D g2d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawButton(Graphics2D g2d) {
		Tool.drawImage(ImageData.resume_button[0], 30, 690, 180, 50, g2d);
		Tool.drawImage(ImageData.return_button[0], 270, 690, 180, 50, g2d);
		
	}

	@Override
	public void clickAction(int x, int y) {
		if (y >= 690 && y <= 740) {
			if (x >= 30 && x <= 210) {
				GameData.score = 0;
				GameData.grade = 0;
				GameLogic.prepareNewGame();
				GameData.status = RUN;
			} else if (x >= 270 && x <= 450) {
				GameData.status = MENU;
			}
		}
		
	}

	@Override
	public void doLogic() {
		// TODO Auto-generated method stub
		
	}

}
