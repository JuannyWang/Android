package com.cqupt.mobilestudiesdemo.media;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class MusicService extends Service implements Runnable{
	private static String TAG="MusicService";
	public static final String ACTION_PLAY = "play";
	public static final String ACTION_NEXT = "next";
	public static final String ACTION_PREV = "prev";
	public static final String ACTION_STOP = "stop";
	public static final String ACTION_PAUSE = "pause";
	public static final String ACTION_BIND_LISTENER="bind_listener";
	
	private static List<Music> list;
	private static List<Music> list1;
	public LrcProcess mLrcProcess;
	public static int currentItem;
	public static PlayerEngine mPlayerEngine;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		mPlayerEngine=MusicActivity.mPlayerEngine;
		
		//mPlayerEngine.setListener(mLocalEngineListener);

//		list=MusicActivity.mediaList;
//		Music mm = new Music();
//		mm = list.get(4);
//		Log.v("MusicList",mm.getResourceEntity().getResourcePath());
//		//mPlayerEngine.openPlayList(list);
		super.onCreate();
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		try {
			Log.v(TAG, "开始获取");
			list1 = (List<Music>) Intent.getIntent("MusicList");
			Log.v(TAG, "获取成功");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		currentItem=intent.getIntExtra("selectID",0);
		mPlayerEngine.setCurrentItem(currentItem);
		Log.v("Service Che ", currentItem +"");

		String action = intent.getAction();	
		if(action.equals(ACTION_STOP)){
			stopSelfResult(startId);
		}		
		if(action.equals(ACTION_PLAY)){	
			if (mPlayerEngine.getCurrentItem()!=currentItem) {
				mPlayerEngine.stop();
			}
			mPlayerEngine.play();
		}
		
		if(action.equals(ACTION_NEXT)){	
			mPlayerEngine.next();
		}
		
		if(action.equals(ACTION_PREV)){	
			mPlayerEngine.previous();
		}
		if (action.equals(ACTION_PAUSE)) {
			mPlayerEngine.pause();
		}
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		//MusicManager.getInstance().setConcretePlayerEngine(null);
		currentItem=0;
		mPlayerEngine.stop();
		mPlayerEngine=null;
		super.onDestroy();
	}
}
