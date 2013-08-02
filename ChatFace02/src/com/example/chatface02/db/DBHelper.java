package com.example.chatface02.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "Contactor.db";
	private static final int DATABASE_VERSION = 1;

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		 db.execSQL("CREATE TABLE IF NOT EXISTS FriendsList" +  
	                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, Name VARCHAR, NewTel VARCHAR,NormalPhone VARCHAR,Groups VARCHAR)");
		 db.execSQL("CREATE TABLE IF NOT EXISTS MESSAGE" +
				 "(_id INTEGER PRIMARY KEY AUTOINCREMENT, Mname VARCHAR, MnewTel VARCHAR, msgs TEXT,ISREAD INTEGER)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		 db.execSQL("ALTER TABLE FriendsList ADD COLUMN other STRING");  
		 db.execSQL("ALTER TABLE MESSAGE ADD COLUMN other STRING");  

	}

}
