package com.cqupt.mobilestudiesdemo.media;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

import com.cqupt.mobilestudiesdemo.entity.ResourceEntity;
import com.cqupt.mobilestudiesdemo.service.DownloadService;
import com.cqupt.mobilestudiesdemo.util.download.DownloadHelper;
import com.cqupt.mobilestudiesdemo.util.download.DownloadManager;

public class LrcProcess {

	private List<LrcContent> ENLrcList;
	private List<LrcContent> CHLrcList;
	
	private LrcContent mLrcContent;
	private Music music;
	private LrcDownloadListener lrcDownloadListener;
	public LrcProcess(Music music) {
		this.music=music;
		mLrcContent = new LrcContent();
		ENLrcList = new ArrayList<LrcContent>();
		CHLrcList=new ArrayList<LrcContent>();
	}

	public boolean readLRC(LrcDownloadListener dListener) {
		// public void Read(String file){
		DownloadManager dManager=DownloadService.getDownloadManager();
		String lrcPath=dManager.getResourcePath(music.getResourceEntity());
		lrcDownloadListener=dListener;
		DownloadLrcTask dLrcTask=new DownloadLrcTask(music.getResourceEntity());
		
		if (lrcPath!=null) {
			lrcPath=lrcPath.replace(".mp3", ".lrc");
			File eNFile=new File(lrcPath);
			lrcPath=lrcPath.replace(".lrc", "CH.lrc");
			File cHFile=new File(lrcPath);
			if(eNFile.exists())
			{
				readFile(eNFile,ENLrcList);
				if (cHFile.exists()) {
					readFile(cHFile, CHLrcList);
				}
				Log.i("lrc read", "OK");
				return true;
			}
			else {
				dLrcTask.execute();
				mLrcContent.setLrc("没有字幕！");
				return false;
				//LrcList.add(mLrcContent);
			}
		}
		return false;
	}

	private void readFile(File file,List<LrcContent> lrcContents)
	{
		try {
			Log.e(".............", "OK");
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, "GB2312");
			BufferedReader br = new BufferedReader(isr);
			String s = "";
			while ((s = br.readLine()) != null) {
				s = s.replace("[", "");
				s = s.replace("]", "@");

				String splitLrc_data[] = s.split("@");
				if (splitLrc_data.length > 1) {
					
					mLrcContent.setLrc(splitLrc_data[1]);
					int LrcTime = TimeStr(splitLrc_data[0]);
					mLrcContent.setLrcTime(LrcTime);
					lrcContents.add(mLrcContent);
					mLrcContent = new LrcContent();
				}
			}
			br.close();
			isr.close();
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Log.e("read lrc error", "error");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("read lrc error", "error");
			e.printStackTrace();
		}
	}
	
	public int TimeStr(String timeStr) {

		timeStr = timeStr.replace(":", ".");
		timeStr = timeStr.replace(".", "@");

		String timeData[] = timeStr.split("@");

		int minute = Integer.parseInt(timeData[0]);
		int second = Integer.parseInt(timeData[1]);
		int millisecond = Integer.parseInt(timeData[2]);

		int currentTime = (minute * 60 + second) * 1000 + millisecond * 10;

		return currentTime;
	}

	public List<LrcContent> getEnLrcContent() {

		return ENLrcList;
	}
	public List<LrcContent> getChLrcContent()
	{
		return CHLrcList;
	}
	public class DownloadLrcTask extends AsyncTask<Void, Integer, Boolean>
	{
		private  ResourceEntity resourceEntity;
		private static final String TAG="DownloadLrcTask";
		public DownloadLrcTask(ResourceEntity resourceEntity)
		{
			this.resourceEntity=resourceEntity;
		}
		@Override
		protected void onPostExecute(Boolean result) 
		{
			super.onPostExecute(result);
			lrcDownloadListener.downloadEnded(music,result);
		};
		@Override
		protected Boolean doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			lrcDownloadListener.downloadStarted(music);
			try {
				return downloadFile(resourceEntity);
			} catch (IOException e) {
				//Log.e(TAG, "Download file faild reason-> " + e.getMessage());
				return false;
			}
			//return true;
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		
		public Boolean downloadFile(ResourceEntity rEntity) throws IOException {

			// TODO rewrite to apache client
			
			URL url = new URL(DownloadHelper.getUrl(rEntity, DownloadHelper.LRC_FORMAT));

			/**
			 * HTTP链接
			 */
			HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setRequestMethod("GET");
			urlConn.setDoOutput(true);

			int code = urlConn.getResponseCode();

			if (code == HttpURLConnection.HTTP_OK) {
				Log.d(TAG, "http server ok");
			} else {
				Log.d(TAG, "http server error code:---" + code);
				return false;
			}

			urlConn.connect();

			String path = DownloadHelper.getAbsolutePath(rEntity,
					DownloadHelper.getDownloadPath());
			String fileName = DownloadHelper.getFileName(rEntity,
					DownloadHelper.LRC_FORMAT);
			Log.d(TAG, path);
			try {
				//Create multiple directory
				boolean success = (new File(path)).mkdirs();
				if (success) {
					Log.i(TAG, "Directory: " + path + " created");
				}

			} catch (Exception e) {// Catch exception if any
				Log.e(TAG, "Error creating folder", e);
				return false;
			}
			File file = new File(path, fileName);

			FileOutputStream fos = new FileOutputStream(file);

			InputStream in = urlConn.getInputStream();
			Log.d(TAG, "download start");
			if (in == null) {
				// When InputStream is a NULL
				fos.close();
				return false;
			}

			byte[] buffer = new byte[1024];
			int lenght = 0;
			while ((lenght = in.read(buffer)) > 0) {
				fos.write(buffer, 0, lenght);
			}

			fos.close();
			return true;
		}
	}
}
