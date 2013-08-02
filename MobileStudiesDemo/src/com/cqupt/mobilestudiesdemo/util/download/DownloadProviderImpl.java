package com.cqupt.mobilestudiesdemo.util.download;

/*
 * Copyright (C) 2011 Teleca Poland Sp. z o.o. <android@teleca.com>
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

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.cqupt.mobilestudiesdemo.entity.ResourceEntity;

/**
 * DownloadProvider implementations. Uses SqlLite database to store
 * DownloadJobs.
 * 
 * </br></br>1.请优先选择辅助线程完成所有操作</br>2.锁住相关操作，保证数据一致性</br> 3.这里需控制下载线程的个数
 * 
 * @author Bartosz Cichosz
 * @author ap
 */

public class DownloadProviderImpl implements DownloadProvider {

	private ArrayList<DownloadJob> mQueuedJobs;
	private ArrayList<DownloadJob> mCompletedJobs;
	private DownloadManager mDownloadManager;

	private Context mContext;

	public DownloadProviderImpl(DownloadManager downloadManager, Context context) {
		mDownloadManager = downloadManager;
		mContext = context;

		mQueuedJobs = new ArrayList<DownloadJob>();
		mCompletedJobs = new ArrayList<DownloadJob>();
	}

	@Override
	public void loadOldDownloads() {
		DownloadDatabase mDb = new DownloadDatabaseImpl(mContext);
		ArrayList<DownloadJob> oldDownloads;
		synchronized (this) {
			oldDownloads = mDb.getAllDownloadJobs();
		}

		for (DownloadJob dJob : oldDownloads) {
			if (dJob.getProgress() == 100) {
				synchronized (this) {
					mCompletedJobs.add(dJob);
				}
			} else {
				// 这里回调queueDownload方法，因此不能对整个方法上锁
				mDownloadManager.download(dJob.getResourceEntity());
			}
		}
	}

	@Override
	public ArrayList<DownloadJob> getAllDownloads() {
		ArrayList<DownloadJob> allDownloads = new ArrayList<DownloadJob>();
		synchronized (this) {
			allDownloads.addAll(mCompletedJobs);
			allDownloads.addAll(mQueuedJobs);
		}
		return allDownloads;
	}

	@Override
	public ArrayList<DownloadJob> getCompletedDownloads() {
		ArrayList<DownloadJob> completeDownloads = new ArrayList<DownloadJob>();
		synchronized (this) {
			completeDownloads.addAll(mCompletedJobs);
		}
		return mCompletedJobs;
	}

	@Override
	public ArrayList<DownloadJob> getQueuedDownloads() {
		ArrayList<DownloadJob> queuedDownloads = new ArrayList<DownloadJob>();
		synchronized (this) {
			queuedDownloads.addAll(mQueuedJobs);
		}
		return mQueuedJobs;
	}

	@Override
	public void downloadCompleted(DownloadJob job) {
		synchronized (this) {
			// 由于这里采用同步锁控制，因此必须判断当前job在队列中的 情况
			if (mQueuedJobs.remove(job) != true) {
				return;
			}
			// 设置下载成功与否
			DownloadDatabase mDb = new DownloadDatabaseImpl(mContext);
			if (job.getProgress() == 100) {
				mCompletedJobs.add(job);
				mDb.setStatus(job.getResourceEntity(), true);
			} else {
				mDb.remove(job);
			}
		}
		mDownloadManager.notifyObservers();
	}

	@Override
	public boolean queueDownload(DownloadJob downloadJob) {
		synchronized (this) {
			for (DownloadJob dJob : mCompletedJobs) {
				if (dJob.getResourceEntity().getResourceID() == downloadJob
						.getResourceEntity().getResourceID()) {
					return false;
				}
			}

			for (DownloadJob dJob : mQueuedJobs) {
				if (dJob.getResourceEntity().getResourceID() == downloadJob
						.getResourceEntity().getResourceID()) {
					return false;
				}
			}
			DownloadDatabase mDb = new DownloadDatabaseImpl(mContext);
			if (mDb.addToLibrary(downloadJob.getResourceEntity())) {
				mQueuedJobs.add(downloadJob);
			} else {
				return false;
			}
		}

		mDownloadManager.notifyObservers();
		return true;
	}

	@Override
	public void removeDownload(DownloadJob job) {
		if (job.getProgress() < 100) {
			job.cancel();
		}
		synchronized (this) {
			// 由于job没有控制，所以上一步判断无法得到保证，因此应该确认当前队列是否存在该job
			if (mQueuedJobs.remove(job) != true) {
				mCompletedJobs.remove(job);
			}

			DownloadDatabase mDb = new DownloadDatabaseImpl(mContext);
			mDb.remove(job);
		}
		mDownloadManager.notifyObservers();
	}

	@Override
	public void removeDownloads(ArrayList<DownloadJob> jobs) {
		// TODO Auto-generated method stub
		int size = jobs.size();
		for (int i = 0; i < size; ++i) {
			DownloadJob job = jobs.get(i);
			if (job.getChecked() == true) {
				if (job.getProgress() < 100) {
					job.cancel();
				}
				synchronized (this) {
					// 由于job没有控制，所以上一步判断无法得到保证，因此应该确认当前队列是否存在该job
					if (mQueuedJobs.remove(job) != true) {
						mCompletedJobs.remove(job);
					}
					Log.i("Download Provider", "delete " + job.getStartId());
					DownloadDatabase mDb = new DownloadDatabaseImpl(mContext);
					mDb.remove(job);
				}
			}
		}
		mDownloadManager.notifyObservers();
	}

	@Override
	public synchronized void startDownload(int index) {
		// TODO Auto-generated method stub
		int size = mQueuedJobs.size();
		if (size > 0 && size > index) {
			// 当该索引存在时
			DownloadJob downloadJob = mQueuedJobs.get(index);
			downloadJob.start();
		}
	}

	@Override
	public synchronized void startDownload(ResourceEntity resourceEntity,
			int startId, DownloadJobListener downloadJobListener) {
		// TODO Auto-generated method stub
		for (DownloadJob dJob : mQueuedJobs) {
			if (resourceEntity.getResourceID() == dJob.getResourceEntity()
					.getResourceID()) {
				// 设置相关下载数据
				dJob.setServiceListener(downloadJobListener);
				dJob.setStartId(startId);
				dJob.start();
			}
		}
	}

	@Override
	public synchronized Boolean resourceAvailable(ResourceEntity resourceEntity) {
		// TODO Auto-generated method stub
		for (DownloadJob dJob : mCompletedJobs) {
			if (resourceEntity.getResourceID() == dJob.getResourceEntity()
					.getResourceID()) {
				return true;
			}
		}
		return false;
	}

}
