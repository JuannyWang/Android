package com.jdbc.androidjdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new Thread(new Runnable() {

			@Override
			public void run() {
				String driver = "net.sourceforge.jtds.jdbc.Driver";
				String url = "jdbc:jtds:sqlserver://192.168.149.1:1433/";
				String user = "sa";
				String password = "shixue";
				try {
					Class.forName(driver);
					Connection conn = (Connection) DriverManager.getConnection(url, user, password);
					if(!conn.isClosed()) {
						System.out.println("Succeeded connecting to the Database!");
					}
					else {
						System.out.println("Failed connecting to the Database!");
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
