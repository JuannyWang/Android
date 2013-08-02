package com.cqupt.mobilestudiesdemo.media;

import android.media.MediaPlayer;

public interface PlayerEngineListener {
/*	public boolean onTrackStart();
	public void onTrackChanged(Music music);
	public void onTrackStop();
	public void onTrackPause();*/
	public boolean onStart();
	public int onMusicStarted(MediaPlayer mPlayer);
	public int onMusicPause(boolean playing);
	public int onMusicStop();
	public void onTrackProgress(int seconds);
	public void onPlaying(Music music);
	public void onUpdateView();
	public void onSeekTo();
}
