package com.cqupt.mobilestudiesdemo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class ListDetailDialog extends Dialog{
	private Context context;

	public ListDetailDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;	
	}

	@Override
	public void addContentView(View view, LayoutParams params) {
		// TODO Auto-generated method stub
		super.addContentView(view, params);
	}



}
