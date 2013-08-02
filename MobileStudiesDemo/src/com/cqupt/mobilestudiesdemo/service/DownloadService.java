/*
 * Copyright (C) 2009 Teleca Poland Sp. z o.o. <android@teleca.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cqupt.mobilestudiesdemo.service;

import java.util.concurrent.Semaphore;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.cqupt.mobilestudiesdemo.activity.R;
import com.cqupt.mobilestudiesdemo.entity.ResourceEntity;
import com.cqupt.mobilestudiesdemo.util.download.DownloadJob;
import com.cqupt.mobilestudiesdemo.util.download.DownloadJobListener;
import com.cqupt.mobilestudiesdemo.util.download.DownloadManager;
import com.cqupt.mobilestudiesdemo.util.download.DownloadManagerImpl;
import com.cqupt.mobilestudiesdemo.util.download.DownloadProvider;

// TODO sd card listener
/**
 * Background download manager
 * 
 * @author Lukasz Wisniewski
 */
public class DownloadService extends Service {
	private static final String TAG = "DownloadService";
	public static final String ACTION_ADD_TO_DOWNLOAD = "add_to_download";
	public static final String ACTION_START_DOWNLOAD = "start_download";

	public static final String EXTRA_RESOURCE_ENTITY = "resourceEntity";

	private static final int DOWNLOAD_NOTIFY_ID = 667668;

	// 最大下载线程数量
	private static final int DOWNLOAD_NUM_MAX = 1;

	// 控制下载线程数量
	private final Semaphore mutex = new Semaphore(DOWNLOAD_NUM_MAX, true);

	private NotificationManager mNotificationManager = null;

	private DownloadProvider mDownloadProvider;
	private static DownloadManager mDownloadManager;

	private Handler mHandler;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mDownloadManager = new DownloadManagerImpl(this);
		mDownloadProvider = mDownloadManager.getProvider();
		mHandler = new Handler();
		Log.i(TAG, "DownloadService.onCreate");
	}

	/**
	 * 得到下载管理实例
	 * 
	 * @return
	 */
	public static DownloadManager getDownloadManager() {
		return mDownloadManager;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		if (intent == null) {
			return super.onStartCommand(intent, flags, startId);
		}

		String action = intent.getAction();
		Log.i(TAG, "DownloadService.onStart - " + action);

		if (action != null) {
			if (action.equals(ACTION_ADD_TO_DOWNLOAD)) {
				ResourceEntity resourceEntity = (ResourceEntity) intent
						.getSerializableExtra(EXTRA_RESOURCE_ENTITY);
				addToDownloadQueue(resourceEntity, startId);
			} else if (action.equals(ACTION_START_DOWNLOAD)) {
				// TODO Auto-generated method stub
				// 防止在主线程阻塞
				Thread thread = new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						// 加载旧下载数据
						mDownloadProvider.loadOldDownloads();
					}
				});
				thread.start();
			}
		}

		return super.onStartCommand(intent, flags, startId);
	}

	public void addToDownloadQueue(final ResourceEntity resourceEntity,
			final int startId) {

		// check database if record already exists, if so abandon starting
		// another download process
		// 防止在主线程阻塞
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// 必须先减少下载线程数量，再显示下载完成
				mDownloadProvider.startDownload(resourceEntity, startId,
						mDownloadJobListener);
			}
		});
		// 设置低优先级，让UI线程优先执行
		thread.setPriority(Thread.MIN_PRIORITY);
		thread.start();
	}

	private void displayNotifcation(DownloadJob job) {
		String notificationString = null;
		String notificationMessage = job.getResourceEntity().getResourceTitle();
		// 判断下载成功与否
		if (job.getProgress() == 100) {
			notificationString = getString(R.string.downloaded);
		} else {
			notificationString = getString(R.string.download_failed);
		}
		Notification notification = new Notification(
				android.R.drawable.stat_sys_download_done, notificationMessage
						+ "---" + notificationString,
				System.currentTimeMillis());
		notification.setLatestEventInfo(this, getString(R.string.downloaded),
				notificationMessage, null);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		mNotificationManager.notify(DOWNLOAD_NOTIFY_ID, notification);
	}

	private DownloadJobListener mDownloadJobListener = new DownloadJobListener() {

		@Override
		public void downloadEnded(final DownloadJob job) {
			mDownloadProvider.downloadCompleted(job);
			mHandler.post(new JobRunnable(job));
			// 释放资源
			mutex.release();
		}

		@Override
		public void downloadStarted(DownloadJob job) {
			try {
				// 得到资源
				mutex.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void downloadCanceled(DownloadJob job) {
			// TODO Auto-generated method stub
			// 释放资源
			if (mutex.availablePermits() != DOWNLOAD_NUM_MAX) {
				mutex.release();
			}
		}

		@Override
		public void progressUpdate(DownloadJob job) {
			// 通知下载更新进度
			mDownloadManager.notifyObservers();
		};
	};

	/**
	 * Runnable display Notifcation
	 */

	private class JobRunnable implements Runnable {
		DownloadJob mDownloadJob;

		public JobRunnable(DownloadJob job) {
			mDownloadJob = job;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			displayNotifcation(mDownloadJob);
		}
	}
}
