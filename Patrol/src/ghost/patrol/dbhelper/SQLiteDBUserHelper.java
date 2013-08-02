/**
 * 
 */
package ghost.patrol.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-4-1
 */
public class SQLiteDBUserHelper extends SQLiteOpenHelper {

	/**
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public SQLiteDBUserHelper(Context context, String name, CursorFactory factory,
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
		db.execSQL("create table usertable (UserID long,"
				+ "username varchar(50)," + "password varchar(50),"
				+ "name varchar(50)," + "sex varchar(50)," + "age long,"
				+ "purview long," + "telephone varchar(50),"
				+ "department varchar(50)," + "team varchar(50),"
				+ "monitor varchar(50)," + "passForFinder varchar(50),"
				+ "passForManager varchar(50)" + ")");
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
