package com.mydomain.wallpaper.mywallpaper;

import rajawali.wallpaper.Wallpaper;
import android.content.Context;

public class Service extends Wallpaper {
	private Rajawali2000PlanesRenderer mRenderer;

	public Engine onCreateEngine() {
		mRenderer = new Rajawali2000PlanesRenderer(this);
		return new WallpaperEngine(this.getSharedPreferences(SHARED_PREFS_NAME,
				Context.MODE_PRIVATE), getBaseContext(), mRenderer, false);
	}
}
