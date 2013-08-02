package com.proxy.activity;

import java.io.DataInputStream;
import java.io.InterruptedIOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

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
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

public class ProxyManagerActivity extends Activity {
    /** Called when the activity is first created. */
	private EditText usernameEdit, passwdEdit;
	private CheckBox remname;
	private Button login;
	private ProgressDialog progressDialog;
	private String username, password, ip;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        usernameEdit = (EditText)findViewById(R.id.username);                
        passwdEdit = (EditText)findViewById(R.id.password);
        if(getRemName())
        	setEditName();
             
        remname = (CheckBox)findViewById(R.id.remname);
        remname.setChecked(getRemName());
        remname.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton remcheck, boolean isCheck) {
				// TODO Auto-generated method stub
				SharedPreferences pf = getPreferences(MODE_PRIVATE);
		    	Editor editor = pf.edit();
		    	editor.putBoolean("isRemname", isCheck);
		    	editor.commit();
			}
		});
        
             
        
        login = (Button)findViewById(R.id.login);
        login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				username = usernameEdit.getText().toString().trim();
				password = passwdEdit.getText().toString().trim();
				if(username.equals("") | password.equals("")  )
				{
					Toast.makeText(ProxyManagerActivity.this, "不能为空", Toast.LENGTH_LONG).show();
					return;
				}
				
				ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
				params.add(new BasicNameValuePair("ip", ip));
				
				HttpTask task = new HttpTask("<#GetKey#>");
				task.execute(params);
				
			}
		});
    }
      
    private boolean getRemName()
    {
    	SharedPreferences pf = getPreferences(MODE_PRIVATE);
    	boolean isRem = pf.getBoolean("isRemname", false);
    	return isRem;
    }
    
    private void setEditName()
    {
    	SharedPreferences pf = getPreferences(MODE_PRIVATE);
    	String dfname = pf.getString("username", null);
    	if(dfname != null)
    		usernameEdit.setText(dfname);
    }
    
    private void setUsername(String name)
    {
    	SharedPreferences pf = getPreferences(MODE_PRIVATE);
    	Editor editor = pf.edit();
    	editor.putString("username", name);
    	editor.commit();
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
			String result = "<#fail#>";
			if(message.startsWith("<#GetKey#>"))
			{
				HttpURLConnection conn = null;
				String id = params[0].get(0).getValue();
				try {
//					do {
						
						URL url = new URL(
								Constants.Url + "/cellkey.action");
						conn = (HttpURLConnection) url.openConnection();
						conn.setConnectTimeout(6000);
						conn.setRequestMethod("GET");
						conn.setDoOutput(true);
						conn.connect();
						System.out.println("sendkey connect");

						DataInputStream dis = new DataInputStream(
								conn.getInputStream());
						int length = dis.readInt();
						System.out.println(length);
						byte[] key = new byte[length];
						
						dis.read(key);
						KEYUtil.unwrapkey(key, getApplicationContext());
						result = "getkey";
//					} while (isContinue.equals("continue"));
					if (conn != null)
						conn.disconnect();
					return result;
				}  catch (InterruptedIOException e) {
					// TODO: handle exception
					e.printStackTrace();
					
					result =  "overtime";
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					if (conn != null)
						conn.disconnect();
				}
			}
			else if(message.startsWith("<#Login#>"))
			{
				System.out.println("start query");
				String url = Constants.Url + "/celllogin.action";
				System.out.println(url);
				HttpPost post = new HttpPost(url);
				HttpClient client = new DefaultHttpClient();
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
			if(result.equals("<#fail#>"))
			{
				setUsername(username);
				Intent intent = new Intent();
				intent.putExtra("userid", username);
				intent.setClass(ProxyManagerActivity.this, SaleUploadCodeActivity.class);
				startActivity(intent);
				//Toast.makeText(ProxyManagerActivity.this, "连接失败", Toast.LENGTH_LONG).show();
			}
			else if(result.equals("getkey"))
			{
				ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
				params.add(new BasicNameValuePair("username", KEYUtil.wrapMsg(username)));
				params.add(new BasicNameValuePair("password", KEYUtil.wrapMsg(password)));
				
				HttpTask task = new HttpTask("<#Login#>");
				task.execute(params);
			}
			else if(result.equals("overtime"))
				Toast.makeText(ProxyManagerActivity.this, "网络连接超时", Toast.LENGTH_LONG).show();
			else if(result.equals("PasswordWrong"))
				Toast.makeText(ProxyManagerActivity.this, "用户名密码错误", Toast.LENGTH_LONG).show();
			else
			{
				setUsername(username);
				Intent intent = new Intent();
				intent.putExtra("userid", username);
				intent.setClass(ProxyManagerActivity.this, SaleUploadCodeActivity.class);
				startActivity(intent);
			}
		}
		
	}

}