package com.cqupt.mobilestudiesdemo.media;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.cqupt.mobilestudiesdemo.activity.R;
import com.cqupt.mobilestudiesdemo.db.DBResourceImpl;
import com.cqupt.mobilestudiesdemo.dialog.LoadingDialog;
import com.cqupt.mobilestudiesdemo.entity.ResourceEntity;
import com.cqupt.mobilestudiesdemo.entity.WebServiceApi;
import com.cqupt.mobilestudiesdemo.entity.impl.WebServiceApiImpl;
import com.cqupt.mobilestudiesdemo.service.DownloadService;
import com.cqupt.mobilestudiesdemo.util.download.DownloadManager;

public class MusicActivity extends Activity implements OnClickListener {
	private static String TAG = "MusicActivity";
	public static MusicActivity musicActivity;

	private ImageButton playButton;
	private ImageButton preButton;
	private ImageButton nextButton;
	private ListView mediaListView;
	private SeekBar proSeekBar;
	private EnLrcView enLrcView;
	private ChLrcView chLrcView;
	private ChEnLrcView chEnLrcView;
	private TextView currentTime;
	private TextView durationTime;
	private TextView groupName;
	public static CheckBox showLrc;

	private static int CURRENTITEM = 0;
	public static List<Music> mediaList;
	private List<LrcContent> eNLrcList = new ArrayList<LrcContent>();
	private List<LrcContent> cHLrcList=new ArrayList<LrcContent>();

	private Handler handler;
	private DownloadManager downloadManager;
	public static PlayerEngine mPlayerEngine;
	private DataInfo mDataInfo;
	private ActionBar mActionBar;
	private ViewPager mViewPager;
	private MusicAdapter adapter;
	private LrcProcess mLrcProcess;
	private MediaPlayer mediaPlayer;
	int CurrentTime = 0;
	int CountTime = 0;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.music_player_layout);
		initView();// 初始化控件

		musicActivity = this;
		/* 得到需要加载的resourceID */
		Intent intent = getIntent();
		Bundle mBundle = intent.getExtras();
		mDataInfo = new DataInfo();
		mediaList = new ArrayList<Music>();
		mediaList = (List<Music>) mBundle.getSerializable("MusicList");
		mDataInfo.resourceID = mBundle.getInt("selectID");
//		groupName.setText("外语听力学习："+mBundle.getString("resourceGroupName"));

		NetDataLoad netDataLoad = new NetDataLoad(this);
		netDataLoad.execute(0);
		handler = new Handler();
		downloadManager = DownloadService.getDownloadManager();
		mPlayerEngine = new PlayerEngineImpl();
		mPlayerEngine.openPlayList(mediaList);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub

		mPlayerEngine.setListener(mPlayerEngineListener);
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		MusicAdapter.selectPosition = -1;
		Intent intent = new Intent(MusicActivity.this, MusicService.class);
		stopService(intent);
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.imgbtn_play) {
			// startAction(MusicService.ACTION_PLAY);
			if (mPlayerEngine != null) {
				mPlayerEngine.pause();
			}
		} else if (id == R.id.imgbtn_next) {
			// startAction(MusicService.ACTION_NEXT);
			if (mPlayerEngine != null) {
				mPlayerEngine.next();
			}
		} else if (id == R.id.imgbtn_previous) {
			// startAction(MusicService.ACTION_PREV);
			if (mPlayerEngine != null) {
				mPlayerEngine.previous();
			}
		} else {
		}
	}







	private void initView() {

		playButton = (ImageButton) findViewById(R.id.imgbtn_play);
		nextButton = (ImageButton) findViewById(R.id.imgbtn_next);
		preButton = (ImageButton) findViewById(R.id.imgbtn_previous);
		proSeekBar = (SeekBar) findViewById(R.id.proseekbar);
		enLrcView = (EnLrcView) findViewById(R.id.txtview_enLrc);
		chLrcView=(ChLrcView)findViewById(R.id.txtview_chLrc);
		chEnLrcView=(ChEnLrcView)findViewById(R.id.txtview_chenLrc);
		currentTime = (TextView) findViewById(R.id.txtview_currenttime);
		durationTime = (TextView) findViewById(R.id.txtview_totaltime);
		showLrc = (CheckBox) findViewById(R.id.checkbox_lrc);
		//groupName=(TextView)findViewById(R.id.txtview_group);
		mViewPager=(ViewPager)findViewById(R.id.viewpager);
		//mPagerTitleStrip=(TitlePageIndicator)findViewById(R.id.pagertitle);
		
		
		playButton.setOnClickListener(this);
		nextButton.setOnClickListener(this);
		preButton.setOnClickListener(this);
		proSeekBar.setOnSeekBarChangeListener(new seekBarPositionChangeListener());
		
		

		adapter = new MusicAdapter(this, mediaList);

		
		//将要分页显示的View装入数组中
        LayoutInflater mLi = LayoutInflater.from(this);
        View view1 = mLi.inflate(R.layout.layout_enlrc, null);
        View view2 =mLi.inflate(R.layout.layout_chlrc, null);
        
        //每个页面的Title数据
        final ArrayList<View> views = new ArrayList<View>();
        views.add(view1);
        views.add(view2);
        
        final ArrayList<String> titles = new ArrayList<String>();
        titles.add("英文字幕");
        titles.add("中英文字幕");
        List<Integer > list =new ArrayList<Integer>();
        list.add(R.id.txtview_enLrc);
        list.add(R.id.txtview_chLrc);
        //lrcViewPaperAdapter=new LrcViewPaperAdapter(MusicActivity.this);
        //填充ViewPager的数据适配器
        PagerAdapter mPagerAdapter = new PagerAdapter() {
			
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == (View)arg1;
			}
			
			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public int getItemPosition(Object object) {
				// TODO Auto-generated method stub
				return POSITION_NONE;
			}
			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager)container).removeView(views.get(position));
			}

			@Override
			public CharSequence getPageTitle(int position) {
				return titles.get(position);
			}

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager)container).addView(views.get(position));
				return views.get(position);
			}
		};
		
		mViewPager.setAdapter(mPagerAdapter);
		//mPagerTitleStrip.setViewPager(mViewPager);
	}

	class listItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Log.d(TAG + "  listItemClick", "OK");
			CURRENTITEM = arg2;
			startAction(MusicService.ACTION_PLAY);
		}

	}

	class seekBarPositionChangeListener implements OnSeekBarChangeListener {

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			try {
				if (mPlayerEngine != null) {
					mPlayerEngine.seekTo(seekBar.getProgress());
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

	private void startAction(String action) {
		Intent intent = new Intent(MusicActivity.this, MusicService.class);
		intent.setAction(action);
		intent.putExtra("selectID", CURRENTITEM);
		startService(intent);
	}

	private PlayerEngineListener mPlayerEngineListener = new PlayerEngineListener() {
		Music mMusic;

		@Override
		public void onTrackProgress(int seconds) {
			// TODO Auto-generated method stub
			proSeekBar.setProgress(seconds);
			currentTime.setText(toTime(seconds));
			switch (mViewPager.getCurrentItem()) {
			case 0:
				enLrcView.SetIndex(LrcIndex());
				enLrcView.invalidate();
				break;
			case 1:
				chLrcView.SetIndex(LrcIndex());
				chLrcView.invalidate();
				break;
			default:
				break;
			}
		}

		@Override
		public boolean onStart() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public int onMusicStop() {
			// TODO Auto-generated method stub
			currentTime.setText("00:00");
			durationTime.setText("00:00");
			proSeekBar.setProgress(0);
			playButton.setImageResource(R.drawable.pause_selecor);
			onUpdateView();
			return 0;
		}

		@Override
		public int onMusicStarted(MediaPlayer mPlayer) {
			// TODO Auto-generated method stub
			handler.post(mUpdateTimeTask);

			durationTime.setText(toTime(mPlayer.getDuration()));
			proSeekBar.setProgress(0);
			proSeekBar.setMax(mPlayer.getDuration());
			mediaPlayer = mPlayer;

			// 开始加载字幕
			mLrcProcess = new LrcProcess(mMusic);
			boolean lrcresult = mLrcProcess.readLRC(new LrcDownloadListener() {

				@Override
				public void progressUpdate(Music music) {
					// TODO Auto-generated method stub

				}

				@Override
				public void downloadStarted(Music music) {
					// TODO Auto-generated method stub
					eNLrcList = mLrcProcess.getEnLrcContent();
					cHLrcList = mLrcProcess.getChLrcContent();
					enLrcView.setmEnSentenceEntities(eNLrcList);
					chLrcView.setmChSentenceEntities(cHLrcList);
					chLrcView.setmEnSentenceEntities(eNLrcList);
				}

				@Override
				public void downloadEnded(Music music, boolean bool) {
					// TODO Auto-generated method stub
					if (music.getResourceEntity().getResourceID() == mMusic
							.getResourceEntity().getResourceID()) {
						if (mLrcProcess.readLRC(this)) {
							eNLrcList = mLrcProcess.getEnLrcContent();
							cHLrcList=mLrcProcess.getChLrcContent();
							enLrcView.setmEnSentenceEntities(eNLrcList);
							chLrcView.setmEnSentenceEntities(eNLrcList);
							chLrcView.setmChSentenceEntities(cHLrcList);
						}
					}
				}

				@Override
				public void downloadCanceled(Music music) {
					// TODO Auto-generated method stub

				}
			});

			if (lrcresult) {
				eNLrcList = mLrcProcess.getEnLrcContent();
				cHLrcList = mLrcProcess.getChLrcContent();
				enLrcView=(EnLrcView)findViewById(R.id.txtview_enLrc);
				chLrcView=(ChLrcView)findViewById(R.id.txtview_chLrc);
				enLrcView.setmEnSentenceEntities(eNLrcList);
				chLrcView.setmChSentenceEntities(cHLrcList);
				chLrcView.setmEnSentenceEntities(eNLrcList);
			}

			return 0;
		}

		@Override
		public int onMusicPause(boolean playing) {
			// TODO Auto-generated method stub
			if (playing) {
				playButton.setImageResource(R.drawable.pause_selecor);
			} else {
				playButton.setImageResource(R.drawable.play_selecor);// 当联网失败时会崩溃
			}
			Log.i(TAG, playing + "");
			return 0;
		}

		@Override
		public void onPlaying(Music music) {
			// playingMusicTitle.setText(music.getResourceEntity().getResourceTitle());
			playButton.setImageResource(R.drawable.pause_selecor);
			mMusic = music;
		};

		@Override
		public void onUpdateView() {
			handler.post(mUpdateTimeTask);
		};

		public int LrcIndex() {
			int index = 0;
			try {
				if (mediaPlayer.isPlaying() && mediaPlayer != null) {
					CurrentTime = mediaPlayer.getCurrentPosition();
					CountTime = mediaPlayer.getDuration();
				}
				if (CurrentTime < CountTime) {

					for (int i = 0; i < eNLrcList.size(); i++) {
						if (i < eNLrcList.size() - 1) {
							if (CurrentTime < eNLrcList.get(i).getLrcTime()
									&& i == 0) {
								index = i;
							}
							if (CurrentTime > eNLrcList.get(i).getLrcTime()
									&& CurrentTime < eNLrcList.get(i + 1)
											.getLrcTime()) {
								index = i;
							}
						}
						if (i == eNLrcList.size() - 1
								&& CurrentTime > eNLrcList.get(i).getLrcTime()) {
							index = i;
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			return index;
		}

		@Override
		public void onSeekTo() {
			// TODO Auto-generated method stub

		}
	};

	/**
	 * 加载网络数据
	 * 
	 * @author ap
	 * 
	 */
	private class NetDataLoad extends LoadingDialog<Object, Integer> {
		private ArrayList<ResourceEntity> mResourceEntities;

		public NetDataLoad(Activity activity) {
			super(activity);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Integer doInBackground(Object... params) {
			// TODO Auto-generated method stub

			/**
			 * 获取网络数据
			 */
			WebServiceApi webServiceApi = new WebServiceApiImpl(this.mActivity);
			int flagID = -1;
			flagID = webServiceApi.GetResource(mDataInfo.resourceGroupID);
			/**
			 * 加载本地数据
			 */
			DBResourceImpl db = new DBResourceImpl(this.mActivity);
			/**
			 * 初始化数据库查询位置
			 */
			mDataInfo.dataIndex = 0;
			mDataInfo.dataSize = 20;

			mResourceEntities = db
					.getAllResourcesFromRG(mDataInfo.resourceGroupID);

			return flagID;
		}

		@Override
		public void doStuffWithResult(Integer result) {
			// TODO Auto-generated method stub
			/**
			 * 校验数据并通知更新
			 */
			if (mResourceEntities != null) {
				for (ResourceEntity resourceEntity : mResourceEntities) {
					Music music = new Music();
					music.setResourceEntity(resourceEntity);
					mediaList.add(music);
				}
			}
			handler.post(mUpdateTimeTask);
		}
	}

	static final class DataInfo {
		private ArrayList<ResourceEntity> resourceEntities;
		private int resourceGroupID = -1;
		private int resourceID = -1;
		/**
		 * 每次请求数据库index、size
		 */
		private int dataIndex = 0;
		private int dataSize = 20;
	}

	private Runnable mUpdateTimeTask = new Runnable() {
		@Override
		public void run() {
			adapter.notifyDataSetChanged();
		}
	};

	/**
	 * 将字幕文件中的时间与播放器进度进行相互转换
	 * 
	 * @param time
	 * @return
	 */
	public String toTime(int time) {
		time /= 1000;
		int minute = time / 60;
		int hour = minute / 60;
		int second = time % 60;
		minute %= 60;
		return String.format("%02d:%02d", minute, second);
	}
}
