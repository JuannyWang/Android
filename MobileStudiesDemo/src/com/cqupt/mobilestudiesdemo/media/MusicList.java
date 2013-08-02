package com.cqupt.mobilestudiesdemo.media;

import android.util.Log;

public class MusicList {

	
	
	/*public static List<Music> getMediaData(String groupName)
	{
		
		DBHelper dbHelper=new DBHelper();
		List<Music> musicList = new ArrayList<Music>();
		Cursor cursor=dbHelper.GetResourceLists(groupName);
		if (null == cursor) {
			return null;
		}
		if (cursor.moveToFirst()) {
			do {
				Music m = new Music();
				String name = cursor.getString(cursor
						.getColumnIndex("resourceTitle"));
				String url =toUrl(cursor.getString(cursor
						.getColumnIndex("resourcePath")));
				
				Log.e("MusicList --------------", name);
				m.setUrl(url);
				m.setName(name);
				musicList.add(m);
			} while (cursor.moveToNext());
		}
		dbHelper.closeDB();
		Log.i("MusicList..........listsize", ""+musicList.size());
		return musicList;		
	}
	*/
	private static String toUrl(String path)
	{
		if (null==path) {
			return null;
		}
		else
		{
			String[]url=path.split(",");
			Log.i("MusicList......url", url[1]);
			return "/sdcard/music/"+url[1].trim();
		}
	}
}
