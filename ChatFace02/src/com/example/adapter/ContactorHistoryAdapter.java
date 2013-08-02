package com.example.adapter;

import java.util.List;

import com.example.chatface02.R;
import com.example.chatface02.R.drawable;
import com.example.chatface02.R.id;
import com.example.chatface02.R.layout;
import com.example.chatface02.ContactorHistory;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactorHistoryAdapter extends BaseAdapter{
	
	private List<ContactorHistory> list;
	private Context context;
	

	public ContactorHistoryAdapter(Context context, List<ContactorHistory> list) {
		this.context = context;
		this.list = list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=LayoutInflater.from(context).inflate(R.layout.history_list_item, null);
		}
		
		ContactorHistory toucher = list.get(position);

		TextView toucherName = (TextView)convertView.findViewById(R.id.toucherName);
		toucherName.setText(toucher.getToucherName());
		
		/*	
		   来电：CallLog.Calls.INCOMING_TYPE （常量值：1）

		    已拨：CallLog.Calls.OUTGOING_TYPE（常量值：2）

		    未接：CallLog.Calls.MISSED_TYPE（常量值：3）
		*/
		
		int type = toucher.getType();
		ImageView imageView = (ImageView)convertView.findViewById(R.id.telType);
		if(type == 1) {
			imageView.setImageResource(R.drawable.inimg);
		} else if(type == 2) {
			imageView.setImageResource(R.drawable.outimg);
		} else {
			imageView.setImageResource(R.drawable.missedtel);
		}
		
		TextView telNumber = (TextView)convertView.findViewById(R.id.telNumber);
		telNumber.setText(toucher.getTel());
	
		TextView time = (TextView)convertView.findViewById(R.id.time);
		time.setText(toucher.getTime());
		
		
		return convertView;
	}

}
