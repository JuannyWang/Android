/**
 * 
 */
package ghost.android3d.audio;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * 音效播放工具类
 * 
 * @author 玄雨
 * @qq 821580467
 * @date 2013-1-23
 */
abstract public class MyAudioTool {
	/**
	 * 音频池
	 */
	private static SoundPool sp;
	/**
	 * 音频文件映射表
	 */
	private static HashMap<Integer, Integer> spMap;

	/**
	 * 初始化音频池
	 * 
	 * @param maxStream
	 *            最大可播放数
	 */
	@SuppressLint("UseSparseArrays")
	public static void initialization(int maxStream) {
		if (sp != null) {
			sp.release();
		}
		sp = new SoundPool(maxStream, AudioManager.STREAM_MUSIC, 0);
		spMap = new HashMap<Integer, Integer>();
	}

	/**
	 * 加载音频
	 * 
	 * @param context
	 *            上下文参数
	 * @param key
	 *            音频在音频池中的序号
	 * @param value
	 *            音频的资源ID定位
	 */
	public static void loadAudio(Context context, int key, int value) {
		spMap.put(key, sp.load(context, value, 1));
	}

	/**
	 * 播放
	 * 
	 * @param context
	 *            上下文参数
	 * @param key
	 *            需要播放的音频的序号
	 * @param loop
	 *            是否循环，0表示不循环 -1表示无限循环
	 */
	public static void playAudio(Context context, int key, int loop) {
		@SuppressWarnings("static-access")
		AudioManager am = (AudioManager) context
				.getSystemService(context.AUDIO_SERVICE);
		float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float audioCurrentVolumn = am
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		float volumnRatio = audioCurrentVolumn / audioMaxVolumn;
		/**
		 * 第一个参数为音频，第二个为左声道，第三个右声道， 第三个表示优先级，0为最低，第四个表示是否循环 0表示不循环，-1表示一直循环
		 * 第五个表示播放速度 1 为正常速度
		 */
		sp.play(spMap.get(key), volumnRatio, volumnRatio, 1, loop, 1);
	}

	/**
	 * 暂停
	 * 
	 * @param key
	 *            需要暂停的音频的序号
	 */
	public static void pause(int key) {
		sp.pause(spMap.get(key));
	}
}
