/**
 * 
 */
package ghost.fivechess.bean;

import java.io.IOException;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 图片工厂类，提供读取图片和获得图片操作
 * @author 玄雨
 * @qq 821580467
 * @date 2013-1-19
 */
abstract public class ImageFactory {
	private static HashMap<String, Bitmap> data = new HashMap<String, Bitmap>();

	/**
	 * 从加载好的资源中直接读取图片
	 * 
	 * @param imageName
	 *            图片名称
	 * @return 如果存在则返回该图片，否则返回null并打印相关信息
	 */
	public static Bitmap getImageByName(String imageName) {
		Bitmap temp = null;
		temp = data.get(imageName);
		if (temp == null) {
			if (GameData.debugModel)
				System.err.println("you need to load " + imageName + " first!");
		}
		return temp;
	}

	/**
	 * 从assets文件夹下加载相应图片
	 * 
	 * @param fileName
	 *            文件准确位置
	 * @param imageName
	 *            图片名称
	 * @return 如果加载成功则返回true否则返回false并打印相关错误信息
	 */
	public static boolean loadImageFromAssets(String fileName, String imageName) {
		Bitmap temp = null;
		try {
			temp = BitmapFactory.decodeStream(GameData.context.getResources()
					.getAssets().open(fileName));
		} catch (IOException e) {
			if (GameData.debugModel)
				System.err.println("Image:" + fileName
						+ " does not exist! name: " + imageName);
			return false;
		}
		data.put(imageName, temp);
		return true;
	}

	/**
	 * 未启用
	 * 
	 * @return
	 */
	public static boolean loadImageFromeUrl() {
		return true;
	}
}

