/**
 * 
 */
package ghost.android3d.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 数据库操作功能类(不可通用)
 * 
 * @author 玄雨
 * @qq 821580467
 * @date 2013-1-23
 */
abstract public class MySQLiteTool {

	private static SQLiteDBHelper dh;

	/**
	 * 打开一个数据库
	 * 
	 * @param context
	 *            上下文参数
	 * @param sqlDB
	 *            数据库文件名字
	 */
	public static void open(Context context, String sqlDB) {
		dh = new SQLiteDBHelper(context, sqlDB, null, 1);
		Log.d("MySQLiteTool", "create or open DB sucess!");
	}

	/**
	 * 插入数据
	 * 
	 * @param uid
	 * @param uname
	 */
	public static void insert(int uid, String uname) {
		ContentValues cv = new ContentValues();
		cv.put("uid", uid);
		cv.put("uname", uname);
		dh.getWritableDatabase().insert("sqlitetest", null, cv);
		Log.d("MySQLiteTool", "insert data to DB sucess!");
	}

	/**
	 * 更新数据
	 * @param uid
	 * @param uname
	 */
	public static void update(String uid, String uname) {
		ContentValues cv = new ContentValues();
		cv.put("uname", uname);
		dh.getWritableDatabase().update("sqlitetest", cv, "uid=?",
				new String[] { uid });
		Log.d("MySQLiteTool", "update data in DB sucess!");
	}

	/**
	 * 查询数据
	 */
	public static void search() {
		Cursor cursor = dh.getReadableDatabase().query("sqlitetest",
				new String[] { "uid", "uname" }, null, null, null, null, null);
		while (cursor.moveToNext()) {
			String name = cursor.getString(cursor.getColumnIndex("uname"));
			Log.d("MySQLiteTool", "search data from DB sucess:" + name);
		}
	}

}

class SQLiteDBHelper extends SQLiteOpenHelper {

	/**
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public SQLiteDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite
	 * .SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table sqlitetest (uid long,uname varchar(25))");
		Log.d("SQLiteDBHelper", "create DB sucess!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite
	 * .SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
