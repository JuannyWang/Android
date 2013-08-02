package com.cqupt.mobilestudiesdemo.activity;

import com.cqupt.mobilestudiesdemo.util.MoveBg;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class AssugnmentsActivity extends Activity {

	RelativeLayout layout;
	TextView tv_front;//需要移动的View

	TextView tv_bar_abeyance;
	TextView tv_bar_finished;
	TextView tv_bar_discuss;


	int avg_width = 0;// 用于记录平均每个标签的宽度，移动的时候需要

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_tab_assignments);

		initViews();
	}

	private void initViews() {
		layout = (RelativeLayout) findViewById(R.id.assignments_layout_title_bar);

		tv_bar_abeyance = (TextView) findViewById(R.id.assignments_title_bar_abeyance);
		tv_bar_finished = (TextView) findViewById(R.id.assignmentstitle_bar_finished);
		tv_bar_discuss = (TextView) findViewById(R.id.assignments_title_bar_discuss);


		tv_bar_abeyance.setOnClickListener(onClickListener);
		tv_bar_finished.setOnClickListener(onClickListener);
		tv_bar_discuss.setOnClickListener(onClickListener);


		tv_front = new TextView(this);
		tv_front.setBackgroundResource(R.drawable.slidebar);
		tv_front.setTextColor(Color.WHITE);
		tv_front.setText("待办");
		tv_front.setGravity(Gravity.CENTER);
		RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		param.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		layout.addView(tv_front, param);

	}

	private OnClickListener onClickListener = new OnClickListener() {
		int startX;//移动的起始位置

		@Override
		public void onClick(View v) {
			avg_width = findViewById(R.id.assignments_tab_layout).getWidth();
			switch (v.getId()) {
			case R.id.assignments_title_bar_abeyance:
				MoveBg.moveFrontBg(tv_front, startX, 0, 0, 0);
				startX = 0;
				tv_front.setText(R.string.title_assignments_category_abeyance);
				break;
			case R.id.assignmentstitle_bar_finished:
				MoveBg.moveFrontBg(tv_front, startX, avg_width, 0, 0);
				startX = avg_width;
				tv_front.setText(R.string.title_assignments_category_finished);
				break;
			case R.id.assignments_title_bar_discuss:
				MoveBg.moveFrontBg(tv_front, startX, avg_width * 2, 0, 0);
				startX = avg_width * 2;
				tv_front.setText(R.string.title_assignments_category_discuss);
				break;

			default:
				break;
			}

		}
	};

}
