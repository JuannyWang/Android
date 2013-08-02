package com.example.mobilenetstatedemo.adapter;

import android.content.ContentValues;

import android.content.Context;

import android.database.Cursor;

import android.database.SQLException;

import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;

public class DataBaseAdapter {

	public static final String WIFI_IP = "_id";

	private static final String TAG = "DBAdapter";

	private static final String DATABASE_NAME = "MyPhoneNet";

	private static final String DATABASE_TABLE = "USERNET";

	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE = "create table titles (_id varchar(20) primary key autoincrement, ";

	private final Context context;

	private DatabaseHelper DBHelper;

	private SQLiteDatabase db;

	public DataBaseAdapter(Context ctx) {

		this.context = ctx;

		DBHelper = new DatabaseHelper(context);

	}

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {

			super(context, DATABASE_NAME, null, DATABASE_VERSION);

		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			db.execSQL(DATABASE_CREATE);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion,

		int newVersion) {

			Log.w(TAG, "Upgrading database from version " + oldVersion

			+ " to "

			+ newVersion + ", which will destroy all old data");

			db.execSQL("DROP TABLE IF EXISTS WIFI_IP");

			onCreate(db);

		}

	}

	// ---打开数据库---

	public DataBaseAdapter open() throws SQLException

	{

		db = DBHelper.getWritableDatabase();

		return this;

	}

	// ---关闭数据库---

	public void close()

	{

		DBHelper.close();

	}

	// ---向数据库中插入ip---

	public long insertWifiIP(String ip)

	{

		ContentValues initialValues = new ContentValues();

		initialValues.put(WIFI_IP, ip);

		return db.insert(DATABASE_TABLE, null, initialValues);

	}

	// ---删除ip--

	public boolean deleteWifiIP(long rowId)

	{

		return db.delete(DATABASE_TABLE, WIFI_IP +

		"=" + rowId, null) > 0;

	}

	//---检索所有ip---

	public Cursor getAllIP()

	{

	return db.query(DATABASE_TABLE, new String[] {

	WIFI_IP },
	
	null,

	null,

	null,

	null,

	null);

	}
	// ---检索一个指定ip---

	public Cursor getWifiIP(long rowId) throws SQLException

	{

		Cursor mCursor =

		db.query(true, DATABASE_TABLE, new String[] {

		WIFI_IP

		},

		WIFI_IP + "=" + rowId,

		null,

		null,

		null,

		null,

		null);

		if (mCursor != null) {

			mCursor.moveToFirst();

		}

		return mCursor;

	}

	// ---更新一个标题---

	public boolean updateWifiIP(long rowId)

	{

		ContentValues args = new ContentValues();

		args.put(WIFI_IP, rowId);

		return db.update(DATABASE_TABLE, args,

				WIFI_IP + "=" + rowId, null) > 0;

	}
}
