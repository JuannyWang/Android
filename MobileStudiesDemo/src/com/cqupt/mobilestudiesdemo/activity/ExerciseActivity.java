package com.cqupt.mobilestudiesdemo.activity;

import java.util.ArrayList;

import android.animation.AnimatorSet.Builder;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;


import com.cqupt.mobilestudiesdemo.util.MoveBg;

public class ExerciseActivity extends Activity {

	private static final String TAG = "QuestionChapterActivity";
	public static final String QUESTION_CHAPTER_TAG = "question_chapter_fragment_tag";
	private Button mStartButton;
	private RadioGroup mRadioGroup;

	private Activity mActivity;
	RelativeLayout layout;
	String grade_selected;
	TextView grade_selected_tv;
	TextView tv_front;// 需要移动的View
	TextView tv_bar_reading;
	TextView tv_bar_grammar;
	TextView tv_bar_vocabulary;
	TextView tv_bar_more;
	TextView tv_bar_notes;

	int avg_width = 0;// 用于记录平均每个标签的宽度，移动的时候需要



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_tab_exersise);

		grade_selected_tv = (TextView) findViewById(R.id.chapter_tip);
		initViews();
	}

	private void initViews() {
		layout = (RelativeLayout) findViewById(R.id.exercise_layout_title_bar);

		tv_bar_reading = (TextView) findViewById(R.id.exercise_title_bar_reading);
		tv_bar_grammar = (TextView) findViewById(R.id.exercise_title_bar_grammar);
		tv_bar_vocabulary = (TextView) findViewById(R.id.exercise_title_bar_vocabulary);
		tv_bar_more = (TextView) findViewById(R.id.exercise_title_bar_more);
		tv_bar_notes = (TextView) findViewById(R.id.exercise_title_bar_notes);

		tv_bar_reading.setOnClickListener(onClickListener);
		tv_bar_grammar.setOnClickListener(onClickListener);
		tv_bar_vocabulary.setOnClickListener(onClickListener);
		tv_bar_more.setOnClickListener(onClickListener);
		tv_bar_notes.setOnClickListener(onClickListener);

		tv_front = new TextView(this);
		tv_front.setBackgroundResource(R.drawable.slidebar);
		tv_front.setTextColor(Color.WHITE);
		tv_front.setText("CET4");
		tv_front.setGravity(Gravity.CENTER);
		RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		param.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		layout.addView(tv_front, param);

	}

	private OnClickListener onClickListener = new OnClickListener() {
		int startX;// 移动的起始位置

		@Override
		public void onClick(View v) {
			avg_width = findViewById(R.id.exercise_layout).getWidth();
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

	public void exerciseOnClick(View view) {
		switch (view.getId()) {
		case R.id.chapter_tip_select:
			SelectDialog();
			grade_selected_tv.setText(grade_selected);
			break;
		case R.id.start_btn:

			break;
		}
	}

	public void SelectDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("选择学习难度");
		builder.setSingleChoiceItems(R.array.select_grade, 0,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						String[] items = getResources().getStringArray(
								R.array.select_grade);
						grade_selected = items[which];
					}
				});
		final AlertDialog ad = builder.create();
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				ad.dismiss();

			}
		});

	}


}
