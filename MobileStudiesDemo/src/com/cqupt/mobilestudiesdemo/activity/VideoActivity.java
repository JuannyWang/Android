package com.cqupt.mobilestudiesdemo.activity;

import com.cqupt.mobilestudiesdemo.util.MoveBg;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class VideoActivity extends Activity {

	RelativeLayout layout;
	TextView tv_front;//需要移动的View

	TextView tv_bar_edu;
	TextView tv_bar_daily;
	TextView tv_bar_economics;
	TextView tv_bar_science;	
	TextView tv_bar_more;

	int avg_width = 0;// 用于记录平均每个标签的宽度，移动的时候需要

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_tab_video);

		initViews();
	}

	private void initViews() {
		layout = (RelativeLayout) findViewById(R.id.video_layout_title_bar);

		tv_bar_edu = (TextView) findViewById(R.id.video_title_bar_edu);
		tv_bar_daily = (TextView) findViewById(R.id.video_title_bar_daily);
		tv_bar_economics = (TextView) findViewById(R.id.video_title_bar_economics);
		tv_bar_science = (TextView) findViewById(R.id.video_title_bar_science);		
		tv_bar_more = (TextView) findViewById(R.id.video_title_bar_more);

		tv_bar_edu.setOnClickListener(onClickListener);
		tv_bar_daily.setOnClickListener(onClickListener);
		tv_bar_economics.setOnClickListener(onClickListener);
		tv_bar_science.setOnClickListener(onClickListener);		
		tv_bar_more.setOnClickListener(onClickListener);

		tv_front = new TextView(this);
		tv_front.setBackgroundResource(R.drawable.slidebar);
		tv_front.setTextColor(Color.WHITE);
		tv_front.setText("CET4");
		tv_front.setGravity(Gravity.CENTER);
		RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		param.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		layout.addView(tv_front, param);

	}

	private OnClickListener onClickListener = new OnClickListener() {
		int startX;//移动的起始位置


		@Override
		public void onClick(View v) {
			avg_width = findViewById(R.id.video_layout).getWidth();
			switch (v.getId()) {
			case R.id.tv_title_bar_cet4:
				MoveBg.moveFrontBg(tv_front, startX, 0, 0, 0);
				startX = 0;
				tv_front.setText(R.string.title_audio_category_cet4);
				break;
			case R.id.tv_title_bar_cet6:
				MoveBg.moveFrontBg(tv_front, startX, avg_width, 0, 0);
				startX = avg_width;
				tv_front.setText(R.string.title_audio_category_cet6);
				break;
			case R.id.tv_title_bar_ielts:
				MoveBg.moveFrontBg(tv_front, startX, avg_width * 2, 0, 0);
				startX = avg_width * 2;
				tv_front.setText(R.string.title_audio_category_ielts);
				break;
			case R.id.tv_title_bar_toefl:
				MoveBg.moveFrontBg(tv_front, startX, avg_width * 3, 0, 0);
				startX = avg_width * 3;
				tv_front.setText(R.string.title_audio_category_toefl);
				break;			
			case R.id.tv_title_bar_more:
				MoveBg.moveFrontBg(tv_front, startX, avg_width * 4, 0, 0);
				startX = avg_width * 4;
				tv_front.setText(R.string.title_audio_category_more);
				break;

			default:
				break;
			}

		}
	};

	public void videoOnclick(View view){
		switch(view.getId()){
		case R.id.local_video_btn:

			break;
		}
	}
}
