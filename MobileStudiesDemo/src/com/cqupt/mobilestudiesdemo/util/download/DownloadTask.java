package com.cqupt.mobilestudiesdemo.util.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

import com.cqupt.mobilestudiesdemo.entity.ResourceEntity;

public class DownloadTask extends AsyncTask<Void, Integer, Boolean> {
	private static final String TAG = "DownloadTask";
	private DownloadJob mJob;

	public DownloadTask(DownloadJob job) {
		mJob = job;
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		// TODO Auto-generated method stub
		// 通知下载开始
		mJob.notifyDownloadStarted();
		try {
			return downloadFile(mJob);
		} catch (IOException e) {
			Log.e(TAG, "Download file faild reason-> " + e.getMessage());
			return false;
		}
	}

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
		// 防止在主线程阻塞
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (DownloadTask.this.isCancelled()) {
					removeDownloadFromDisk(mJob);
					// 通知下载中断
					mJob.notifyDownloadCanceled();
					Log.d(TAG, "download cancel");
				}
			}
		});
		thread.start();
	}

	@Override
	protected void onPostExecute(final Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		// 防止在主线程阻塞
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				// 通知下载完成
				mJob.notifyDownloadEnded(result);
				Log.d(TAG, "download end");
			}
		});
		// 设置低优先级，让UI线程优先执行
		thread.setPriority(Thread.MIN_PRIORITY);
		thread.start();
	}

	public static Boolean downloadFile(DownloadJob job) throws IOException {

		// TODO rewrite to apache client

		ResourceEntity mResourceEntity = job.getResourceEntity();

		String mDestination = job.getDestination();

		URL url = new URL(DownloadHelper.getUrl(job.getResourceEntity(),
				job.getFormat()));

		/**
		 * HTTP链接
		 */
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		urlConn.setRequestMethod("GET");
		urlConn.setDoOutput(true);

		int code = urlConn.getResponseCode();

		/**
		 * 判断网络
		 */
		if (code == HttpURLConnection.HTTP_OK) {
			Log.d(TAG, "http server ok");
		} else {
			Log.d(TAG, "http server error code:---" + code);
			return false;
		}

		/**
		 * 链接网络
		 */
		urlConn.connect();
		job.setTotalSize(urlConn.getContentLength());
		Log.d(TAG, "ContentLength---" + urlConn.getContentLength() + "");
		Log.i(TAG, "creating file");

		String path = DownloadHelper.getAbsolutePath(mResourceEntity,
				mDestination);
		String fileName = DownloadHelper.getFileName(mResourceEntity,
				job.getFormat());
		Log.d(TAG, path);
		try {
			// Create multiple directory
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
			// 通知下载更新
			job.setDownloadedSize(job.getDownloadedSize() + lenght);
		}

		fos.close();
		return true;
	}

	private void removeDownloadFromDisk(DownloadJob job) {

		ResourceEntity resourceEntity = job.getResourceEntity();

		String resourcePath = DownloadHelper.getAbsolutePath(resourceEntity,
				DownloadHelper.getDownloadPath());

		String fileName = DownloadHelper.getFileName(resourceEntity,
				job.getFormat());

		File file = new File(resourcePath, fileName);

		if (file.exists()) {
			file.delete();
		}
	}
}
