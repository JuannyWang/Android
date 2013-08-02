package ghost.picmatch.logic;

import ghost.picmatch.data.GameData;
import ghost.picmatch.data.ImageData;
import ghost.picmatch.util.Tool;

import java.awt.Color;
import java.awt.Graphics2D;

public class GameRank extends GameObject {

	private int list[];

	public void loadScoreList() {
		list = Tool.getScoreList();
	}

	@Override
	public void drawBackground(Graphics2D g2d) {
		Tool.drawImage(ImageData.background[0], 0, 0, GameData.width,
				GameData.height, g2d);

	}

	@Override
	public void drawFunc(Graphics2D g2d) {
		Tool.drawImage(ImageData.rank_back, 40, 200, 400, 460, g2d);
		g2d.setColor(Color.orange);
		Tool.drawText("No.1:" + list[0], 170, 300, g2d);
		g2d.setColor(Color.red);
		Tool.drawText("No.2:" + list[1], 170, 340, g2d);
		g2d.setColor(Color.green);
		Tool.drawText("No.3:" + list[2], 170, 380, g2d);
		g2d.setColor(Color.BLACK);
		Tool.drawText("No.4:" + list[3], 170, 420, g2d);
		g2d.setColor(Color.gray);
		Tool.drawText("No.5:" + list[4], 170, 460, g2d);
		Tool.drawText("No.6:" + list[5], 170, 500, g2d);
		Tool.drawText("No.7:" + list[6], 170, 540, g2d);
		Tool.drawText("No.8:" + list[7], 170, 580, g2d);
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
