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

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import com.cqupt.mobilestudiesdemo.entity.ResourceEntity;
import com.cqupt.mobilestudiesdemo.service.DownloadService;

/**
 * DownloadManager implementation. Using DownloadProviderDbImpl as DownloadJobs
 * provider. </br></br>1.请优先选择辅助线程完成所有操作
 * 
 * @author Bartosz Cichosz
 * 
 ** @author ap
 * 
 */
public class DownloadManagerImpl implements DownloadManager {

	private Context mContext;
	private DownloadProvider mProvider;
	private ArrayList<DownloadObserver> mObservers;

	public DownloadManagerImpl(Context context) {
		mContext = context;
		mObservers = new ArrayList<DownloadObserver>();
		mProvider = new DownloadProviderImpl(this, mContext);
	}

	@Override
	public DownloadJob download(ResourceEntity resourceEntity) {

		if (resourceEntity != null) {
			String downloadPath = DownloadHelper.getDownloadPath();
			DownloadJob downloadJob = new DownloadJob(resourceEntity,
					downloadPath, 0, DownloadHelper.MP3_FORMAT);

			if (mProvider.queueDownload(downloadJob)) {

				// 启动下载服务管理下载
				Intent intent = new Intent(mContext, DownloadService.class);
				intent.setAction(DownloadService.ACTION_ADD_TO_DOWNLOAD);
				intent.putExtra(DownloadService.EXTRA_RESOURCE_ENTITY,
						resourceEntity);
				mContext.startService(intent);

				return downloadJob;
			}
		}
		return null;
	}

	@Override
	public void startDownloadService() {
		// TODO Auto-generated method stub
		mProvider.loadOldDownloads();
	}

	@Override
	public String getResourcePath(ResourceEntity resourceEntity) {
		if (resourceEntity == null) {
			return null;
		}

		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			ArrayList<DownloadJob> completedJobs = getCompletedDownloads();
			Boolean isAvailable = false;
			DownloadJob mDownloadJob = null;

			// we need to check the database to be sure whether file was
			// downloaded completely
			for (DownloadJob downloadJob : completedJobs) {
				if (resourceEntity.getResourceID() == downloadJob
						.getResourceEntity().getResourceID()) {
					isAvailable = true;
					mDownloadJob = downloadJob;
					break;
				}
			}

			if (isAvailable == false) {
				return null;
			} else {
				// now we may give a reference to this file after we check it
				// really
				// exists
				// in case file was somehow removed manually

				String resourcePath = DownloadHelper.getAbsolutePath(
						resourceEntity, DownloadHelper.getDownloadPath());

				String fileName = DownloadHelper.getFileName(resourceEntity,
						mDownloadJob.getFormat());

				File file = new File(resourcePath, fileName);

				if (file.exists()) {// 当下载文件存在时
					String path = file.getAbsolutePath();
					return path;
				} else {// 当下载文件不存在时
					// we need delete this job from database
					if (mDownloadJob != null) {
						deleteDownload(mDownloadJob);
					}
					return null;
				}
			}
		}
		return null;
	}

	@Override
	public ArrayList<DownloadJob> getAllDownloads() {
		return mProvider.getAllDownloads();
	}

	@Override
	public ArrayList<DownloadJob> getCompletedDownloads() {
		return mProvider.getCompletedDownloads();
	}

	@Override
	public ArrayList<DownloadJob> getQueuedDownloads() {
		return mProvider.getQueuedDownloads();
	}

	@Override
	public DownloadProvider getProvider() {
		return mProvider;
	}

	@Override
	public void deleteDownload(DownloadJob job) {
		mProvider.removeDownload(job);
		removeDownloadFromDisk(job);
	}

	@Override
	public void deleteDownloads(ArrayList<DownloadJob> jobs) {
		// TODO Auto-generated method stub
		mProvider.removeDownloads(jobs);
		int size = jobs.size();
		for (int i = 0; i < size; ++i) {
			DownloadJob downloadJob = jobs.get(i);
			if (downloadJob.getChecked() == true) {
				removeDownloadFromDisk(downloadJob);
			}
		}
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

	@Override
	public synchronized void deregisterDownloadObserver(
			DownloadObserver observer) {
		mObservers.remove(observer);
	}

	@Override
	public synchronized void registerDownloadObserver(DownloadObserver observer) {
		mObservers.add(observer);
	}

	@Override
	public synchronized void notifyObservers() {
		for (DownloadObserver observer : mObservers) {
			observer.onDownloadChanged(this);
		}
	}

}
