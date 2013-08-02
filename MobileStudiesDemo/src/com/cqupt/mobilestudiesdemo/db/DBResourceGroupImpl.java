package com.cqupt.mobilestudiesdemo.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import com.cqupt.mobilestudiesdemo.db.DataBaseImpl.DBHelper;

import com.cqupt.mobilestudiesdemo.entity.ResourceGroupEntity;

public class DBResourceGroupImpl implements DBResourceGroup {
	private DBHelper mDBHelper = null;
	private DataBaseImpl dataBaseImpl = null;

	public DBResourceGroupImpl(Context context) {
		dataBaseImpl = new DataBaseImpl(context);
		mDBHelper = dataBaseImpl.getDBHelper();
	}

	@Override
	public void addResourceGroup(ResourceGroupEntity resourceGroupEntity) {
		// TODO Auto-generated method stub
		DBResourceGroupBuilder builder = new DBResourceGroupBuilder();
		ContentValues contentValues = builder.deconstruct(resourceGroupEntity);
		try {
			mDBHelper.insertOrUpdate(DataBaseImpl.DB_RESOURCE_GROUP_TABEL,
					contentValues, "resourceGroupID",
					resourceGroupEntity.getResourceGroupID());
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			mDBHelper.close();
		}
	}

	@Override
	public void addResourceGroups(
			ArrayList<ResourceGroupEntity> resourceGroupEntities) {
		// TODO Auto-generated method stub
		DBResourceGroupBuilder builder = new DBResourceGroupBuilder();
		mDBHelper.beginTransaction();
		try {
			for (ResourceGroupEntity resourceGroupEntity : resourceGroupEntities) {
				ContentValues contentValues = builder
						.deconstruct(resourceGroupEntity);
				mDBHelper.insertOrUpdate(DataBaseImpl.DB_RESOURCE_GROUP_TABEL,
						contentValues, "resourceGroupID",
						resourceGroupEntity.getResourceGroupID());
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
	public ResourceGroupEntity getResourceGroup(int resourceGroupID) {
		// TODO Auto-generated method stub
		ResourceGroupEntity resourceGroupEntity = null;
		Cursor cursor = null;
		try {
			cursor = mDBHelper
					.query(DataBaseImpl.DB_RESOURCE_GROUP_TABEL, null,
							"resourceGroupID=?",
							DBHelper.toArgs(resourceGroupID));
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
				DBResourceGroupBuilder builder = new DBResourceGroupBuilder();
				resourceGroupEntity = builder.build(cursor);
			}
		} catch (SQLException e) {
			// TODO: handle exception
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			mDBHelper.close();
		}
		return resourceGroupEntity;
	}

	@Override
	public ArrayList<ResourceGroupEntity> getRGsFromRST(int resourceSubTypeID,
			int index, int size) {
		// TODO Auto-generated method stub
		ArrayList<ResourceGroupEntity> resourceGroupEntities = null;
		Cursor cursor = null;
		try {
			String sql = String
					.format("Select * From %s Where resourceSubTypeID=? Limit ? Offset ?",
							DataBaseImpl.DB_RESOURCE_GROUP_TABEL);

			String[] selectionArgs = new String[] {
					String.valueOf(resourceSubTypeID), String.valueOf(size),
					String.valueOf(index) };
			cursor = mDBHelper.rawQuery(sql, selectionArgs);
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
				resourceGroupEntities = new ArrayList<ResourceGroupEntity>();
				DBResourceGroupBuilder builder = new DBResourceGroupBuilder();
				while (!cursor.isAfterLast()) {
					ResourceGroupEntity resourceGroupEntity = builder
							.build(cursor);
					resourceGroupEntities.add(resourceGroupEntity);
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
		return resourceGroupEntities;
	}

	@Override
	public ArrayList<ResourceGroupEntity> getRGsFromRT(int resourceTypeID,
			int index, int size) {
		// TODO Auto-generated method stub
		ArrayList<ResourceGroupEntity> resourceGroupEntities = null;
		Cursor cursor = null;
		try {
			String sql = String.format(
					"Select * From %s Where resourceTypeID=? Limit ? Offset ?",
					DataBaseImpl.DB_RESOURCE_GROUP_TABEL);

			String[] selectionArgs = new String[] {
					String.valueOf(resourceTypeID), String.valueOf(size),
					String.valueOf(index) };
			cursor = mDBHelper.rawQuery(sql, selectionArgs);
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
				resourceGroupEntities = new ArrayList<ResourceGroupEntity>();
				DBResourceGroupBuilder builder = new DBResourceGroupBuilder();
				while (!cursor.isAfterLast()) {
					ResourceGroupEntity resourceGroupEntity = builder
							.build(cursor);
					resourceGroupEntities.add(resourceGroupEntity);
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
		return resourceGroupEntities;
	}

	@Override
	public ArrayList<ResourceGroupEntity> getAllRGsFromRST(int resourceSubTypeID) {
		// TODO Auto-generated method stub
		ArrayList<ResourceGroupEntity> resourceGroupEntities = null;
		Cursor cursor = null;
		try {
			String[] selectionArgs = new String[] { String
					.valueOf(resourceSubTypeID) };
			cursor = mDBHelper.query(DataBaseImpl.DB_RESOURCE_GROUP_TABEL,
					null, "resourceSubTypeID=?", selectionArgs);
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
				resourceGroupEntities = new ArrayList<ResourceGroupEntity>();
				DBResourceGroupBuilder builder = new DBResourceGroupBuilder();
				while (!cursor.isAfterLast()) {
					ResourceGroupEntity resourceGroupEntity = builder
							.build(cursor);
					resourceGroupEntities.add(resourceGroupEntity);
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
		return resourceGroupEntities;
	}

	@Override
	public ArrayList<ResourceGroupEntity> getAllRGsFromRT(int resourceTypeID) {
		// TODO Auto-generated method stub
		ArrayList<ResourceGroupEntity> resourceGroupEntities = null;
		Cursor cursor = null;
		try {
			String[] selectionArgs = new String[] { String
					.valueOf(resourceTypeID) };
			cursor = mDBHelper.query(DataBaseImpl.DB_RESOURCE_GROUP_TABEL,
					null, "resourceTypeID=?", selectionArgs);
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
				resourceGroupEntities = new ArrayList<ResourceGroupEntity>();
				DBResourceGroupBuilder builder = new DBResourceGroupBuilder();
				while (!cursor.isAfterLast()) {
					ResourceGroupEntity resourceGroupEntity = builder
							.build(cursor);
					resourceGroupEntities.add(resourceGroupEntity);
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
		return resourceGroupEntities;
	}

	@Override
	public void updateResourceGroup(ResourceGroupEntity resourceGroupEntity) {
		// TODO Auto-generated method stub
		DBResourceGroupBuilder builder = new DBResourceGroupBuilder();
		ContentValues contentValues = builder.deconstruct(resourceGroupEntity);
		try {
			mDBHelper.insertOrUpdate(DataBaseImpl.DB_RESOURCE_GROUP_TABEL,
					contentValues, "resourceGroupID",
					resourceGroupEntity.getResourceGroupID());
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			mDBHelper.close();
		}
	}

	@Override
	public void deleteResourceGroup(int resourceGroupID) {
		// TODO Auto-generated method stub
		try {
			String[] selectionArgs = new String[] { String
					.valueOf(resourceGroupID) };
			mDBHelper.delete(DataBaseImpl.DB_RESOURCE_GROUP_TABEL,
					"resourceGroupID=?", selectionArgs);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			mDBHelper.close();
		}
	}

	@Override
	public void deleteRGsFromRST(int resourceSubTypeID) {
		// TODO Auto-generated method stub
		try {
			String[] selectionArgs = new String[] { String
					.valueOf(resourceSubTypeID) };
			mDBHelper.delete(DataBaseImpl.DB_RESOURCE_GROUP_TABEL,
					"resourceSubTypeID=?", selectionArgs);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			mDBHelper.close();
		}
	}

	@Override
	public void deleteRGsFromRT(int resourceTypeID) {
		// TODO Auto-generated method stub
		try {
			String[] selectionArgs = new String[] { String
					.valueOf(resourceTypeID) };
			mDBHelper.delete(DataBaseImpl.DB_RESOURCE_GROUP_TABEL,
					"resourceTypeID=?", selectionArgs);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			mDBHelper.close();
		}
	}

	@Override
	public void deleteAllResourceGroup() {
		// TODO Auto-generated method stub
		try {
			mDBHelper.delete(DataBaseImpl.DB_RESOURCE_GROUP_TABEL, null, null);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			mDBHelper.close();
		}
	}

}
