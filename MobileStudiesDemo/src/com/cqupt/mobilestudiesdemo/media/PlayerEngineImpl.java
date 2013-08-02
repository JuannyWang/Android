package com.cqupt.mobilestudiesdemo.media;

import java.io.IOException;
import java.util.List;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Handler;
import android.util.Log;

import com.cqupt.mobilestudiesdemo.service.DownloadService;
import com.cqupt.mobilestudiesdemo.util.download.DownloadJob;
import com.cqupt.mobilestudiesdemo.util.download.DownloadJobListener;
import com.cqupt.mobilestudiesdemo.util.download.DownloadManager;

public class PlayerEngineImpl implements PlayerEngine {
	private static String TAG = "PlayerEngineImpl";
	private static int SELECTID = 0;
	public static int currentID = -1;
	private static String mediaPath;

	private class MyMusicPlayer extends MediaPlayer {
		public boolean preparing = false;
		public boolean playAfterPrepare = false;
		public Music music;
	}

	private static MyMusicPlayer musicPlayer;
	private static PlayerEngineListener mPlayerEngineListener;
	private DownloadManager downloadManager;
	private List<Music> playList;

	public PlayerEngineImpl() {
		downloadManager = DownloadService.getDownloadManager();
		mHandler = new Handler();
		Log.d(TAG, "PlayerEngineImpl");
	}

	public static PlayerEngineListener getPlayerEngineListener() {
		return mPlayerEngineListener;
	}

	@Override
	public List<Music> getPlayList() {
		// TODO Auto-generated method stub
		return playList;
	}

	@Override
	public void openPlayList(List<Music> list) {
		// TODO Auto-generated method stub
		if (list != null) {
			playList = list;
			Log.i(TAG, "not null");
		} else {
			playList = null;
			Log.i(TAG, "null");
		}
	}

	@Override
	public void play() {
		// TODO Auto-generated method stub
		/*
		 * if (SELECTID!=currentID) { stop(); }
		 */

		mediaPath = downloadManager.getResourcePath(playList.get(SELECTID)
				.getResourceEntity());
		MusicAdapter.selectPosition = SELECTID;
		if (mediaPath == null) {
			if (playList != null) {
				Log.e(TAG, "file not exist ,download");
				downLoadResource();
			}
		} else {
			if (musicPlayer == null) {
				musicPlayer = build(playList.get(SELECTID));
			}
			if (musicPlayer != null
					&& musicPlayer.music != playList.get(SELECTID)) {
				clearUp();
				musicPlayer = build(playList.get(SELECTID));
			}
			currentID = SELECTID;
			if (!musicPlayer.preparing) {
				if (!musicPlayer.isPlaying()) {
					musicPlayer.start();
					mHandler.removeCallbacks(mUpdateTimeTask);
					mHandler.postDelayed(mUpdateTimeTask, 1000);

					mPlayerEngineListener.onPlaying(playList.get(currentID));
					mPlayerEngineListener.onMusicStarted(musicPlayer);
					Log.i(TAG, "start play");
				}
			} else {
				musicPlayer.playAfterPrepare = true;
			}
		}
		Log.d(TAG, "play().." + SELECTID);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		if (musicPlayer != null) {
			mPlayerEngineListener.onMusicPause(musicPlayer.isPlaying());
			if (musicPlayer.isPlaying()) {
				musicPlayer.pause();
				if (mPlayerEngineListener != null) {
					// mPlayerEngineListener.onTrackPause();
				}
				return;
			} else {
				musicPlayer.start();
			}

		}

		Log.d(TAG, "pause()");
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub
		stop();
		if (++SELECTID < playList.size()) {
			currentID = SELECTID;
			play();
		} else {
			SELECTID = playList.size();
			currentID = SELECTID;
		}
		Log.d(TAG, "next()");
	}

	@Override
	public void previous() {
		// TODO Auto-generated method stub
		stop();
		if (--SELECTID >= 0) {
			currentID = SELECTID;
			play();
		} else {
			SELECTID = 0;
			currentID = SELECTID;
		}
		Log.d(TAG, "previous()");
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		try {
			mPlayerEngineListener.onMusicStop();
			clearUp();
		} catch (Exception e) {
			// TODO: handle exception
		}
		Log.d(TAG, "stop()");
	}

	@Override
	public void seekTo(int index) {
		// TODO Auto-generated method stub
		if (musicPlayer != null) {
			if (musicPlayer.isPlaying()) {
				musicPlayer.seekTo(index);
			} else {
				mPlayerEngineListener.onMusicPause(musicPlayer.isPlaying());
				musicPlayer.start();
				musicPlayer.seekTo(index);
			}
		}

		Log.d(TAG, "skipto()");
	}

	@Override
	public boolean isplaying() {
		// TODO Auto-generated method stub
		if (musicPlayer == null) {
			return false;
		}
		if (musicPlayer.preparing) {
			return false;
		}
		return musicPlayer.isPlaying();
	}

	@Override
	public void setListener(PlayerEngineListener playerEngineListener) {
		// TODO Auto-generated method stub
		mPlayerEngineListener = playerEngineListener;
	}

	@Override
	public void setCurrentItem(int selectID) {
		// TODO Auto-generated method stub
		SELECTID = selectID;
	}

	@Override
	public int getCurrentItem() {
		// TODO Auto-generated method stub
		return currentID;
	}

	private void clearUp() {
		if (musicPlayer != null) {
			try {
				musicPlayer.stop();
			} catch (IllegalStateException e) {
				// TODO: handle exception
			} finally {
				musicPlayer.release();
				musicPlayer = null;
			}
		}
	}

	private MyMusicPlayer build(Music music) {
		Log.d(TAG, "build()");
		final MyMusicPlayer mediaPlayer = new MyMusicPlayer();
		try {
			mediaPlayer.setDataSource(mediaPath);
			mediaPlayer.music = music;
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub
					// 播放下一首
					// 否则stop()
					next();
				}
			});
			mediaPlayer.setOnPreparedListener(new OnPreparedListener() {

				@Override
				public void onPrepared(MediaPlayer mp) {
					// TODO Auto-generated method stub
					mediaPlayer.preparing = false;
					if (mediaPlayer.playAfterPrepare) {
						mediaPlayer.playAfterPrepare = false;
						play();
					}
				}
			});
			mediaPlayer.setOnErrorListener(new OnErrorListener() {

				@Override
				public boolean onError(MediaPlayer mp, int what, int extra) {
					// TODO Auto-generated method stub
					if (what == MediaPlayer.MEDIA_ERROR_UNKNOWN) {
						if (mPlayerEngineListener != null) {
							// mPlayerEngineListener.on
							// 正在下载
						}
						stop();
						return true;
					}
					if (what == -1) {
						stop();
						return true;
					}
					return false;
				}
			});
			mediaPlayer.prepareAsync();
			mediaPlayer.preparing = true;
			return mediaPlayer;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private DownloadJobListener downloadJobListener = new DownloadJobListener() {

		@Override
		public void downloadStarted(DownloadJob job) {
			// TODO Auto-generated method stub
			// Log.i(TAG, "downloadStarted");
		}

		@Override
		public void downloadEnded(DownloadJob job) {
			// TODO Auto-generated method stub
			// job.getProgress();
			mPlayerEngineListener.onUpdateView();
			Log.i(TAG, "downloadEnd");
			if (job.getResourceEntity().getResourceID() == playList
					.get(SELECTID).getResourceEntity().getResourceID()
					&& job.getProgress() == 100) {
				play();

			} else {
				// stop();
			}

		}

		@Override
		public void downloadCanceled(DownloadJob job) {
			// TODO Auto-generated method stub

		}

		@Override
		public void progressUpdate(DownloadJob job) {
			// TODO Auto-generated method stub
		}
	};

	public void downLoadResource() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				DownloadManager downloadManager = DownloadService
						.getDownloadManager();
				DownloadJob dJob = downloadManager.download(playList.get(
						SELECTID).getResourceEntity());
				Log.i(TAG, playList.get(SELECTID).getResourceEntity() + "");
				if (dJob != null) {
					dJob.registerListener(downloadJobListener);
					playList.get(SELECTID).setDownloadJob(dJob);
					mPlayerEngineListener.onUpdateView();
					Log.i(TAG, "dJob is nuo null!");
				}
			}
		});

		Log.i("Thread.........start", "OK");
		// 设置低优先级，让UI线程优先执行
		thread.setPriority(Thread.MIN_PRIORITY);
		if (playList.get(SELECTID).getDownloadJob() == null) {
			thread.start();
		} else if (playList.get(SELECTID).getDownloadJob().getProgress() != 100) {
			thread.start();
		}

	}

	private Handler mHandler;
	private Runnable mUpdateTimeTask = new Runnable() {
		@Override
		public void run() {

			if (mPlayerEngineListener != null) {
				// TODO use getCurrentPosition less frequently (usage of
				// currentTimeMillis or uptimeMillis)
				if (musicPlayer != null) {
					if (musicPlayer.isPlaying() && musicPlayer != null) {

						mPlayerEngineListener.onTrackProgress(musicPlayer
								.getCurrentPosition());
					}
				}
				mHandler.postDelayed(this, 100);
			}
		}
	};

}
