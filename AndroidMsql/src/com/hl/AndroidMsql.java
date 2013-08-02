package com.hl;

import java.sql.DriverManager;
import java.sql.ResultSet;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AndroidMsql extends Activity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button btnOracle=(Button)findViewById(R.id.btn);
        Button btnMysql=(Button)findViewById(R.id.btn1);
        Button btnSqlServer=(Button)findViewById(R.id.btn2);
        btnOracle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				conOracle();
			}
		});
        btnMysql.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				conMysql();
			}
		});
        btnSqlServer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				conSqlserver();
			}
		});
    }
    private void mSetText(String str){
    	 TextView txt=(TextView)findViewById(R.id.txt);
    	 txt.setText(str);
    }
    private void conOracle(){
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
            String url ="jdbc:oracle:thin:@192.168.0.5:1522:purchasedb";//链接数据库语句
            java.sql.Connection conn= (java.sql.Connection) DriverManager.getConnection(url,"ty","123"); //链接数据库
            java.sql.Statement stmt=(java.sql.Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String sql="select * from s_dict_data";//查询user表语句
            ResultSet rs=stmt.executeQuery(sql);//执行查询
            StringBuilder str=new StringBuilder();
            while(rs.next()){
            	str.append(rs.getString(1)+"\n");	
            }
            mSetText(str.toString());
            rs.close();    
            stmt.close();
            conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    private void conMysql(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
            String url ="jdbc:mysql://113.251.168.39:3306/schedule";//链接数据库语句
            com.mysql.jdbc.Connection conn= (com.mysql.jdbc.Connection) DriverManager.getConnection(url,"root","root"); //链接数据库
            com.mysql.jdbc.Statement stmt=(com.mysql.jdbc.Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String sql="select * from project";//查询user表语句
            ResultSet rs=stmt.executeQuery(sql);//执行查询
            StringBuilder str=new StringBuilder();
            while(rs.next()){
            	str.append(rs.getString(1)+"\n");	
            }
            mSetText(str.toString());
            rs.close();    
            stmt.close();
            conn.close();
		} catch (Exception e) {
			mSetText(e.toString());
			e.printStackTrace();
		}
	}
    private void conSqlserver(){
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
            String url ="jdbc:jtds:sqlserver://192.168.1.50:1433/eKOBA_yu";//链接数据库语句
            java.sql.Connection conn= (java.sql.Connection) DriverManager.getConnection(url,"sa","newland"); //链接数据库
            java.sql.Statement stmt=(java.sql.Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String sql="select * from dbo.USERMST";//查询user表语句
            ResultSet rs=stmt.executeQuery(sql);//执行查询
            StringBuilder str=new StringBuilder();
            while(rs.next()){
            	str.append(rs.getString(1)+"\n");	
            }
            mSetText(str.toString());
            rs.close();    
            stmt.close();
            conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}