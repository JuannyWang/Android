package ghost.picmatch.util;

import ghost.picmatch.data.GameData;
import ghost.picmatch.data.ImageData;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;

/**
 * 工具类，主要存放跟平台有关代码
 * 
 * @author ghost
 * 
 */
public class Tool {

	/**
	 * 从assets文件夹下面加载图片
	 * 
	 * @param name
	 * @return
	 */
	public static Image loadImage(String name) {
		try {
			return ImageIO.read(new File("./assets/" + name));
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("failed to load " + name);
			return null;
		}
	}

	/**
	 * 加载所有的图片资源
	 * 
	 * @return
	 */
	public static boolean loadAllImage() {
		// ///////////////////////////////////////////按钮图片
		ImageData.start_button[0] = loadImage("image/button/start1.png");
		ImageData.start_button[1] = loadImage("image/button/start2.png");

		ImageData.return_button[0] = loadImage("image/button/return1.png");
		ImageData.return_button[1] = loadImage("image/button/return2.png");

		ImageData.help_button[0] = loadImage("image/button/help1.png");
		ImageData.help_button[1] = loadImage("image/button/help2.png");

		ImageData.exit_button[0] = loadImage("image/button/exit1.png");
		ImageData.exit_button[1] = loadImage("image/button/exit2.png");

		ImageData.rank_button[0] = loadImage("image/button/rank1.png");
		ImageData.rank_button[1] = loadImage("image/button/rank2.png");

		ImageData.pause_button[0] = loadImage("image/button/pause1.png");
		ImageData.pause_button[1] = loadImage("image/button/pause2.png");

		ImageData.resume_button[0] = loadImage("image/button/resume1.png");
		ImageData.resume_button[1] = loadImage("image/button/resume2.png");

		ImageData.ad_button = loadImage("image/button/unknow.png");

		// ////////////////////////////////////背景图片
		ImageData.background[0] = loadImage("image/background/background1.png");
		ImageData.background[1] = loadImage("image/background/background2.png");
		ImageData.background[2] = loadImage("image/background/background3.png");
		ImageData.background[3] = loadImage("image/background/background4.png");
		ImageData.background[4] = loadImage("image/background/background5.png");

		// ///////////////////////////////////logo
		ImageData.logo = loadImage("image/logo/logo.png");

		// ///////////////////////////////////帮助信息
		ImageData.help_text = loadImage("image/func/help.png");

		// ///////////////////////////////////排行榜背景
		ImageData.rank_back = loadImage("image/func/rank.png");

		// ////////////////////////////////////加载图块
		ImageData.red = loadImage("image/func/red.png");
		ImageData.green = loadImage("image/func/green.png");
		ImageData.black = loadImage("image/func/black.png");
		ImageData.line = loadImage("image/func/line.png");

		for (int i = 1; i <= 57; ++i) {
			String filename = "image/pics/" + i + ".png";
			ImageData.pics[i - 1] = loadImage(filename);
		}

		// ////////////////////////////////////计分板
		ImageData.score_display = loadImage("image/func/score.png");
		return true;
	}

	/**
	 * 在屏幕指定绘制绘制图像
	 * 
	 * @param image
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param g2d
	 */
	public static void drawImage(Image image, int x, int y, int w, int h,
			Graphics2D g2d) {
		if (image == null) {
			System.err.println("image is null!");
			return;
		}
		g2d.drawImage(image, (int) (x * GameData.scaleW),
				(int) (y * GameData.scaleH), (int) (w * GameData.scaleW),
				(int) (h * GameData.scaleH), null);
	}

	/**
	 * 在屏幕指定位置绘制文字
	 * 
	 * @param text
	 * @param x
	 * @param y
	 * @param g2d
	 */
	public static void drawText(String text, int x, int y, Graphics2D g2d) {
		g2d.setFont(new Font("宋体", Font.BOLD, 14));
		g2d.drawString(text, (int) (x * GameData.scaleW),
				(int) (y * GameData.scaleH));
	}

	/**
	 * 获得当前排行榜
	 * 
	 * @return
	 */
	public static int[] getScoreList() {
		int[] result = new int[8];
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("./assets/data/data"));
			for (int i = 0; i < 8; ++i) {
				result[i] = Integer.parseInt(prop.getProperty("" + (i + 1)));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 更新分数排行榜
	 * 
	 * @param score
	 */
	@SuppressWarnings("deprecation")
	public static void updateScoreList(int score) {
		int[] result = new int[8];
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("./assets/data/data"));
			for (int i = 0; i < 8; ++i) {
				result[i] = Integer.parseInt(prop.getProperty("" + (i + 1)));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < 8; ++i) {
			if (result[i] < score) {
				for (int j = 7; j > i; --j) {
					result[j] = result[j - 1];
				}
				result[i] = score;
				break;
			}
		}
		for (int i = 0; i < 8; ++i) {
			prop.setProperty("" + (i + 1), result[i] + "");
		}
		try {
			prop.save(new FileOutputStream("./assets/data/data"), "auto list");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
