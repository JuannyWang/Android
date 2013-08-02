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

public class SaleUploadCodeActivity extends Activity {

	private EditText CodeView;
	private Button scanCode, upload;
	private ArrayList<String> CodeArray = new ArrayList<String>();
	private String operator, brandName, userid;
	private boolean isOperator, isBrand, isCode;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.saleuploadcode);
		
		userid = getIntent().getStringExtra("userid");
		
		CodeView = (EditText)findViewById(R.id.salecode);
		
		
		scanCode = (Button)findViewById(R.id.salescancode);
		scanCode.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SaleUploadCodeActivity.this, ScanActivity.class);
				startActivityForResult(intent, 1);
			}
		});
		
		upload = (Button)findViewById(R.id.saleupload);
		upload.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!CodeView.getText().toString().trim().equals(""))
				{
					String code = CodeView.getText().toString().trim();					
					ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();	
					//可增加参加上传字段
					params.add(new BasicNameValuePair("userid", KEYUtil.wrapMsg(userid)));
					params.add(new BasicNameValuePair("barcode", KEYUtil.wrapMsg(code)));
					HttpTask task = new HttpTask("upload");
					task.execute(params);
				}
				else
					Toast.makeText(getApplicationContext(), "无代码", Toast.LENGTH_LONG).show();
			}
		});
		
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(resultCode == 1)
		{
			CodeView.append(data.getExtras().getString("code"));			
		}
	}
	
	public class HttpTask extends AsyncTask<ArrayList<BasicNameValuePair>, String, String> {
		private String message;
		
		public HttpTask(String message) {
			super();
			this.message = message;
		}
		
		@Override
		protected String doInBackground(ArrayList<BasicNameValuePair>... params) {
			// TODO Auto-generated method stub
			if(message.startsWith("upload"))
			{
				System.out.println("start query");
				//服务器路径
				String url = Constants.Url + "/cellsale";
				System.out.println(url);
				HttpPost post = new HttpPost(url);
				HttpClient client = new DefaultHttpClient();
				String result = "<#fail#>";
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
				Toast.makeText(SaleUploadCodeActivity.this, "连接失败", Toast.LENGTH_LONG).show();
			}
			else if(result.equals("overtime"))
				Toast.makeText(SaleUploadCodeActivity.this, "网络连接超时", Toast.LENGTH_LONG).show();
			else{
				CodeView.setText("");
				Toast.makeText(SaleUploadCodeActivity.this, result, Toast.LENGTH_LONG).show();
			}
		}
		
	}
}
