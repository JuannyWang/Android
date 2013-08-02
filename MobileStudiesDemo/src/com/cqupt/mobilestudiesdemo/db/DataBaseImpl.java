package com.cqupt.mobilestudiesdemo.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.apkits.android.database.SQLiteDB;

/**
 * 外语学习模块数据库操作实现
 * 
 * @author ap
 * @date 2012-11-28
 */
public class DataBaseImpl implements DataBase {
	private final String TAG = "LanguageLearnDataBase";
	private Context mContext;
	private DBHelper mDBHelper;

	/**
	 * 数据库信息
	 */
	private static final String DB_NAME = "LanguageLearn.db";
	private static final int VERSION = 1;
	public static final String DB_RESOURCE_TABEL = "tResource";
	public static final String DB_RESOURCE_GROUP_TABEL = "tResourceGroup";
	public static final String DB_RESOURCE_SUB_TYPE_TABEL = "tResourceSubType";
	public static final String DB_RESOURCE_TYPE_TABEL = "tResourceType";

	public DataBaseImpl(Context context) {
		mContext = context;
		mDBHelper = new DBHelper(mContext, DB_NAME, VERSION);
	}

	/**
	 * 继承自com.apkits.android.database.SQLiteDB
	 * 
	 * @author ap
	 * @date 2012-11-28
	 */

	public class DBHelper extends SQLiteDB {
		/** SQLite数据库对象 */
		private SQLiteDatabase mDatabase;

		public DBHelper(Context context, String dbName, int version) {
			super(context, dbName, version);
			// TODO Auto-generated constructor stub
		}

		/**
		 * 执行SQL语句
		 * 
		 * @param sql
		 *            sql语句
		 * @param selectionArgs
		 *            查询条件
		 * @return 查询结果游标对象
		 */
		public Cursor rawQuery(String sql, String[] selectionArgs) {
			if (this.mDatabase == null) {
				this.mDatabase = this.getReadableDatabase();
			}
			return this.mDatabase.rawQuery(sql, selectionArgs);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			StringBuffer sqlStatements = new StringBuffer();
			sqlStatements
					.append("CREATE TABLE IF NOT EXISTS "
							+ DB_RESOURCE_TYPE_TABEL
							+ "(resourceTypeID INTEGER PRIMARY KEY,resourceTypeName TEXT);");
			sqlStatements
					.append("CREATE TABLE IF NOT EXISTS "
							+ DB_RESOURCE_SUB_TYPE_TABEL
							+ "(resourceSubTypeID INTEGER PRIMARY KEY,resourceTypeID INTEGER,resourceSubTypeName TEXT);");
			sqlStatements
					.append("CREATE TABLE IF NOT EXISTS "
							+ DB_RESOURCE_GROUP_TABEL
							+ "(resourceGroupID INTEGER PRIMARY KEY,resourceSubTypeID INTEGER,resourceTypeID INTEGER,resourceGroupName TEXT);");
			sqlStatements
					.append("CREATE TABLE IF NOT EXISTS "
							+ DB_RESOURCE_TABEL
							+ "(resourceID INTEGER PRIMARY KEY,"
							+ "resourceGroupID INTEGER,resourceSubTypeID INTEGER,resourceTypeID INTEGER,"
							+ "resourceTitle TEXT,resourceComment TEXT,resourcePath TEXT,resourceDescript TEXT,resourceCreateDate TEXT);");

			sqlStatements
					.append("CREATE TABLE if not exists  userWords(wordId INTEGER NOT NULL PRIMARY KEY,wordText TEXT UNIQUE NOT NULL ,wordTextDescript TEXT,wordCreateTime DATETIME,userID INTEGER ,bookID INTEGER,FOREIGN KEY(bookID) REFERENCES wordBook(wordbookID));");
			sqlStatements.append("CREATE TABLE if not exists wordBook(wordbookID INTEGER NOT NULL PRIMARY KEY,wordbookName TEXT NOT NULL,bookCreateTime DATETIME,userId INTEGER);");
			sqlStatements.append("CREATE TABLE if not exists userNote(noteID INTEGER NOT NULL PRIMARY KEY,questionID INTEGER,noteDescription TEXT  ,userID INTEGER, noteTime DATETIME);");
			this.addSQLStatement(sqlStatements.toString());
			super.onCreate(db);
		}
	}

	public DBHelper getDBHelper() {
		return mDBHelper;
	}
}
