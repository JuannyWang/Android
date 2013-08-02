package com.cqupt.mobilestudiesdemo.media;

import java.util.List;

public interface PlayerEngine {
	public List<Music> getPlayList();
	public void openPlayList(List<Music> list);
	public void play();
	public void next();
	public void pause();
	public void previous();
	public void stop();
	public void seekTo(int index);
	public boolean isplaying();
	public void setListener(PlayerEngineListener playerEngineListener);
	public int getCurrentItem();
	public void setCurrentItem(int selectID);
}
