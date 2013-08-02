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

/**
 * Callback invoked at secondary thread
 * 
 * @author Lukasz Wisniewski
 */
public interface DownloadJobListener {

	/**
	 * Callback invoked when a download finishes, at secondary thread
	 */
	public void downloadEnded(DownloadJob job);

	/**
	 * Callback invoked when a download starts, at secondary thread
	 */
	public void downloadStarted(DownloadJob job);

	/**
	 * Callback invoked when a download cancels, at secondary thread
	 */
	public void downloadCanceled(DownloadJob job);

	/**
	 * Callback invoked when a download progress update,at secondary thread
	 */
	public void progressUpdate(DownloadJob job);
}
