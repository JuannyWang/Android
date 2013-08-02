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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.os.Environment;
import android.util.Log;

import com.cqupt.mobilestudiesdemo.entity.ResourceEntity;

/**
 * Various helper functions
 * 
 * @author Lukasz Wisniewski
 */
public class DownloadHelper {
	private static final String TAG = "DownloadHelper";
	private static final String URL = "http://202.202.43.36/webcollege/Resource/Listen/YY900/";
	public static final long BYTE_TO_MB = 1024 * 1024;
	public static final String MP3_FORMAT = ".mp3";
	public static final String LRC_FORMAT = ".lrc";

	public static String getUrl(ResourceEntity resourceEntity,
			String downloadFormat) {
		String path = resourceEntity.getResourcePath();
		if (path != null) {
			Log.d("Download", path);
			String[] url = path.split(",");
			String urlPath = null;
			if (downloadFormat.equals(MP3_FORMAT)) {
				urlPath = URL + toUtf8(url[1].trim());
			} else if (downloadFormat.equals(LRC_FORMAT)) {
				urlPath = URL + toUtf8(url[0].trim());
			}
			return urlPath;
		} else {
			return null;
		}
	}

	public static String getFileName(ResourceEntity resourceEntity,
			String downloadFormat) {
		String pattern = null;
		if (downloadFormat.equals(MP3_FORMAT)) {
			pattern = "%s.mp3";
		} else if (downloadFormat.equals(LRC_FORMAT)) {
			pattern = "%s.lrc";
		}

		return String.format(pattern, resourceEntity.getResourceTitle());
	}

	public static String getRelativePath(ResourceEntity resourceEntity) {
		String pattern = "/%d/%s";
		return String.format(pattern, resourceEntity.getResourceGroupID(),
				resourceEntity.getResourceTitle());
	}

	public static String getAbsolutePath(ResourceEntity resourceEntity,
			String destination) {
		return destination + getRelativePath(resourceEntity);
	}

	public static String getDownloadPath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath()
				+ "/LanguageLearn";
	}

	public static String toUtf8(String str) {
		String result = null;
		try {
			result = URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			result = null;
			Log.e(TAG, "str to utf-8 encode error:" + e.getMessage());
		}
		return result;
	}
}
