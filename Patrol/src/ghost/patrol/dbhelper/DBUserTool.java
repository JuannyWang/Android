/**
 * 
 */
package ghost.patrol.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-3-31
 */
public class DBUserTool {

	private static SQLiteDBUserHelper dh;

	/**
	 * 打开一个数据库
	 * 
	 * @param context
	 *            上下文参数
	 * @param sqlDB
	 *            数据库文件名字
	 */
	public static void open(Context context) {
		if (dh == null) {
			dh = new SQLiteDBUserHelper(context, "user_table", null, 1);
		}
		Log.d("MySQLiteTool", "create or open usertable sucess!");
	}

	/**
	 * 插入数据
	 * 
	 * @param uid
	 * @param uname
	 */
	public static void insert(int UserID, String username, String password,
			String name, String sex, int age, int purview, String telephone,
			String department, String team, String monitor,
			String passForFinder, String passForManager) {
		ContentValues cv = new ContentValues();
		cv.put("UserID", UserID);
		cv.put("username", username);
		cv.put("password", password);
		cv.put("name", name);
		cv.put("sex", sex);
		cv.put("age", age);
		cv.put("purview", purview);
		cv.put("telephone", telephone);
		cv.put("department", department);
		cv.put("team", team);
		cv.put("monitor", monitor);
		cv.put("passForFinder", passForFinder);
		cv.put("passForManager", passForManager);
		dh.getWritableDatabase().insert("usertable", null, cv);
		Log.d("MySQLiteTool", "insert data to usertable sucess!");
	}

	public static void clear() {
		dh.getWritableDatabase().execSQL("delete from usertable where 1");
		Log.d("MySQLiteTool", "delete data from usertable sucess!");
	}

	/**
	 * 查询数据
	 */
	public static Cursor search() {
		Cursor cursor = dh.getReadableDatabase().query(
				"usertable",
				new String[] { "UserID", "username", "password", "name", "sex",
						"age", "purview", "telephone", "department", "team",
						"monitor", "passForFinder", "passForManager" }, null,
				null, null, null, null);
		return cursor;
	}

	public static String getPassword(String username) {
		String result = "";
		Cursor cursor = dh.getReadableDatabase().query("usertable",
				new String[] { "password" }, "username", new String[] { username },
				null, null, null, null);
		while (cursor.moveToNext()) {
			result = cursor.getString(cursor.getColumnIndex("password"));
		}
		return result;
	}

}