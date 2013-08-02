package com.proxy.activity;

import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import com.manor.util.Constants;
import com.manor.util.KEYUtil;
import com.proxy.activity.R;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class StoreUploadCodeActivity extends Activity {

	private Spinner BarrelId, BrandName;
	private EditText Operator, CodeView;
	private Button setDate, setTime, scanCode, upload;
	private ArrayList<String> CodeArray = new ArrayList<String>();
	private String date="", time="", username, pwd, barrelID, brandName, ip, command;
	private boolean isDate, isTime, isBarrel, isBrand, isCode;
	private Time timer = new Time("GMT+8");
	private TextView inDate;
	private String[] BarrelIDs, BrandNames;
	private String userid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.storeuploadcode);
		
		ip = getIntent().getStringExtra("ip");
		userid = getIntent().getStringExtra("userid");
		
		inDate = (TextView)findViewById(R.id.in_date);
		Operator = (EditText)findViewById(R.id.operator);
		CodeView = (EditText)findViewById(R.id.code);
		
		BarrelId = (Spinner)findViewById(R.id.barrel_id);
		BarrelId.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				barrelID = BarrelIDs[arg2];			
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub				
			}
		});
		
		BrandName = (Spinner)findViewById(R.id.brand_name);
		BrandName.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				brandName = BrandNames[arg2];	
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub				
			}
		});
		
		setDate = (Button)findViewById(R.id.setdata);
		setDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showDialog(1);
			}
		});
		
		setTime = (Button)findViewById(R.id.settime);
		setTime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showDialog(2);
			}
		});
		
		scanCode = (Button)findViewById(R.id.scancode);
		scanCode.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(StoreUploadCodeActivity.this, ScanActivity.class);
				startActivityForResult(intent, 1);
			}
		});
		
		upload = (Button)findViewById(R.id.upload);
		upload.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String oper = Operator.getText().toString().trim();
				if(isDate&&isTime&& !oper.equals("") && !CodeView.getText().toString().trim().equals(""))
				{
					String code = CodeView.getText().toString();	
					System.out.println("code: " + code);
					ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();		
					params.add(new BasicNameValuePair("barrelid", KEYUtil.wrapMsg(barrelID)));
					params.add(new BasicNameValuePair("brandname", KEYUtil.wrapMsg(brandName)));
					params.add(new BasicNameValuePair("operator", KEYUtil.wrapMsg(Operator.getText().toString().trim())));
					params.add(new BasicNameValuePair("indate", KEYUtil.wrapMsg(date + " " + time)));
					params.add(new BasicNameValuePair("codes", KEYUtil.wrapMsg(code)));
					params.add(new BasicNameValuePair("userid", KEYUtil.wrapMsg(userid)));
					HttpTask task = new HttpTask("upload");
					task.execute(params);
				}
				else
					Toast.makeText(getApplicationContext(), "填写不完整", Toast.LENGTH_LONG).show();
			}
		});
		
		ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();		
		HttpTask task = new HttpTask("<#GetBarrelID#>");
		task.execute(params);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(resultCode == 1)
		{
			CodeView.append(data.getExtras().getString("code") + "\n");			
		}
	}



	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		switch(id)
		{
			case 1:
				timer.setToNow();
				return new DatePickerDialog(this, startdatelistener, timer.year, timer.month, timer.monthDay);
			case 2:
				timer.setToNow();
				return new TimePickerDialog(this, timelistener, timer.hour, timer.minute, true);
		}
		return null;
	}
	
	private OnDateSetListener startdatelistener = new OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			isDate = true;
			date = "";
			date += year; 
			date += "-" + (monthOfYear+1);
			date += "-" + dayOfMonth;
			inDate.setText(date + " " + time);
		}
	};
	
	private OnTimeSetListener timelistener = new OnTimeSetListener() {
		
		@Override
		public void onTimeSet(TimePicker arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			isTime = true;
			time = "";
			time += arg1;
			time += ":" + arg2 + ":00";
			inDate.setText(date + " " + time);
		}
	};
	
	public class HttpTask extends AsyncTask<ArrayList<BasicNameValuePair>, String, String> {
		private String message;
		
		public HttpTask(String message) {
			super();
			this.message = message;
		}
		
		@Override
		protected String doInBackground(ArrayList<BasicNameValuePair>... params) {
			// TODO Auto-generated method stub
			if(message.startsWith("<#GetBarrelID#>"))
			{
				System.out.println("start query");
				String url = "http://"+ip+"/" + Constants.Url + "/celladdquery";
				System.out.println(url);
				HttpPost post = new HttpPost(url);
				HttpClient client = new DefaultHttpClient();
				String result = "<#fail#>";
				post.addHeader("command", command);
				try {					
					HttpEntity entity = new UrlEncodedFormEntity(params[0]);					
					post.setEntity(entity);
					client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000); 
					client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
					HttpResponse response = client.execute(post);
					System.out.println(response.getStatusLine().getStatusCode());
					if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
					{				
						System.out.println("query succ");
						result = EntityUtils.toString(response.getEntity(), "UTF-8");
						System.out.println("back result: " + result);
						result = KEYUtil.unwrapMsg(result);
					}
					else
						result = "<#fail#>";
				} catch (InterruptedIOException e) {
					// TODO: handle exception
					e.printStackTrace();
					
					result =  "overtime";
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					result = "<#fail#>";
				} finally
				{
					client.getConnectionManager().shutdown();
				}
				return result;
			}else if(message.startsWith("upload"))
			{
				System.out.println("start query");
				String url = "http://"+ip+"/" + Constants.Url + "/celladd";
				System.out.println(url);
				HttpPost post = new HttpPost(url);
				HttpClient client = new DefaultHttpClient();
				String result = "<#fail#>";
				post.addHeader("command", command);
				try {
					HttpEntity entity = new UrlEncodedFormEntity(params[0]);
					post.addHeader("command", message);
					post.setEntity(entity);
					client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000); 
					client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
					HttpResponse response = client.execute(post);
					System.out.println(response.getStatusLine().getStatusCode());
					if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
					{				
						System.out.println("query succ");
						result = EntityUtils.toString(response.getEntity(), "UTF-8");
						System.out.println("back result: " + result);
						result = KEYUtil.unwrapMsg(result);
					}
					else
						result = "<#fail#>";
				} catch (InterruptedIOException e) {
					// TODO: handle exception
					e.printStackTrace();
					
					result =  "overtime";
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					result = "<#fail#>";
				} finally
				{
					client.getConnectionManager().shutdown();
				}
				return result;
			}
			return "<#fail#>";
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			System.out.println("result: " + result);
			if(result.equals("<#fail#>")|result.endsWith("fail"))
			{
				Toast.makeText(StoreUploadCodeActivity.this, "连接失败", Toast.LENGTH_LONG).show();
			}
			else if(result.equals("overtime"))
				Toast.makeText(StoreUploadCodeActivity.this, "网络连接超时", Toast.LENGTH_LONG).show();
			else if(result.endsWith("succquery"))
			{
				String[] results = result.split("\\|");
				BarrelIDs = results[0].split("\\*");
				BrandNames = results[1].split("\\*");
				ArrayAdapter<String> barrelAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, BarrelIDs);
				ArrayAdapter<String> BrandAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, BrandNames);
				barrelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				BrandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				BrandName.setAdapter(BrandAdapter);
				BarrelId.setAdapter(barrelAdapter);
				barrelID = BarrelIDs[0];
				brandName = BrandNames[0];
			}
			else{
				CodeView.setText("");
				Toast.makeText(StoreUploadCodeActivity.this, result, Toast.LENGTH_LONG).show();
			}
		}
		
	}
}
