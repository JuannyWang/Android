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

import com.cqupt.mobilestudiesdemo.entity.ResourceEntity;

/**
 * Download jobs provider. Handles storing DownloadJobs.
 * 
 * @author Bartosz Cichosz
 * 
 */
public interface DownloadProvider {
	public abstract void loadOldDownloads();

	public abstract ArrayList<DownloadJob> getAllDownloads();

	public abstract ArrayList<DownloadJob> getCompletedDownloads();

	public abstract ArrayList<DownloadJob> getQueuedDownloads();

	public abstract void downloadCompleted(DownloadJob job);

	public abstract boolean queueDownload(DownloadJob downloadJob);

	public abstract void removeDownload(DownloadJob job);

	public abstract void removeDownloads(ArrayList<DownloadJob> jobs);

	public abstract void startDownload(int index);

	public abstract void startDownload(ResourceEntity resourceEntity,
			int startId, DownloadJobListener downloadJobListener);

	public abstract Boolean resourceAvailable(ResourceEntity resourceEntity);

}