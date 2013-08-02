package com.cqupt.mobilestudiesdemo.util.download;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.apkits.android.database.SQLiteDB;
import com.cqupt.mobilestudiesdemo.db.DBResourceBuilder;
import com.cqupt.mobilestudiesdemo.entity.ResourceEntity;

/**
 * 下载管理数据库
 * 
 * @author ap
 * 
 */
public class DownloadDatabaseImpl implements DownloadDatabase {
	private static final String TAG = "DownloadDatabase";
	private Context mContext;
	private DBHelper mDBHelper;

	/**
	 * 数据库信息
	 */
	private static final String DB_NAME = "LanguageLearnDownload.db";
	private static final int VERSION = 1;
	private static final String DB_DOWNLOAD_TABEL = "tDownload";

	public DownloadDatabaseImpl(Context context) {
		mContext = context;
		mDBHelper = new DBHelper(mContext, DB_NAME, VERSION);
	}

	/**
	 * 继承自com.apkits.android.database.SQLiteDB
	 * 
	 * @author ap
	 * @date 2012-12-11
	 */
	private class DBHelper extends SQLiteDB {
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
							+ DB_DOWNLOAD_TABEL
							+ "(resourceID INTEGER PRIMARY KEY,downloaded INTEGER,"
							+ "resourceGroupID INTEGER,resourceSubTypeID INTEGER,resourceTypeID INTEGER,"
							+ "resourceTitle TEXT,resourceComment TEXT,resourcePath TEXT,resourceDescript TEXT,resourceCreateDate TEXT);");
			this.addSQLStatement(sqlStatements.toString());
			super.onCreate(db);
		}

	}

	@Override
	public boolean addToLibrary(ResourceEntity resourceEntity) {
		// TODO Auto-generated method stub
		DBResourceBuilder resourceDBBuilder = new DBResourceBuilder();
		ContentValues contentValues = resourceDBBuilder
				.deconstruct(resourceEntity);
		contentValues.put("downloaded", 0);
		long row_count = -1l;

		try {
			row_count = mDBHelper
					.insertOrUpdate(DB_DOWNLOAD_TABEL, contentValues,
							"resourceID", resourceEntity.getResourceID());
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			mDBHelper.close();
		}
		return row_count != -1l;
	}

	@Override
	public void setStatus(ResourceEntity resourceEntity, boolean downloaded) {
		// TODO Auto-generated method stub
		DBResourceBuilder resourceDBBuilder = new DBResourceBuilder();
		ContentValues contentValues = resourceDBBuilder
				.deconstruct(resourceEntity);
		contentValues.put("downloaded", downloaded ? 1 : 0);

		try {
			mDBHelper.insertOrUpdate(DB_DOWNLOAD_TABEL, contentValues,
					"resourceID", resourceEntity.getResourceID());
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			mDBHelper.close();
		}
	}

	@Override
	public boolean ResourceAvailable(ResourceEntity resourceEntity) {
		// TODO Auto-generated method stub
		Boolean result = false;
		Cursor cursor = null;
		try {
			cursor = mDBHelper.query(DB_DOWNLOAD_TABEL, null,
					"resourceID=? and downloaded>0",
					DBHelper.toArgs(resourceEntity.getResourceID()));
			result = cursor.getCount() > 0;
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			mDBHelper.close();
		}
		return result;
	}

	@Override
	public ArrayList<DownloadJob> getAllDownloadJobs() {
		// TODO Auto-generated method stub
		ArrayList<DownloadJob> downloadJobs = new ArrayList<DownloadJob>();
		Cursor cursor = null;
		try {
			cursor = mDBHelper.query(DB_DOWNLOAD_TABEL, null, null, null);
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
				DownloadJobBuilder downloadJobBuilder = new DownloadJobBuilder();

				while (!cursor.isAfterLast()) {
					DownloadJob downloadJob = downloadJobBuilder.build(cursor);
					downloadJobs.add(downloadJob);
					cursor.moveToNext();
				}
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			mDBHelper.close();
		}
		return downloadJobs;
	}

	@Override
	public void remove(DownloadJob job) {
		// TODO Auto-generated method stub
		try {
			mDBHelper.delete(DB_DOWNLOAD_TABEL, "resourceID=?",
					mDBHelper.toArgs(job.getResourceEntity().getResourceID()));
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			mDBHelper.close();
		}
	}

}
