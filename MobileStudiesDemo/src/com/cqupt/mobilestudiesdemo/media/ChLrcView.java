package com.cqupt.mobilestudiesdemo.media;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class ChLrcView extends TextView{
	private float width;
	private float high;
	private Paint CurrentPaint;
	private Paint NotCurrentPaint;
	int []colors=new int[]{Color.parseColor("#FFFFFF"),Color.parseColor("#F0F0F0"),Color.parseColor("#d0d0d0"),Color.parseColor("#ADADAD"),Color.parseColor("#8E8E8E")};
	private float TextHigh = 30;
	private float TextSize = 20;
	private int Index = 0;
	private List<LrcContent> mEnSentenceEntities = new ArrayList<LrcContent>();
	private List<LrcContent> mChSentenceEntities = new ArrayList<LrcContent>();

	public void setmEnSentenceEntities(List<LrcContent> mEnSentenceEntities) {
		this.mEnSentenceEntities = mEnSentenceEntities;
	}

	public void setmChSentenceEntities(List<LrcContent> mChSentenceEntities) {
		this.mChSentenceEntities = mChSentenceEntities;
	}
	public ChLrcView(Context context) {
		super(context);
		init();
		// TODO Auto-generated constructor stub
	}
	public ChLrcView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		// TODO Auto-generated constructor stub
	}
	public ChLrcView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
		// TODO Auto-generated constructor stub
	}
	private void init() {
		// TODO Auto-generated method stub
		setFocusable(true);

		CurrentPaint = new Paint();
		CurrentPaint.setAntiAlias(true);
		CurrentPaint.setTextAlign(Paint.Align.CENTER);

		NotCurrentPaint = new Paint();
		NotCurrentPaint.setAntiAlias(true);
		NotCurrentPaint.setTextAlign(Paint.Align.CENTER);
		
		CurrentPaint.setColor(Color.rgb(208, 86, 87));
		NotCurrentPaint.setColor(Color.rgb(66, 234, 65));

		CurrentPaint.setTextSize(34);
		CurrentPaint.setTypeface(Typeface.SERIF);

		NotCurrentPaint.setTextSize(TextSize);
		NotCurrentPaint.setTypeface(Typeface.DEFAULT);	
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		if (canvas == null) {
			return;
		}

		try {
			if (MusicActivity.showLrc.isChecked()) {
				setText("");
				
				float tempY=high/2;
				for (int i = Index; i >=0;i--) {//画出本句及之前的句子
					if (i==Index) {
						tempY=DrawLrcIndex(canvas, CurrentPaint, mEnSentenceEntities.get(i).getLrc()+(mChSentenceEntities.size()==0?"":mChSentenceEntities.get(i).getLrc()), tempY);
						float temp=high/2+TextHigh;
						for(int j=Index+1;j<mEnSentenceEntities.size();j++)
						{
							temp =temp+TextHigh;
							if(j<=Index+5)
							{
								if (mEnSentenceEntities.get(j).getLrc()!=null) {
									NotCurrentPaint.setColor(colors[Index+5-j]);
								}
							}
							temp=DrawLrcDownward(canvas, NotCurrentPaint, mEnSentenceEntities.get(j).getLrc()+(mChSentenceEntities.size()==0?"":mChSentenceEntities.get(j).getLrc()), temp);
						}
					}
					else{
						tempY=tempY-TextHigh;
						if (i>=Index-5) {
							if (mEnSentenceEntities.get(i).getLrc()!=null) {
								NotCurrentPaint.setColor(colors[i-Index+5]);
							}
						}
						tempY=DrawLrcUpward(canvas, NotCurrentPaint,mEnSentenceEntities.get(i).getLrc()+(mChSentenceEntities.size()==0?"":mChSentenceEntities.get(i).getLrc()), tempY);
					}
				}
				
			}
			else {
			}
		} catch (Exception e) {
			
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);

		this.width = w;
		this.high = h;
	}

	public void SetIndex(int index) {
		this.Index = index;
	}

	/**
	 * 向下推移  满屏后调用DrawLrcUpward()向上推移
	 * @param lrcCanvas
	 * @param lrcPaint
	 * @param content
	 * @param hight 当前字幕要画的位置
	 * @return 下一句字幕要画的位置
	 */
	public float DrawLrcDownward(Canvas lrcCanvas,Paint lrcPaint,String content,float hight)
	{
		String[]lrcString=autoSplit(content, 450);
		for (int j = 0; j <lrcString.length; j++) {
			lrcCanvas.drawText(lrcString[j], width/2, hight+j*TextHigh, lrcPaint);
		}
		hight=hight+(lrcString.length)*TextHigh;
		return hight;
	}
	
	/**
	 * 当字幕满屏后向上推移
	 * @param lrcCanvas 
	 * @param lrcPaint
	 * @param content 字幕内容
	 * @param hight 当前字幕要画的位置
	 * @return 下一句字幕要画的位置
	 */ 
	public float DrawLrcUpward(Canvas lrcCanvas,Paint lrcPaint,String content,float hight)
	{
		String[]lrcString=autoSplit(content, 450);
		for (int j = lrcString.length-1; j >=0; j--) {
			lrcCanvas.drawText(lrcString[lrcString.length-1-j], width/2, hight-j*TextHigh, lrcPaint);
		}
		hight=hight-(lrcString.length)*TextHigh;
		return hight;
	}
	
	public float DrawLrcIndex(Canvas lrcCanvas,Paint lrcPaint,String content,float hight)
	{
		String[]lrcString=autoSplit(content, 250);
		for (int j = lrcString.length-1; j >=0; j--) {
			lrcCanvas.drawText(lrcString[lrcString.length-1-j], width/2, hight-j*TextHigh, lrcPaint);
		}
		hight=hight-(lrcString.length)*TextHigh;
		return hight;
	}
	/**
	 * 自动换行
	 * @param content
	 * @param width
	 * @return
	 */
	 private String[] autoSplit(String content, float width) { 
        Paint p=new Paint();
        int length = content.length();  
        float textWidth = p.measureText(content); 
        if(textWidth <= width) {  
            return new String[]{content};  
        }  
          
        int start = 0, end = 1, line = 0;  
        int lines = (int) Math.ceil(textWidth / width);
        String[] lineTexts = new String[lines]; 
        while(start < length) {  
            if(p.measureText(content, start, end) > width) {
                lineTexts[line++] = (String) content.subSequence(start, end);  
                start = end;  
            }  
            if(end == length) { 
                lineTexts[line] = (String) content.subSequence(start, end);  
                break;  
            }  
            end += 1;  
        }
       return lineTexts;
      
	}
}
