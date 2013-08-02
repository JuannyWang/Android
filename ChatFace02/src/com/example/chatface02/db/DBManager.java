package com.example.chatface02.db;

import java.util.ArrayList;
import java.util.List;

import com.example.chatface02.entity.Contactor;
import com.example.chatface02.entity.Messages;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
	private DBHelper dbHelper;
	private SQLiteDatabase db;

	public DBManager(Context context) {
		dbHelper = new DBHelper(context);
		// 因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0,
		// mFactory);
		// 所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
		db = dbHelper.getWritableDatabase();
	}

	/**
	 * Add Friends
	 */
	public void addPerson(Contactor Newcontactor) {
		db.beginTransaction(); // 开始事务
		try {
			db.execSQL(
					"INSERT INTO FriendsList VALUES(null, ?, ?, ?,?)",
					new Object[] { Newcontactor.getName(),
							Newcontactor.getNormalPhone(),
							Newcontactor.getNewTel(), Newcontactor.getGroups() });

			db.setTransactionSuccessful(); // 设置事务成功完成
		} finally {
			db.endTransaction(); // 结束事务
		}
	}

	/**
	 * Update NewPhoneNum
	 */
	public void updateNewPhoneNum(Contactor contactor) {
		ContentValues cv = new ContentValues();
		cv.put("NewTel", contactor.getNewTel());
		db.update("FriendsList", cv, "Name = ?",
				new String[] { contactor.getName() });
	}

	/**
	 * Update NormalPhoneNum
	 */
	public void updateNormalPhoneNum(Contactor contactor) {
		ContentValues cv = new ContentValues();
		cv.put("NormalPhone", contactor.getNormalPhone());
		db.update("FriendsList", cv, "Name = ?",
				new String[] { contactor.getName() });
	}

	/**
	 * Update Group
	 */
	public void updateGroup(Contactor contactor) {
		ContentValues cv = new ContentValues();
		cv.put("Groups", contactor.getGroups());
		db.update("FriendsList", cv, "Name = ?",
				new String[] { contactor.getName() });
	}

	/**
	 * delete friends
	 * 
	 */
	public void deleteOldFriends(Contactor contactor) {
		db.delete("FriendsList", "NewTel = ?",
				new String[] { contactor.getNewTel() });
	}

	/**
	 * query all persons, return list
	 * 
	 * @return List<Person>
	 */
	public List<Contactor> query() {
		ArrayList<Contactor> friends = new ArrayList<Contactor>();
		Cursor c = queryTheCursor();
		String sname, stel, sgroup, sid;
		while (c.moveToNext()) {
			Contactor friend = new Contactor();
			sname = c.getString(c.getColumnIndex("Name"));

			sid = c.getString(c.getColumnIndex("NewTel"));
			stel = c.getString(c.getColumnIndex("NormalPhone"));

			sgroup = c.getString(c.getColumnIndex("Groups"));
			friend.setNewTel(sid);
			friend.setName(sname);
			friend.setGroups(sgroup);
			friend.setNormalPhone(stel);
			friends.add(friend);
		}
		c.close();
		return friends;
	}

	/**
	 * query all friends, return cursor
	 * 
	 * @return Cursor
	 */
	public Cursor queryTheCursor() {
		Cursor c = db.rawQuery("SELECT * FROM FriendsList", null);
		return c;
	}

	/**
	 * Add Message
	 */
	public void addMessage(Messages messages) {
		db.beginTransaction(); // 开始事务
		try {
			db.execSQL("INSERT INTO MESSAGE VALUES(null, ?, ?, ?,?)",
					new Object[] { messages.getName(), messages.getNewTel(),
							messages.getMsgs(),messages.getISREAD() });

			db.setTransactionSuccessful(); // 设置事务成功完成
		} finally {
			db.endTransaction(); // 结束事务
		}
	}
	/**
	 * delete Message
	 * 
	 */
	public void deleteMessages(Messages message) {
		db.delete("MESSAGE", "MnewTel = ?",
				new String[] { message.getNewTel() });
	}
	/**
	 * query all msgs, return list
	 * 
	 * @return List<Messages>
	 */
	public List<Messages> queryMsgs() {
		ArrayList<Messages> messages = new ArrayList<Messages>();
		Cursor c = queryTheCursor();
		String sname, newTel, msgs;
		int isread;
		while (c.moveToNext()) {
			Messages message = new Messages();
			sname = c.getString(c.getColumnIndex("Mname"));

			newTel = c.getString(c.getColumnIndex("MnewTel"));
			msgs = c.getString(c.getColumnIndex("msgs"));
			isread = c.getInt(c.getColumnIndex("ISREAD"));
			message.setNewTel(newTel);
			message.setName(sname);
			message.setMsgs(msgs);

			messages.add(message);
		}
		c.close();
		return messages;
	}
//	/**
//	 * query  msgs, return list
//	 * 
//	 * @return List<Messages>
//	 */
//	public List<Messages> queryMsgs(String newPhone) {
//		ArrayList<Messages> messages = new ArrayList<Messages>();
//		Cursor c = queryTheCursor();
//		String sname, newTel, msgs;
//		int isread;
//		db.beginTransaction(); // 开始事务
//		try {
//			db.rawQuery("select * from MESSAGE where MnewTel="+newPhone, new String[]{});
//
//			db.setTransactionSuccessful(); // 设置事务成功完成
//		} finally {
//			db.endTransaction(); // 结束事务
//		}
//		c.close();
//		return messages;
//	}

	/**
	 * close database
	 */
	public void closeDB() {
		db.close();
	}
}
