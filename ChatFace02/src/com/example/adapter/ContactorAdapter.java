package com.example.adapter;

import java.util.List;

import com.example.chatface02.R;
import com.example.chatface02.entity.Contactor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ContactorAdapter extends BaseAdapter {
	
	private List<Contactor> list;
	private Context context;
	
	public ContactorAdapter(Context context, List<Contactor> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		
		return list.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {


		if(convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.contactor_list_item, null);
		}
		
		Contactor toucher = list.get(position);
		TextView name = (TextView)convertView.findViewById(R.id.toucherName);
		name.setText(toucher.getName());
		
		return convertView;
	}

}
