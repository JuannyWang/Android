package com.example.chatface02;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.chatface02.db.DBManager;
import com.example.chatface02.entity.Messages;
import com.example.data.Common;
import com.example.data.LocalDataFactory;
import com.example.servers.ChatService;
import com.example.utils.ChatUtils;

public class SMSListActivity extends Activity implements OnClickListener {

	private RelativeLayout newSMSLayout = null;
	private RelativeLayout newSMSTextLayout = null;
	private TableLayout chooseLayout = null;
	
	private ListView smsListView = null;
	
	private TextView newSMSText = null;
	
	private Button chooseButton = null;
	
	private boolean chooseLayoutShow = false; 
	
	private ArrayList<Map<String, String>> list;
	private List<Messages> messages;
	public static final String tag = "MsgShowActivity";
	private DBManager mgr;

	private Button newSMSBtn = null;
	private String ID;
	private Bundle bundle;
	private EditText TelEdit,smsContentEdit;
	private int pos;
	private static final int SHOW_ITEM = Menu.FIRST;
	private static final int DELETE_ITEM = Menu.FIRST + 1;
	private static final int SEND_SHARE = Menu.FIRST + 2;
	private static final int SET_AS = Menu.FIRST + 3;
	private boolean isselect;

	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_sms);
		initView();
		bindService(new Intent(this, ChatService.class), serviceConnection,
				BIND_AUTO_CREATE);
		
	
	}
	public void initView() {
		newSMSLayout = (RelativeLayout)findViewById(R.id.newsmsLayout);
		newSMSTextLayout = (RelativeLayout)findViewById(R.id.smsTopLayout);
		chooseLayout = (TableLayout)findViewById(R.id.smschooseLayout);
		smsListView = (ListView)findViewById(R.id.smsListView);
		newSMSText = (TextView)findViewById(R.id.newsmsTextView);
		chooseButton = (Button)findViewById(R.id.smsChooseButton);
		TelEdit = (EditText)findViewById(R.id.telEdit);
		smsContentEdit = (EditText)findViewById(R.id.smsContentEdit);
		newSMSLayout.setOnClickListener(this);
		newSMSTextLayout.setOnClickListener(this);
		newSMSText.setOnClickListener(this);
		chooseButton.setOnClickListener(this);
		smsListView.setOnItemClickListener(new ListItemClickListener());
		smsListView.setOnCreateContextMenuListener(new ContextMenuListener());

	}
	public void initList(){
		mgr = new DBManager(this);
		messages = mgr.queryMsgs();
		list = new ArrayList<Map<String, String>>();
		for (Messages message : messages) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("name", message.getName());
			//map.put("newPhoneNum", friend.getID());
			list.add(map);
			Log.v("DB", message.getName());
		}
		Log.v("DB", "is got");
		SimpleAdapter adapter = new SimpleAdapter(
				SMSListActivity.this, list,
				R.layout.contactor_list_item, new String[] { "name"
						 }, new int[] { R.id.toucherName
						});
		smsListView.setAdapter(adapter);	

	}
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Common.MESSAGE_TEST:
				Bundle b = msg.getData();
				String message = b.getString("data");
				break;
			case Common.SEND_MSG:
				Bundle reciverBundle = msg.getData();
				String reciver_msg = reciverBundle.getString("msg");
				String ID_From = reciverBundle.getString("ID");
				Messages msgs = new Messages(null, ID_From, reciver_msg,1);
				mgr.addMessage(msgs);
//				msg_edit.append(ID_From + ": " + reciver_msg + "\n");
				break;
			default:
				ChatUtils.alertToast(getBaseContext(), tag, "aaaaa"
						+ msg.what);
				break;
			}
		}

	};


	private Messenger mMessenger;


	private Messenger rMessenger;

	private ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			rMessenger = new Messenger(service);
			Message msg = Message.obtain(null, Common.BLIND);
			msg.replyTo = mMessenger;
			try {
				rMessenger.send(msg);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Message msg = Message.obtain(null, Common.BLIND);
			msg.replyTo = null;
			try {
				rMessenger.send(msg);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			mMessenger = null;
		}
	};

	@Override
	protected void onDestroy() {
		unbindService(serviceConnection);
		super.onDestroy();
	}


	class ListItemClickListener implements OnItemClickListener {

		public void onItemClick(AdapterView<?> arg0, View view, int position,
				long id) {
			Messages receive_msg = messages.get(position);
			Log.v("Intent ChatMsg", receive_msg+"");

			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putString("msgName", receive_msg.getName());
			bundle.putString("msgNewPhone", receive_msg.getNewTel());
			bundle.putString("receive_msgs", receive_msg.getMsgs());
			intent.putExtras(bundle);
			intent.setClass(SMSListActivity.this, ChatMsgsActivity.class);
			startActivity(intent);

			
		}

	}
	class ContextMenuListener implements OnCreateContextMenuListener {

		public void onCreateContextMenu(ContextMenu menu, View view,
				ContextMenuInfo info) {
			menu.setHeaderTitle("setting");
			menu.setHeaderIcon(R.drawable.ic_launcher);
			menu.add(0, SHOW_ITEM, 0, "scan");
			menu.add(0, DELETE_ITEM, 0, "delete");
			menu.add(0, SEND_SHARE, 0, "share");
			menu.add(0, SET_AS, 0, "set");
			final AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) info;
			pos = menuInfo.position;
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case SHOW_ITEM:
			
			break;

		case DELETE_ITEM: 
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setMessage("is delete?"
//					.setPositiveButton("Yes",
//							new DialogInterface.OnClickListener() {
//
//								public void onClick(DialogInterface dialog,
//										int which) {
//									
//								}
//							}).setNegativeButton("No", null);
//			AlertDialog ad = builder.create();
//			ad.show();
			break;

		case SEND_SHARE:
			break;

		case SET_AS:
			break;

		}
		return true;
	}


	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.newsmsTextView:
			newSMSTextLayout.setVisibility(View.INVISIBLE);
			smsListView.setVisibility(View.INVISIBLE);
			newSMSLayout.setVisibility(View.VISIBLE);
			break;
		case R.id.smsChooseButton:
			if(!chooseLayoutShow) {
		    	chooseLayout.setVisibility(View.VISIBLE);
		    	chooseLayoutShow = true;
			} else {
				chooseLayout.setVisibility(View.INVISIBLE);
				chooseLayoutShow = false;
			}
			break;
		case R.id.sendsmsButton:
			LocalDataFactory dataFactory = LocalDataFactory.getInstance(this);
			String myID = dataFactory.getAccount();
			bundle = getIntent().getExtras();
			ID = TelEdit.getText().toString();
			String msg = smsContentEdit.getText().toString();
			Bundle typeBundle = new Bundle();
			typeBundle.putString("myID", myID);
			typeBundle.putString("ID", ID);
			typeBundle.putString("msg", msg);
			Message send_msg = Message.obtain(null, Common.SEND_MSG);
			send_msg.setData(typeBundle);
			try {
				rMessenger.send(send_msg);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			break;
		}
	}

}
