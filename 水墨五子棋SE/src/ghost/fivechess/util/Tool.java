/**
 * 
 */
package ghost.fivechess.util;

import java.awt.Color;
import java.awt.Image;

import ghost.fivechess.bean.GameData;
import ghost.fivechess.bean.ImageFactory;

/**
 * 工具辅助类，与平台相关
 * 
 * @author 玄雨
 * @qq 821580467
 * @date 2013-1-18
 */
abstract public class Tool {
	/**
	 * 自动根据传入参数缩放图片并绘制到指定位置
	 * 
	 * @param imageName
	 * @param startX
	 * @param startY
	 * @param width
	 * @param height
	 */
	public static void drawImage(String imageName, int startX, int startY,
			int width, int height) {
		Image temp = ImageFactory.getImageByName(imageName);
		if (temp == null) {
			if (GameData.debugModel) {
				Color tempColor = GameData.g2d.getColor();
				GameData.g2d.setColor(Color.red);
				GameData.g2d.fillRect((int) (startX * GameData.scaleWidth),
						(int) (startY * GameData.scaleHeight),
						(int) (width * GameData.scaleWidth),
						(int) (height * GameData.scaleHeight));
				GameData.g2d.setColor(tempColor);
			}
			return;
		}
		GameData.g2d.drawImage(ImageFactory.getImageByName(imageName),
				(int) (startX * GameData.scaleWidth),
				(int) (startY * GameData.scaleHeight),
				(int) (width * GameData.scaleWidth),
				(int) (height * GameData.scaleHeight), null);
	}

	/**
	 * 在对应的方格上画上一个框
	 * 
	 * @param m
	 * @param n
	 */
	public static void drawRect(int m, int n) {
		int x = 20 + m * 32;
		int y = 2 + n * 32;
		Color tempColor = GameData.g2d.getColor();
		GameData.g2d.setColor(Color.red);
		GameData.g2d.drawRect((int) (x * GameData.scaleWidth),
				(int) (y * GameData.scaleHeight),
				(int) (32 * GameData.scaleWidth),
				(int) (32 * GameData.scaleHeight));
		GameData.g2d.setColor(tempColor);
	}
}
