/**
 * 
 */
package ghost.fivechess.util;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint.Style;
import ghost.fivechess.bean.GameData;
import ghost.fivechess.bean.ImageFactory;

/**
 * 游戏工具类
 * @author 玄雨
 * @qq 821580467
 * @date 2013-1-19
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
		Bitmap temp = ImageFactory.getImageByName(imageName);
		if (temp == null) {
			if (GameData.debugModel) {
				int tempColor = GameData.paint.getColor();
				GameData.paint.setColor(Color.RED);
				GameData.canvas.drawRect((startX * GameData.scaleWidth),
						(startY * GameData.scaleHeight),
						(startX * GameData.scaleWidth)
								+ (width * GameData.scaleWidth),
						(startY * GameData.scaleHeight)
								+ (height * GameData.scaleHeight),
						GameData.paint);
				GameData.paint.setColor(tempColor);
			}
			return;
		}
		temp = Bitmap.createScaledBitmap(temp,
				(int) (width * GameData.scaleWidth),
				(int) (height * GameData.scaleHeight), true);
		GameData.canvas.drawBitmap(temp, (int) (startX * GameData.scaleWidth),
				(int) (startY * GameData.scaleHeight), GameData.paint);
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
		int tempColor = GameData.paint.getColor();
		GameData.paint.setColor(Color.RED);
		GameData.paint.setStyle(Style.STROKE);
		GameData.canvas.drawRect((x * GameData.scaleWidth),
				(y * GameData.scaleHeight), (x * GameData.scaleWidth)
						+ (32 * GameData.scaleWidth),
				(y * GameData.scaleHeight) + (32 * GameData.scaleHeight),
				GameData.paint);
		GameData.paint.setColor(tempColor);
	}
}
