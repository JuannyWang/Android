package com.example.chatface02;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.chatface02.db.DBManager;
import com.example.chatface02.entity.Contactor;

public class ContactorsListActivity extends Activity {

	private ListView contactorsListView;
	private DBManager mgr;
	private EditText nameEdit, sexEdit, newPhoneEdit, normalEdit;
	private ArrayList<Map<String, String>> list;
	private List<Contactor> friends;
	/* 上下文菜单项 */
	private int pos;
	private static final int EDIT_ITEM = Menu.FIRST;
	private static final int DELETE_ITEM = Menu.FIRST + 1;
	private static final int SEND_SHARE = Menu.FIRST + 2;
	private static final int SET_AS = Menu.FIRST + 3;
	private boolean isselect;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_contactors);

		contactorsListView = (ListView) findViewById(R.id.contactorsListView);
		contactorsListView.setOnItemClickListener(new ListItemClickListener());
		contactorsListView.setOnCreateContextMenuListener(new ContextMenuListener());
		initList();
	}
	
	
	public void initList(){
		mgr = new DBManager(this);
		friends = mgr.query();
		list = new ArrayList<Map<String, String>>();
		for (Contactor friend : friends) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("name", friend.getName());
			//map.put("newPhoneNum", friend.getID());
			list.add(map);
			Log.v("DB", friend.getName());
		}
		Log.v("DB", "is got");
		SimpleAdapter adapter = new SimpleAdapter(
				ContactorsListActivity.this, list,
				R.layout.contactor_list_item, new String[] { "name"
						 }, new int[] { R.id.toucherName
						});
		contactorsListView.setAdapter(adapter);	

	}

	class ListItemClickListener implements OnItemClickListener {

		public void onItemClick(AdapterView<?> arg0, View view, int position,
				long id) {
			Contactor Call_person = friends.get(position);
			Log.v("Intent PersonDetailCall", Call_person+"");

			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putString("CallName", Call_person.getName());
			bundle.putString("CallGroup", Call_person.getGroups());
			bundle.putString("CallNewPhone", Call_person.getNewTel());
			bundle.putString("CallNormalPhone", Call_person.getNormalPhone());
			intent.putExtras(bundle);
			intent.setClass(ContactorsListActivity.this, PersonDetailCall.class);
			startActivity(intent);

			
		}

	}
	/* 创建上下文菜单监听器 */
	class ContextMenuListener implements OnCreateContextMenuListener {

		public void onCreateContextMenu(ContextMenu menu, View view,
				ContextMenuInfo info) {
			menu.setHeaderTitle("相关操作");
			menu.setHeaderIcon(R.drawable.ic_launcher);
			menu.add(0, EDIT_ITEM, 0, "编辑");
			menu.add(0, DELETE_ITEM, 0, "删除");
			menu.add(0, SEND_SHARE, 0, "分享");
			menu.add(0, SET_AS, 0, "设置操作");
			final AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) info;
			pos = menuInfo.position;
		}
	}

	/* 上下文菜单的某一项被点击时回调该方法 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case EDIT_ITEM: // 编辑
			break;

		case DELETE_ITEM: // 删除
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("您确定要删除这个联系人吗？")
					.setPositiveButton("是",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									
								}
							}).setNegativeButton("否", null);
			AlertDialog ad = builder.create();
			ad.show();
			break;

		case SEND_SHARE:// 分享被选中的歌曲
			//ShareMusicFile(pos);
			break;

		case SET_AS:// 将专辑列表中被选中的歌曲设置为...
			//setEffects();
			break;

		}
		return true;
	}


	public void newFriendListsOnclick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.newContactorButton:

			Intent intent = new Intent();
			intent.setClass(this, PersonDetail.class);
			startActivity(intent);
			break;
		case R.id.contactorsButton_contactors:
			Intent contactorIntent = new Intent();
			contactorIntent.setClass(this, ContactorsListActivity.class);
			startActivity(contactorIntent);
			break;
		case R.id.msgButton_contactors:
			Intent receiveIntent = new Intent();
			receiveIntent.setClass(this, SMSListActivity.class);
			startActivity(receiveIntent);
			break;
		}
	}
}
