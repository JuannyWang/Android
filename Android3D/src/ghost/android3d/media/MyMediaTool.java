/**
 * 
 */
package ghost.android3d.media;

import java.io.IOException;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

/**
 * 音乐播放类
 * @author 玄雨
 * @qq 821580467
 * @date 2013-1-23
 */
abstract public class MyMediaTool {
	/**
	 * 暂停标志位
	 */
	private static boolean pauseFlag;
	/**
	 * 音乐播放器
	 */
	private static MediaPlayer mp;
	/**
	 * 音频管理工具
	 */
	private static AudioManager am;
	
	/**
	 * 初始化
	 * @param context 上下文参数
	 */
	@SuppressWarnings("static-access")
	public static void initialization(Context context) {
		mp=new MediaPlayer();
		am=(AudioManager) context.getSystemService(context.AUDIO_SERVICE);
	}
	
	/**
	 * 播放音乐
	 * @param filePath 音乐文件位置
	 * @return 播放成功返回true否则返回false
	 */
	public static boolean play(String filePath) {
		try {
			mp.setDataSource(filePath);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return false;
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		try {
			mp.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		mp.start();
		return true;
	}
	
	/**
	 * 暂停播放，如果已经暂停则继续播放
	 */
	public static void pause() {
		if(mp.isPlaying()) {
			mp.pause();
			pauseFlag=true;
		} else if(pauseFlag) {
			mp.start();
			pauseFlag=false;
		}
	}
	
	/**
	 * 停止播放，并且重置
	 */
	public static void stop() {
		mp.stop();
		mp.reset();
	}
	
	/**
	 * 增大音量
	 */
	public static void addVolumn() {
		am.adjustVolume(AudioManager.ADJUST_RAISE, 0);
	}
	
	/**
	 * 减小音量
	 */
	public static void downVolumn() {
		am.adjustVolume(AudioManager.ADJUST_LOWER, 0);
	}
	
}
