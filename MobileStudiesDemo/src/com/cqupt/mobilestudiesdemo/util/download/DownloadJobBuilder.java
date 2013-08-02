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

package com.cqupt.mobilestudiesdemo.util.download;

import android.content.ContentValues;
import android.database.Cursor;

import com.cqupt.mobilestudiesdemo.db.DBResourceBuilder;
import com.cqupt.mobilestudiesdemo.db.DataBaseBuilder;
import com.cqupt.mobilestudiesdemo.entity.ResourceEntity;

/**
 * Download job builder
 * 
 * @author Bartosz Cichosz
 * 
 */
public class DownloadJobBuilder extends DataBaseBuilder<DownloadJob> {

	private static final String DOWNLOADED = "downloaded";

	@Override
	public DownloadJob build(Cursor query) {
		ResourceEntity resourceEntity = new DBResourceBuilder().build(query);
		DownloadJob dJob = new DownloadJob(resourceEntity,
				DownloadHelper.getDownloadPath(), 0, DownloadHelper.MP3_FORMAT);
		int progress = query.getInt(query.getColumnIndex(DOWNLOADED));
		if (progress == 1) {
			dJob.setProgress(100);
		}
		return dJob;
	}

	@Override
	public ContentValues deconstruct(DownloadJob downloadJob) {
		ContentValues contentValues = new DBResourceBuilder()
				.deconstruct(downloadJob.getResourceEntity());
		contentValues.put(DOWNLOADED, (downloadJob.getProgress() == 100) ? 1
				: 0);

		return contentValues;
	}

}
