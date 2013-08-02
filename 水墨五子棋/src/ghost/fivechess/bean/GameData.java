/**
 * 
 */
package ghost.fivechess.bean;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 游戏数据类，包含游戏中需要的所有公用数据
 * @author 玄雨
 * @qq 821580467
 * @date 2013-1-19
 */
abstract public class GameData {
	/**
	 * 作者
	 */
	public static String author="玄雨";
	/**
	 * 游戏名称
	 */
	public static String gameName="水墨五子棋";
	/**
	 * 游戏绘制画笔
	 */
	public static Canvas canvas;
	public static Paint paint;
	/**
	 * 是否开启调试模式
	 */
	public static boolean debugModel;
	/**
	 * 屏幕实际大小
	 */
	public static double screenWidth,screenHeight;
	public static final int width=800,height=480;
	/**
	 * 绘制时的缩放比例
	 */
	public static float scaleWidth,scaleHeight;
	/**
	 * 游戏状态
	 */
	public static int status;
	/**
	 * 绘制标志位
	 */
	public static boolean flag;
	/**
	 * 游戏帧率控制器
	 */
	public static int fps;
	/**
	 * 游戏动画移动速度
	 */
	public static int moveSpeedIn,moveSpeedOut;
	/**
	 * 全局上下文
	 */
	public static Context context;
}
