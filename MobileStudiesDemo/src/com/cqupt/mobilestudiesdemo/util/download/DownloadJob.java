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

package com.cqupt.mobilestudiesdemo.util.download;

import java.util.ArrayList;

import com.cqupt.mobilestudiesdemo.entity.ResourceEntity;

/**
 * Single remote file download task
 * 
 * @author Lukasz Wisniewski
 */
public class DownloadJob {

	private ResourceEntity mResourceEntity;
	private String mDestination;
	private DownloadTask mDownloadTask;
	private int mProgress;
	private int mTotalSize;
	private int mDownloadedSize;

	private int mStartId;

	private String mFormat;

	private DownloadManager mDownloadManager;
	private DownloadJobListener mServiceListener;
	// 判断是否被选中
	private Boolean checked = false;
	// 监听列表
	private ArrayList<DownloadJobListener> mListeners;

	private enum DownloadStatus {
		STARTED, ENDED, CANCELED, UPDATE
	}

	public DownloadJob(ResourceEntity resourceEntity, String destination,
			int startId, String downloadFormat) {
		mResourceEntity = resourceEntity;
		mDestination = destination;
		mProgress = 0;
		mStartId = startId;
		mFormat = downloadFormat;
		mListeners = new ArrayList<DownloadJobListener>();
	}

	public void setStartId(int mStartId) {
		this.mStartId = mStartId;
	}

	public int getStartId() {
		return mStartId;
	}

	public void setFormat(String mFormat) {
		this.mFormat = mFormat;
	}

	public String getFormat() {
		return mFormat;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public ResourceEntity getResourceEntity() {
		return mResourceEntity;
	}

	public void setResourceEntity(ResourceEntity resourceEntity) {
		this.mResourceEntity = resourceEntity;
	}

	public String getDestination() {
		return mDestination;
	}

	public void setDestination(String destination) {
		mDestination = destination;
	}

	public void setServiceListener(DownloadJobListener serviceListener) {
		this.mServiceListener = serviceListener;
	}

	public int getProgress() {
		return mProgress;
	}

	public void setProgress(int progress) {
		mProgress = progress;
	}

	public int getTotalSize() {
		return mTotalSize;
	}

	public void setTotalSize(int totalSize) {
		this.mTotalSize = totalSize;
	}

	public int getDownloadedSize() {
		return mDownloadedSize;
	}

	public void setDownloadedSize(int downloadedSize) {
		this.mDownloadedSize = downloadedSize;
		int oldProgress = mProgress;
		mProgress = (mDownloadedSize * 100) / mTotalSize;

		if (mProgress != oldProgress) {
			if (mServiceListener != null) {
				mServiceListener.progressUpdate(this);
			}
			notifyListeners(DownloadStatus.UPDATE);
		}
	}

	public void start() {
		mDownloadTask = new DownloadTask(this);
		mDownloadTask.execute();
	}

	public void pause() {
		// TODO DownloadTask.pause()
	}

	public void resume() {
		// TODO DownloadTask.resume()
	}

	public void cancel() {
		if (mDownloadTask != null) {
			mDownloadTask.cancel(true);
		}
	}

	public void notifyDownloadStarted() {
		mProgress = 0;
		if (mServiceListener != null) {
			mServiceListener.downloadStarted(this);
		}
		notifyListeners(DownloadStatus.STARTED);
	}

	/**
	 * 成功传入true，失败传入false
	 * 
	 * @param flag
	 *            success is true,fail is false
	 */
	public void notifyDownloadEnded(Boolean flag) {
		if (!mDownloadTask.isCancelled()) {
			// 判断下载成功与否,成功为100，不成功为-1
			if (flag == true) {
				mProgress = 100;
			} else {
				mProgress = -1;
			}
			if (mServiceListener != null) {
				mServiceListener.downloadEnded(this);
			}
			notifyListeners(DownloadStatus.ENDED);
		}
	}

	public void notifyDownloadCanceled() {
		if (mServiceListener != null) {
			mServiceListener.downloadCanceled(this);
		}
		notifyListeners(DownloadStatus.CANCELED);
	}

	public synchronized void deregisterListener(DownloadJobListener listener) {
		mListeners.remove(listener);
	}

	public synchronized void registerListener(DownloadJobListener listener) {
		if (mListeners.indexOf(listener) == -1) {
			mListeners.add(listener);
		}
	}

	private synchronized void notifyListeners(DownloadStatus status) {
		for (DownloadJobListener listener : mListeners) {
			switch (status) {
			case STARTED:
				listener.downloadStarted(this);
				break;
			case ENDED:
				listener.downloadEnded(this);
				break;
			case CANCELED:
				listener.downloadCanceled(this);
				break;
			case UPDATE:
				listener.progressUpdate(this);
				break;
			}
		}
	}
}
