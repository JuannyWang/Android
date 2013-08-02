package com.cqupt.mobilestudiesdemo.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBHelper {
	private SQLiteDatabase database;
	
	public DBHelper() {
		database = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/"
				+ DBManager.DB_NAME, null);
	}
	
	public Cursor GetResourceLists(String groupName) {
		Cursor cur=null;
		try {
			/*database = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/"
					+ DBManager.DB_NAME, null);*/
			 cur= database
					.rawQuery(
							"select resourceTitle ,resourcePath from resourceList where GroupID=(select resourceGroupID from resourceGroup where resourceGroupName=?)",
							new String[] { groupName });
			//database.close();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("DBHelper............", e.toString());
		}	
		return cur;
	}

	public Cursor GetWordBookLists(String userID)
	{
		Cursor cur=null;
		try {
			 cur= database
					.rawQuery(
							"select * from wordBook where userId=?",
							new String[] { userID });
			//database.close();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("DBHelper............", e.toString());
		}	
		return cur;
	}
	
	public Cursor GetWordsLists(String userID, String bookID)
	{
		Cursor cur=null;
		try {
			 cur= database
					.rawQuery(
							"select * from userWords where userID=? and bookID=?",
							new String[] { userID ,bookID});
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("DBHelper............", e.toString());
		}	
		return cur;
	}
	/*public int CreateWordBook(WordBook wordBook)
	{
		try {
			Cursor cur = database
					.rawQuery("select wordbookID from wordBook where wordbookName=? and userId=?",new String[]{wordBook.getBookName(),wordBook.getUserId()});
			if (cur!=null) {
				if (cur.getCount()==0) {
					database.execSQL(
							"insert into wordBook(wordBookName,bookCreateTime,userId) values(?,?,?)",
							new Object[] {wordBook.getBookName(),wordBook.getCreateTime() ,wordBook.getUserId()});
					return 1;
				}
				else {
					return -1;
				}
			}
			else {
				return 0;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Insert data fail");
		}
		return 0;
	}*/
	
	public int DeleteWordBook(String bookID)
	{
		try {
			database.execSQL("delete from wordBook where wordbookID=?",new Object[]{bookID});
			database.execSQL("delete from userWords where bookID=?",new Object[]{bookID});
			return 2;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}

	public Cursor GetWordsList(String userID)
	{
		Cursor cur=null;
		try {
			 cur= database
					.rawQuery(
							"select wordText ,wordTextDescript from userWords where userID=?",
							new String[] { userID });
			//database.close();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("DBHelper............", e.toString());
		}	
		return cur;
	}
	

	/*public int InsertWords(Word word)
	{
		try {
			Cursor cur = database
					.rawQuery("select wordText from userWords where wordText=? and bookID=? and userID=?",new String[]{word.getWordText(),word.getBookID(),word.getUserID()});
			if (cur!=null) {
				if (cur.getCount()==0) {
					database.execSQL(
							"insert into userWords(wordText,wordTextDescript,wordCreateTime,userID,bookID) values(?,?,?,?,?)",
							new Object[] { word.getWordText(),word.getWordTextDescript(),word.getWordCreateTime(),word.getUserID(),word.getBookID() });
					return 1;
				}
				else {
					return -1;
				}
			}
			else {
				return 0;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Insert data fail");
		}
		return 0;
	}*/
	

	public int DeleteWords(String wordText)
	{
		try {
			database.execSQL("delete from userWords where wordText=?",new Object[]{wordText});
			return 2;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}
	

	/*public int UpdateWords(Word newWord,Word oldWord)
	{
		try {
			Cursor cur = database
					.rawQuery("select * from userWords where wordText=? and bookID=? and userID=?",new String[]{newWord.getWordText(),oldWord.getBookID(),oldWord.getUserID()});
			if (cur!=null) {
				if (cur.getCount()==1) {
				database.execSQL("update userWords set wordText=?,wordTextDescript=? where wordText=?",new Object[]{newWord.getWordText(),newWord.getWordTextDescript(),oldWord.getWordText()});
				return 1;
				}
				else {
					return -1;
				}
			}
			else {
				return 0;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}*/
	
	public Cursor GetNoteList(String userID)
	{
		Cursor cur=null;
		try {
			 cur= database
					.rawQuery(
							"select noteDescription,noteTime from userNote where userID=?",
							new String[] { userID });
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("DBHelper............", e.toString());
		}	
		return cur;
	}
	
	public void closeDB()
	{
		try {
			if(database.isOpen())
			{
				database.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
