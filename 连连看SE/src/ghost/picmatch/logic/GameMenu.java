package ghost.picmatch.logic;

import java.awt.Graphics2D;

import ghost.picmatch.data.GameData;
import ghost.picmatch.data.ImageData;
import ghost.picmatch.util.GameObjectFactory;
import ghost.picmatch.util.Tool;

public class GameMenu extends GameObject {

	@Override
	public void drawBackground(Graphics2D g2d) {
		Tool.drawImage(ImageData.background[0], 0, 0, GameData.width,
				GameData.height, g2d);
	}

	@Override
	public void drawFunc(Graphics2D g2d) {
		Tool.drawImage(ImageData.ad_button, 40, 170, 400, 125, g2d);

	}

	@Override
	public void drawLogo(Graphics2D g2d) {
		Tool.drawImage(ImageData.logo, 65, 65, 350, 80, g2d);

	}

	@Override
	public void drawButton(Graphics2D g2d) {
		Tool.drawImage(ImageData.start_button[0], 140, 350, 200, 44, g2d);
		Tool.drawImage(ImageData.help_button[0], 140, 430, 200, 44, g2d);
		Tool.drawImage(ImageData.rank_button[0], 140, 510, 200, 44, g2d);
		Tool.drawImage(ImageData.exit_button[0], 140, 590, 200, 44, g2d);
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
