package ghost.picmatch.logic;

import ghost.picmatch.data.GameData;
import ghost.picmatch.data.ImageData;
import ghost.picmatch.util.Tool;

import java.awt.Graphics2D;

public class GameHelp extends GameObject {

	@Override
	public void drawBackground(Graphics2D g2d) {
		Tool.drawImage(ImageData.background[0], 0, 0, GameData.width,
				GameData.height, g2d);
		
	}

	@Override
	public void drawFunc(Graphics2D g2d) {
		Tool.drawImage(ImageData.help_text, 40, 200, 400, 457, g2d);
		
	}

	@Override
	public void drawLogo(Graphics2D g2d) {
		Tool.drawImage(ImageData.logo, 65, 65, 350, 80, g2d);
		
	}

	@Override
	public void drawButton(Graphics2D g2d) {
		Tool.drawImage(ImageData.return_button[0], 270, 690, 180, 50, g2d);
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
