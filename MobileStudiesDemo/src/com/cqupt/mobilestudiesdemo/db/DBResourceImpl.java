package com.cqupt.mobilestudiesdemo.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import com.cqupt.mobilestudiesdemo.db.DataBaseImpl.DBHelper;

import com.cqupt.mobilestudiesdemo.entity.ResourceEntity;

public class DBResourceImpl implements DBResource {
	private DBHelper mDBHelper = null;
	private DataBaseImpl dataBaseImpl = null;

	public DBResourceImpl(Context context) {
		dataBaseImpl = new DataBaseImpl(context);
		mDBHelper = dataBaseImpl.getDBHelper();
	}

	@Override
	public void addResource(ResourceEntity resourceEntity) {
		// TODO Auto-generated method stub
		DBResourceBuilder builder = new DBResourceBuilder();
		ContentValues contentValues = builder.deconstruct(resourceEntity);
		try {
			mDBHelper
					.insertOrUpdate(DataBaseImpl.DB_RESOURCE_TABEL,
							contentValues, "resourceID",
							resourceEntity.getResourceID());
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			mDBHelper.close();
		}

	}

	@Override
	public void addResource(ArrayList<ResourceEntity> resourceEntities) {
		// TODO Auto-generated method stub
		DBResourceBuilder builder = new DBResourceBuilder();
//		mDBHelper.beginTransaction();
		try {
			for (ResourceEntity resourceEntity : resourceEntities) {
				ContentValues contentValues = builder
						.deconstruct(resourceEntity);
				mDBHelper.insertOrUpdate(DataBaseImpl.DB_RESOURCE_TABEL,
						contentValues, "resourceID",
						resourceEntity.getResourceID());
			}
//			mDBHelper.setTransactionSuccessful();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
//			mDBHelper.endTransaction();
			mDBHelper.close();
		}
	}

	@Override
	public ResourceEntity getResource(int resourceID) {
		// TODO Auto-generated method stub
		ResourceEntity resourceEntity = null;
		Cursor cursor = null;
		try {
			cursor = mDBHelper.query(DataBaseImpl.DB_RESOURCE_TABEL, null,
					"resourceID=?", DBHelper.toArgs(resourceID));
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
				DBResourceBuilder builder = new DBResourceBuilder();
				resourceEntity = builder.build(cursor);
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
		return resourceEntity;
	}

	@Override
	public ArrayList<ResourceEntity> getResourcesFromRG(int resourceGroupID,
			int index, int size) {
		// TODO Auto-generated method stub
		ArrayList<ResourceEntity> resourceEntities = null;
		Cursor cursor = null;
		try {
			String sql = String
					.format("Select * From %s Where resourceGroupID=? Limit ? Offset ?",
							DataBaseImpl.DB_RESOURCE_TABEL);
			String[] selectionArgs = new String[] {
					String.valueOf(resourceGroupID), String.valueOf(size),
					String.valueOf(index) };

			cursor = mDBHelper.rawQuery(sql, selectionArgs);
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
				resourceEntities = new ArrayList<ResourceEntity>();
				DBResourceBuilder builder = new DBResourceBuilder();
				while (!cursor.isAfterLast()) {
					ResourceEntity resourceEntity = builder.build(cursor);
					resourceEntities.add(resourceEntity);
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
		return resourceEntities;
	}

	@Override
	public ArrayList<ResourceEntity> getResourcesFromRST(int resourceSubTypeID,
			int index, int size) {
		// TODO Auto-generated method stub
		ArrayList<ResourceEntity> resourceEntities = null;
		Cursor cursor = null;
		try {
			String sql = String
					.format("Select * From %s Where resourceSubTypeID=? Limit ? Offset ?",
							DataBaseImpl.DB_RESOURCE_TABEL);
			String[] selectionArgs = new String[] {
					String.valueOf(resourceSubTypeID), String.valueOf(size),
					String.valueOf(index) };

			cursor = mDBHelper.rawQuery(sql, selectionArgs);
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
				resourceEntities = new ArrayList<ResourceEntity>();
				DBResourceBuilder builder = new DBResourceBuilder();
				while (!cursor.isAfterLast()) {
					ResourceEntity resourceEntity = builder.build(cursor);
					resourceEntities.add(resourceEntity);
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
		return resourceEntities;
	}

	@Override
	public ArrayList<ResourceEntity> getResourcesFromRT(int resourceTypeID,
			int index, int size) {
		// TODO Auto-generated method stub
		ArrayList<ResourceEntity> resourceEntities = null;
		Cursor cursor = null;
		try {
			String sql = String.format(
					"Select * From %s Where resourceTypeID=? Limit ? Offset ?",
					DataBaseImpl.DB_RESOURCE_TABEL);
			String[] selectionArgs = new String[] {
					String.valueOf(resourceTypeID), String.valueOf(size),
					String.valueOf(index) };

			cursor = mDBHelper.rawQuery(sql, selectionArgs);
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
				resourceEntities = new ArrayList<ResourceEntity>();
				DBResourceBuilder builder = new DBResourceBuilder();
				while (!cursor.isAfterLast()) {
					ResourceEntity resourceEntity = builder.build(cursor);
					resourceEntities.add(resourceEntity);
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
		return resourceEntities;
	}

	@Override
	public ArrayList<ResourceEntity> getAllResourcesFromRG(int resourceGroupID) {
		// TODO Auto-generated method stub
		ArrayList<ResourceEntity> resourceEntities = null;
		Cursor cursor = null;
		try {
			String[] selectionArgs = new String[] { String
					.valueOf(resourceGroupID) };

			cursor = mDBHelper.query(DataBaseImpl.DB_RESOURCE_TABEL, null,
					"resourceGroupID=?", selectionArgs);
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
				resourceEntities = new ArrayList<ResourceEntity>();
				DBResourceBuilder builder = new DBResourceBuilder();
				while (!cursor.isAfterLast()) {
					ResourceEntity resourceEntity = builder.build(cursor);
					resourceEntities.add(resourceEntity);
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
		return resourceEntities;
	}

	@Override
	public ArrayList<ResourceEntity> getAllResourcesFromRST(
			int resourceSubTypeID) {
		// TODO Auto-generated method stub
		ArrayList<ResourceEntity> resourceEntities = null;
		Cursor cursor = null;
		try {
			String[] selectionArgs = new String[] { String
					.valueOf(resourceSubTypeID) };

			cursor = mDBHelper.query(DataBaseImpl.DB_RESOURCE_TABEL, null,
					"resourceSubTypeID=?", selectionArgs);
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
				resourceEntities = new ArrayList<ResourceEntity>();
				DBResourceBuilder builder = new DBResourceBuilder();
				while (!cursor.isAfterLast()) {
					ResourceEntity resourceEntity = builder.build(cursor);
					resourceEntities.add(resourceEntity);
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
		return resourceEntities;
	}

	@Override
	public ArrayList<ResourceEntity> getAllResourcesFromRT(int resourceTypeID) {
		// TODO Auto-generated method stub
		ArrayList<ResourceEntity> resourceEntities = null;
		Cursor cursor = null;
		try {
			String[] selectionArgs = new String[] { String
					.valueOf(resourceTypeID) };

			cursor = mDBHelper.query(DataBaseImpl.DB_RESOURCE_TABEL, null,
					"resourceTypeID=?", selectionArgs);
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
				resourceEntities = new ArrayList<ResourceEntity>();
				DBResourceBuilder builder = new DBResourceBuilder();
				while (!cursor.isAfterLast()) {
					ResourceEntity resourceEntity = builder.build(cursor);
					resourceEntities.add(resourceEntity);
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
		return resourceEntities;
	}

	@Override
	public void updateResource(ResourceEntity resourceEntity) {
		// TODO Auto-generated method stub
		DBResourceBuilder builder = new DBResourceBuilder();
		ContentValues contentValues = builder.deconstruct(resourceEntity);
		try {
			mDBHelper
					.insertOrUpdate(DataBaseImpl.DB_RESOURCE_TABEL,
							contentValues, "resourceID",
							resourceEntity.getResourceID());
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			mDBHelper.close();
		}
	}

	@Override
	public void deleteResource(int resourceID) {
		// TODO Auto-generated method stub
		try {
			String[] selectionArgs = new String[] { String.valueOf(resourceID) };
			mDBHelper.delete(DataBaseImpl.DB_RESOURCE_TABEL, "resourceID=?",
					selectionArgs);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			mDBHelper.close();
		}
	}

	@Override
	public void deleteResourcesFromRG(int resourceGroupID) {
		// TODO Auto-generated method stub
		try {
			String[] selectionArgs = new String[] { String
					.valueOf(resourceGroupID) };
			mDBHelper.delete(DataBaseImpl.DB_RESOURCE_TABEL,
					"resourceGroupID=?", selectionArgs);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			mDBHelper.close();
		}
	}

	@Override
	public void deleteResourcesFromRST(int resourceSubTypeID) {
		// TODO Auto-generated method stub
		try {
			String[] selectionArgs = new String[] { String
					.valueOf(resourceSubTypeID) };
			mDBHelper.delete(DataBaseImpl.DB_RESOURCE_TABEL,
					"resourceSubTypeID=?", selectionArgs);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			mDBHelper.close();
		}
	}

	@Override
	public void deleteResourcesFromRT(int resourceTypeID) {
		// TODO Auto-generated method stub
		try {
			String[] selectionArgs = new String[] { String
					.valueOf(resourceTypeID) };
			mDBHelper.delete(DataBaseImpl.DB_RESOURCE_TABEL,
					"resourceTypeID=?", selectionArgs);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			mDBHelper.close();
		}
	}

	@Override
	public void deleteAllResource() {
		// TODO Auto-generated method stub
		try {
			mDBHelper.delete(DataBaseImpl.DB_RESOURCE_TABEL, null, null);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			mDBHelper.close();
		}
	}
}
