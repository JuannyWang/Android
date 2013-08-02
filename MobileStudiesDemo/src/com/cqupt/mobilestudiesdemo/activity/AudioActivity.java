package com.cqupt.mobilestudiesdemo.activity;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.cqupt.mobilestudiesdemo.db.DBResourceImpl;
import com.cqupt.mobilestudiesdemo.dialog.LoadingDialog;
import com.cqupt.mobilestudiesdemo.entity.ResourceEntity;
import com.cqupt.mobilestudiesdemo.entity.WebServiceApi;
import com.cqupt.mobilestudiesdemo.entity.impl.WebServiceApiImpl;
import com.cqupt.mobilestudiesdemo.listener.ContextMenuListener;
import com.cqupt.mobilestudiesdemo.media.Music;
import com.cqupt.mobilestudiesdemo.media.MusicActivity;
import com.cqupt.mobilestudiesdemo.media.MusicAdapter;
import com.cqupt.mobilestudiesdemo.media.MusicService;
import com.cqupt.mobilestudiesdemo.util.MoveBg;
import com.cqupt.mobilestudiesdemo.util.download.DownloadManager;

public class AudioActivity extends Activity {
	RelativeLayout layout;
	TextView tv_front;// 需要移动的View
	private static int CURRENTITEM = 0;
	private int index;
	private String path, cet4Path, cet6Path, ieltsPath, toeflPath,
			moreAudioPath;
	private ListView mListView;
	private MusicAdapter adapter;
	private Handler handler;
	public  List<Music> listView;
	private DownloadManager downloadManager;
	public static String AudioFolder = "LanguageLearning/Audio";

	
	private final static String AUDIO_PATH = Environment
			.getExternalStorageDirectory() + File.separator + AudioFolder;
	TextView tv_bar_cet4;
	TextView tv_bar_cet6;
	TextView tv_bar_ietls;
	TextView tv_bar_toefl;
	TextView tv_bar_more;
	private Cursor c;

	private DataInfo mDataInfo;

	int avg_width = 0;// 用于记录平均每个标签的宽度，移动的时候需要

	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_tab_audio);

		handler = new Handler();

		initViews();
	}

	private void initViews() {
		layout = (RelativeLayout) findViewById(R.id.audio_layout_title_bar);

		tv_bar_cet4 = (TextView) findViewById(R.id.tv_title_bar_cet4);
		tv_bar_cet6 = (TextView) findViewById(R.id.tv_title_bar_cet6);
		tv_bar_ietls = (TextView) findViewById(R.id.tv_title_bar_ielts);
		tv_bar_toefl = (TextView) findViewById(R.id.tv_title_bar_toefl);
		tv_bar_more = (TextView) findViewById(R.id.tv_title_bar_more);
		mListView = (ListView) findViewById(R.id.audio_listView_show);

		listView = new ArrayList<Music>();
		adapter = new MusicAdapter(this, listView);
		mListView.setAdapter(adapter);
		mListView.setOnCreateContextMenuListener(new ContextMenuListener(AudioActivity.this));
		mListView.setOnItemClickListener(new ListItemClickListener());
		tv_bar_cet4.setOnClickListener(onClickListener);
		tv_bar_cet6.setOnClickListener(onClickListener);
		tv_bar_ietls.setOnClickListener(onClickListener);
		tv_bar_toefl.setOnClickListener(onClickListener);
		tv_bar_more.setOnClickListener(onClickListener);
		tv_front = new TextView(this);
		tv_front.setBackgroundResource(R.drawable.slidebar);
		tv_front.setTextColor(Color.WHITE);
		tv_front.setText("CET4");
		tv_front.setGravity(Gravity.CENTER);
		RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		param.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		layout.addView(tv_front, param);

	}

	private OnClickListener onClickListener = new OnClickListener() {
		int startX;// 移动的起始位置

		@Override
		public void onClick(View v) {
			avg_width = findViewById(R.id.audio_tab_layout).getWidth();
			mDataInfo = new DataInfo();

			switch (v.getId()) {
			case R.id.tv_title_bar_cet4:
				index = 1;
				MoveBg.moveFrontBg(tv_front, startX, 0, 0, 0);
				startX = 0;

				tv_front.setText(R.string.title_audio_category_cet4);
				mDataInfo.resourceGroupID = 5;
				break;
			case R.id.tv_title_bar_cet6:
				index = 2;

				MoveBg.moveFrontBg(tv_front, startX, avg_width, 0, 0);
				startX = avg_width;
				tv_front.setText(R.string.title_audio_category_cet6);
				 mDataInfo.resourceGroupID =5;

				break;
			case R.id.tv_title_bar_ielts:
				index = 3;

				MoveBg.moveFrontBg(tv_front, startX, avg_width * 2, 0, 0);
				startX = avg_width * 2;
				tv_front.setText(R.string.title_audio_category_ielts);
				// mDataInfo.resourceGroupID =1;

				break;
			case R.id.tv_title_bar_toefl:
				index = 4;

				MoveBg.moveFrontBg(tv_front, startX, avg_width * 3, 0, 0);
				startX = avg_width * 3;
				tv_front.setText(R.string.title_audio_category_toefl);
				// mDataInfo.resourceGroupID =1;

				break;
			case R.id.tv_title_bar_more:
				index = 5;

				MoveBg.moveFrontBg(tv_front, startX, avg_width * 4, 0, 0);
				startX = avg_width * 4;
				tv_front.setText(R.string.title_audio_category_more);
				// mDataInfo.resourceGroupID =1;

				break;

			default:
				break;
			}
			NetDataLoad netDataLoad = new NetDataLoad(AudioActivity.this);
			netDataLoad.execute(0);

		}

	};

	/**
	 * 清空所有本地数据
	 */
	public void deleteAllData() {
		Dialog dialog = new AlertDialog.Builder(this).setTitle("友情提示")
				.setMessage("你确定要清空所有本地数据？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

						/*
						 * // 删除资源类型数据 DBResourceTypeImpl mDbResourceTypeImpl =
						 * new DBResourceTypeImpl(
						 * ResourceTypeFragment.this.mActivity);
						 * mDbResourceTypeImpl.deleteAllResourceTypes(); //
						 * 删除资源子类型数据 DBResourceSubTypeImpl
						 * mDbResourceSubTypeImpl = new DBResourceSubTypeImpl(
						 * ResourceTypeFragment.this.mActivity);
						 * mDbResourceSubTypeImpl.deleteAllResourceSubTypes();
						 * // 删除资源组合数据 DBResourceGroupImpl mDbResourceGroupImpl
						 * = new DBResourceGroupImpl(
						 * ResourceTypeFragment.this.mActivity);
						 * mDbResourceGroupImpl.deleteAllResourceGroup(); //
						 * 删除资源数据 DBResourceImpl mDbResourceImpl = new
						 * DBResourceImpl( ResourceTypeFragment.this.mActivity);
						 * mDbResourceImpl.deleteAllResource(); // 清空当前页面资源
						 * mDataInfo.resourceTypeEntities.clear(); // 加载本地数据
						 * LocalDataLoad localDataLoad = new LocalDataLoad(
						 * ResourceTypeFragment.this.mActivity);
						 * localDataLoad.execute(0);
						 */
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				}).create();
		dialog.show();
	}

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
			Log.v("asdasd", flagID +" "+mDataInfo.resourceGroupID);
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
			Log.v("sdadsss", flagID + " ");
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
					listView.add(music);
				}
			}
			handler.post(mUpdateTimeTask);
		}
	}

 class DataInfo {
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

	//arg0  发生点击动作的AdapterView  
    //arg1 在AdapterView中被点击的视图(它是由adapter提供的一个视图)  
    //arg2 视图在adapter中的位置  
    //arg3 被点击元素的行id  
	class ListItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Log.d("medialist" + "  listItemClick", "OK");
			CURRENTITEM = arg2;
//			
			 Music ms = new Music();
			 ms= listView.get(arg2);
			 String msg = ms.getResourceEntity().getResourceTitle();
			 
			Log.v("item Onlicked", msg+"");
			Dialog dialog = new AlertDialog.Builder(AudioActivity.this).setTitle("详细信息")
					.setMessage("文件名："+msg)
					.setPositiveButton("播放", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Intent intent = new Intent();
							intent.setClass(AudioActivity.this, MusicActivity.class);
							intent.putExtra("selectID", CURRENTITEM);
							intent.putExtra("MusicList",  (Serializable)listView);
							startActivity(intent);
							startAction(MusicService.ACTION_PLAY);

							Log.v("Start MusicActivity", ""+CURRENTITEM);

						}
					}).setNeutralButton("下载", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							
						}
					})
					.setNegativeButton("取消", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.cancel();
						}
					}).create();
			dialog.show();			// startAction(MusicService.ACTION_PLAY);
		}

	}
	private void startAction(String action) {
		Intent intent = new Intent(AudioActivity.this, MusicService.class);
		intent.setAction(action);
		Bundle b = new Bundle();
		b.putInt("size", 10000);
		for(int i=0;i<10000;++i) {
			b.putString("name"+i, "mingzi");
			b.putString("addre"+i, "addre");
			b.putString("length"+i, "length");
		}
		intent.putExtra("MusicList",  b);
		intent.putExtra("selectID", CURRENTITEM);
		startService(intent);
	}


}
