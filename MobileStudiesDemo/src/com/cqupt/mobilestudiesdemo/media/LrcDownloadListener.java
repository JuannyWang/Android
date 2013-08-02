package com.cqupt.mobilestudiesdemo.media;


public interface LrcDownloadListener {
	/**
	 * Callback invoked when a download finishes, at main thread
	 */
	public void downloadEnded(Music music,boolean bool);

	/**
	 * Callback invoked when a download starts, at secondary thread
	 */
	public void downloadStarted(Music music);

	/**
	 * Callback invoked when a download cancels,youself decide where the
	 * thread,please preference secondary thread
	 */
	public void downloadCanceled(Music music);

	/**
	 * Callback invoked when a download progress update,at secondary thread
	 */
	public void progressUpdate(Music music);
}
