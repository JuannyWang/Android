package com.cqupt.mobilestudiesdemo.listener;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;

import com.cqupt.mobilestudiesdemo.activity.R;

/* 创建上下文菜单监听器 */
public class ContextMenuListener implements OnCreateContextMenuListener {
	/* 上下文菜单项 */
	private int pos;
	private static final int EDIT_ITEM = Menu.FIRST;
	private static final int DELETE_ITEM = Menu.FIRST + 1;
	private static final int SEND_SHARE = Menu.FIRST + 2;
	private static final int SET_AS = Menu.FIRST + 3;
	private boolean isselect;
	private Context context;

	public ContextMenuListener( Context context){
		this.context = context;
	}
	public void onCreateContextMenu(ContextMenu menu, View view,
			ContextMenuInfo info) {
		menu.setHeaderTitle("相关操作");
//		menu.setHeaderIcon(R.drawable.ic_launcher);
		menu.add(0, EDIT_ITEM, 0, "编辑");
		menu.add(0, DELETE_ITEM, 0, "删除");
		menu.add(0, SEND_SHARE, 0, "分享");
		menu.add(0, SET_AS, 0, "设置操作");
		final AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) info;
		pos = menuInfo.position;
	}

	/* 上下文菜单的某一项被点击时回调该方法 */
	public boolean onContextItemSelected(Context context, MenuItem item) {
		switch (item.getItemId()) {
		case EDIT_ITEM: // 编辑
			break;

		case DELETE_ITEM: // 删除
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage("您确定要删除这个联系人吗？")
					.setPositiveButton("是",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									// deletePerson(pos); // 从列表中删除音乐
									// deleteDBPreson(pos); // 从sdcard中删除音乐
									// initList(); // 从新获得列表中要显示的数据
									// adapter.notifyDataSetChanged(); // 更新列表UI
								}
							}).setNegativeButton("否", null);
			AlertDialog ad = builder.create();
			ad.show();
			break;

		case SEND_SHARE:// 分享被选中的歌曲
			// ShareMusicFile(pos);
			break;

		case SET_AS:// 将专辑列表中被选中的歌曲设置为...
			// setEffects();
			break;

		}
		return true;
	}
}
