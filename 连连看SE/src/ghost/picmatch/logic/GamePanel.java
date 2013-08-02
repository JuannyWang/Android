package ghost.picmatch.logic;

import ghost.picmatch.data.GameData;
import ghost.picmatch.data.ImageData;
import ghost.picmatch.data.Line;
import ghost.picmatch.util.Tool;

import java.awt.Color;
import java.awt.Graphics2D;

public class GamePanel extends GameObject {

	@Override
	public void drawBackground(Graphics2D g2d) {
		Tool.drawImage(ImageData.background[0], 0, 0, GameData.width,
				GameData.height, g2d);
	}

	@Override
	public void drawFunc(Graphics2D g2d) {
		g2d.setColor(Color.white);
		Tool.drawText("时间:", 45, 130, g2d);
		Tool.drawImage(ImageData.red, 110, 120, 320, 10, g2d);
		Tool.drawImage(
				ImageData.green,
				110,
				120,
				(int) (320 * (float) GameData.time / (float) GameData.max_time),
				10, g2d);
		int x = 30, y = 150;
		int key = -1;
		int degree = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 7; ++j) {
				key = GameData.map[i][j];
				if (key != -1) {
					degree = GameData.mapvalue[i][j][0];
					g2d.translate((x + j * 60 + 30) * GameData.scaleW, (y + i
							* 60 + 30)
							* GameData.scaleH);
					g2d.rotate((double) degree / 180 * Math.PI);
					if (GameData.mapvalue[i][j][2] == 1) {
						Tool.drawImage(ImageData.black, -30, -30, 55, 55, g2d);
					}
					Tool.drawImage(ImageData.pics[key], -30, -30, 55, 55, g2d);
					g2d.rotate(-(double) degree / 180 * Math.PI);
					g2d.translate(-(x + j * 60 + 30) * GameData.scaleW, -(y + i
							* 60 + 30)
							* GameData.scaleH);
				}
			}
		}
		Tool.drawImage(ImageData.score_display, 140, 640, 200, 40, g2d);
		g2d.setColor(Color.gray);
		Tool.drawText("" + GameData.grade, 245, 666, g2d);
		Tool.drawText("" + GameData.score, 295, 666, g2d);
		for (int i = 0; i < GameData.lines.size(); ++i) {
			Line temp = GameData.lines.get(i);
			if (temp.life > 0) {
				temp.drawSelf(g2d);
			} else {
				GameData.lines.remove(temp);
			}
		}
	}

	@Override
	public void drawLogo(Graphics2D g2d) {
		Tool.drawImage(ImageData.logo, 65, 10, 350, 80, g2d);

	}

	@Override
	public void drawButton(Graphics2D g2d) {
		Tool.drawImage(ImageData.pause_button[0], 30, 690, 180, 50, g2d);
		Tool.drawImage(ImageData.return_button[0], 270, 690, 180, 50, g2d);

	}

	@Override
	public void clickAction(int x, int y) {
		if (y >= 690 && y <= 740) {
			if (x >= 30 && x <= 210) {
				GameData.status = PAUSE;
			} else if (x >= 270 && x <= 450) {
				// Tool.drawImage(ImageData.return_button[1], 270, 690, 180,
				// 50, (Graphics2D) this.getGraphics());
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
			Tool.updateScoreList(GameData.score);
			GameData.status = LOSS;
		}

	}

}
