package com.cqupt.mobilestudiesdemo.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import com.cqupt.mobilestudiesdemo.db.DataBaseImpl.DBHelper;

import com.cqupt.mobilestudiesdemo.entity.ResourceSubTypeEntity;

public class DBResourceSubTypeImpl implements DBResourceSubType {
	private DBHelper mDBHelper = null;
	private DataBaseImpl dataBaseImpl = null;

	public DBResourceSubTypeImpl(Context context) {
		dataBaseImpl = new DataBaseImpl(context);
		mDBHelper = dataBaseImpl.getDBHelper();
	}

	@Override
	public void addResourceSubType(ResourceSubTypeEntity resourceSubTypeEntity) {
		// TODO Auto-generated method stub
		DBResourceSubTypeBuilder builder = new DBResourceSubTypeBuilder();
		ContentValues contentValues = builder
				.deconstruct(resourceSubTypeEntity);
		try {
			mDBHelper.insertOrUpdate(DataBaseImpl.DB_RESOURCE_SUB_TYPE_TABEL,
					contentValues, "resourceSubTypeID",
					resourceSubTypeEntity.getResourceSubTypeID());
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			mDBHelper.close();
		}
	}

	@Override
	public void addResourceSubTyepes(
			ArrayList<ResourceSubTypeEntity> resourceSubTypeEntities) {
		// TODO Auto-generated method stub
		DBResourceSubTypeBuilder builder = new DBResourceSubTypeBuilder();
		mDBHelper.beginTransaction();

		try {
			for (ResourceSubTypeEntity resourceSubTypeEntity : resourceSubTypeEntities) {
				ContentValues contentValues = builder
						.deconstruct(resourceSubTypeEntity);
				mDBHelper.insertOrUpdate(
						DataBaseImpl.DB_RESOURCE_SUB_TYPE_TABEL, contentValues,
						"resourceSubTypeID",
						resourceSubTypeEntity.getResourceSubTypeID());
			}
			mDBHelper.setTransactionSuccessful();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			mDBHelper.endTransaction();
			mDBHelper.close();
		}
	}

	@Override
	public ResourceSubTypeEntity getResourceSubType(int resourceSubTypeID) {
		// TODO Auto-generated method stub
		ResourceSubTypeEntity resourceSubTypeEntity = null;
		Cursor cursor = null;
		try {
			cursor = mDBHelper.query(DataBaseImpl.DB_RESOURCE_SUB_TYPE_TABEL,
					null, "resourceSubTypeID=?",
					DBHelper.toArgs(resourceSubTypeID));
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
				DBResourceSubTypeBuilder builder = new DBResourceSubTypeBuilder();
				resourceSubTypeEntity = builder.build(cursor);
			}
		} catch (SQLException e) {
			// TODO: handle exception
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			mDBHelper.close();
		}
		return resourceSubTypeEntity;
	}

	@Override
	public ArrayList<ResourceSubTypeEntity> getResourceSubTypes(
			int resourceTypeID, int index, int size) {
		// TODO Auto-generated method stub
		ArrayList<ResourceSubTypeEntity> resourceSubTypeEntities = null;
		Cursor cursor = null;
		try {
			String sql = String.format(
					"Select * From %s Where resourceTypeID=? Limit ? Offset ?",
					DataBaseImpl.DB_RESOURCE_SUB_TYPE_TABEL);

			String[] selectionArgs = new String[] {
					String.valueOf(resourceTypeID), String.valueOf(size),
					String.valueOf(index) };
			cursor = mDBHelper.rawQuery(sql, selectionArgs);
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
				resourceSubTypeEntities = new ArrayList<ResourceSubTypeEntity>();
				DBResourceSubTypeBuilder builder = new DBResourceSubTypeBuilder();
				while (!cursor.isAfterLast()) {
					ResourceSubTypeEntity resourceSubTypeEntity = builder
							.build(cursor);
					resourceSubTypeEntities.add(resourceSubTypeEntity);
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
		return resourceSubTypeEntities;
	}

	@Override
	public ArrayList<ResourceSubTypeEntity> getAllResourceSubTypes(
			int resourceTypeID) {
		// TODO Auto-generated method stub
		ArrayList<ResourceSubTypeEntity> resourceSubTypeEntities = null;
		Cursor cursor = null;
		try {
			cursor = mDBHelper.query(DataBaseImpl.DB_RESOURCE_SUB_TYPE_TABEL,
					null, "resourceTypeID=?", DBHelper.toArgs(resourceTypeID));
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
				resourceSubTypeEntities = new ArrayList<ResourceSubTypeEntity>();
				DBResourceSubTypeBuilder builder = new DBResourceSubTypeBuilder();
				while (!cursor.isAfterLast()) {
					ResourceSubTypeEntity resourceSubTypeEntity = builder
							.build(cursor);
					resourceSubTypeEntities.add(resourceSubTypeEntity);
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
		return resourceSubTypeEntities;
	}

	@Override
	public void updateResourceSubType(
			ResourceSubTypeEntity resourceSubTypeEntity) {
		// TODO Auto-generated method stub
		DBResourceSubTypeBuilder builder = new DBResourceSubTypeBuilder();
		ContentValues contentValues = builder
				.deconstruct(resourceSubTypeEntity);
		try {
			mDBHelper.insertOrUpdate(DataBaseImpl.DB_RESOURCE_SUB_TYPE_TABEL,
					contentValues, "resourceSubTypeID",
					resourceSubTypeEntity.getResourceSubTypeID());
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			mDBHelper.close();
		}
	}

	@Override
	public void deleteResourceSubType(int resourceSubTypeID) {
		// TODO Auto-generated method stub
		try {
			String[] selectionArgs = new String[] { String
					.valueOf(resourceSubTypeID) };
			mDBHelper.delete(DataBaseImpl.DB_RESOURCE_SUB_TYPE_TABEL,
					"resourceSubTypeID=?", selectionArgs);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			mDBHelper.close();
		}
	}

	@Override
	public void deleteResourceSubTypes(int resourceTypeID) {
		// TODO Auto-generated method stub
		try {
			String[] selectionArgs = new String[] { String
					.valueOf(resourceTypeID) };
			mDBHelper.delete(DataBaseImpl.DB_RESOURCE_SUB_TYPE_TABEL,
					"resourceTypeID=?", selectionArgs);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			mDBHelper.close();
		}
	}

	@Override
	public void deleteAllResourceSubTypes() {
		// TODO Auto-generated method stub
		try {
			mDBHelper.delete(DataBaseImpl.DB_RESOURCE_SUB_TYPE_TABEL, null,
					null);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			mDBHelper.close();
		}
	}

}
