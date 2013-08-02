package com.cqupt.mobilestudiesdemo.media;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cqupt.mobilestudiesdemo.activity.R;
import com.cqupt.mobilestudiesdemo.service.DownloadService;
import com.cqupt.mobilestudiesdemo.util.download.DownloadJob;
import com.cqupt.mobilestudiesdemo.util.download.DownloadJobListener;
import com.cqupt.mobilestudiesdemo.util.download.DownloadManager;

public class MusicAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<Music> listMusic;
	private Context context;
	public static int selectPosition=-1;
	ViewHolder holder = null;
	public MusicAdapter(Context context, List<Music> listMusic) {
		this.context = context;
		this.listMusic = listMusic;
		this.mInflater = LayoutInflater.from(context);
	}

	public void setListItem(List<Music> listMusic) {
		this.listMusic = listMusic;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listMusic.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return listMusic.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final Music m = listMusic.get(position);
		final int clickIndex=position;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.listitems_media, null);
			holder.itemlayout=(LinearLayout)convertView.findViewById(R.id.layout_mediaitem);
			holder.fileid = (TextView) convertView
					.findViewById(R.id.txtview_id);
			holder.title = (TextView) convertView
					.findViewById(R.id.txtview_title);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.fileid.setText(1 + position + "");

		holder.title.setText(m.getResourceEntity().getResourceTitle());
				
		if (selectPosition==position) {
			holder.itemlayout.setBackgroundResource(R.drawable.list_selectbg);
			//holder.playinglogo.setVisibility(ImageView.VISIBLE);
		}
		else {
			holder.itemlayout.setBackgroundColor(Color.TRANSPARENT);
			//holder.playinglogo.setVisibility(ImageView.INVISIBLE);
		}
		
//		if (DownloadService.getDownloadManager().getResourcePath(m.getResourceEntity())==null) {
//			holder.downloadbtn.setVisibility(ImageButton.VISIBLE);
//		}
//		else {
//			holder.downloadbtn.setVisibility(ImageButton.INVISIBLE);
//		}
//		if (m.getDownloadJob()!=null) {
//			Log.i("selectionID", "   "+position);
//			if (m.getDownloadJob().getProgress()==-1||m.getDownloadJob().getProgress()==100) {
//				holder.probardownload.setVisibility(ProgressBar.INVISIBLE);
//			}else {
//				holder.downloadbtn.setVisibility(ImageButton.INVISIBLE);
//				holder.probardownload.setVisibility(ProgressBar.VISIBLE);
//			}
//		}
//		else {
//			holder.probardownload.setVisibility(ProgressBar.INVISIBLE);
//		}
		
//		holder.downloadbtn.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				downLoadResource(clickIndex);
//			}
//		});
		return convertView;
	}
	final class ViewHolder {
		public LinearLayout itemlayout;
		public TextView fileid;
		public TextView title;
		public ProgressBar	probardownload;
		public ImageButton downloadbtn;
	}
	/**
	 * 时间转换
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
	private DownloadJobListener downloadJobListener=new DownloadJobListener() {
		
		@Override
		public void downloadStarted(DownloadJob job) {
			// TODO Auto-generated method stub
			//Log.i(TAG, "downloadStarted");
			
		}
		
		@Override
		public void downloadEnded(DownloadJob job){
			// TODO Auto-generated method stub
			PlayerEngineImpl.getPlayerEngineListener().onUpdateView();
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
	
	public void downLoadResource(final int position)
	{ 
		Thread thread=new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				DownloadManager downloadManager=DownloadService.getDownloadManager();
				DownloadJob dJob=downloadManager.download(listMusic.get(position).getResourceEntity());
				if (dJob!=null) {
					dJob.registerListener(downloadJobListener);
					listMusic.get(position).setDownloadJob(dJob);
					PlayerEngineImpl.getPlayerEngineListener().onUpdateView();
				}
			}
		});
		
		Log.i("Thread.........start", "OK");
		// 设置低优先级，让UI线程优先执行
		thread.setPriority(Thread.MIN_PRIORITY);
		if (listMusic.get(position).getDownloadJob()==null) {
			thread.start();
		}
	}
}
