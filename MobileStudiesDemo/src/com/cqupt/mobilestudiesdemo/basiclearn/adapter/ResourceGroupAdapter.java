package com.cqupt.mobilestudiesdemo.basiclearn.adapter;

import java.util.Random;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqupt.mobilestudiesdemo.activity.R;
import com.cqupt.mobilestudiesdemo.entity.ResourceGroupEntity;

public class ResourceGroupAdapter extends ArrayListAdapter<ResourceGroupEntity> {

	public ResourceGroupAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View detailView = convertView;
		ViewHolder holder;
		if (detailView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			detailView = inflater.inflate(R.layout.menu_cell, null);
			holder = new ViewHolder();
			holder.rgNameTextView = (TextView) detailView
					.findViewById(R.id.menu_title);
			holder.bgImageView = (ImageView) detailView
					.findViewById(R.id.img_bg);
			detailView.setTag(holder);
		} else {
			holder = (ViewHolder) detailView.getTag();
		}
		TypedArray imgs = mContext.getResources().obtainTypedArray(
				R.array.random_metro_colors);
		Random random = new Random();
		int i = Math.abs(random.nextInt()) % imgs.length();
		holder.bgImageView.setImageResource(imgs.getResourceId(i, -1));
		holder.rgNameTextView.setText(mList.get(position)
				.getResourceGroupName());
		return detailView;
	}

	static class ViewHolder {
		TextView rgNameTextView;
		ImageView bgImageView;
	}

}
