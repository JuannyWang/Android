package com.example.chatface02;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatface02.db.DBManager;
import com.example.chatface02.entity.Contactor;

public class PersonDetail extends Activity{
	private DBManager mgr;
	private EditText nameEdit,sexEdit,newPhoneEdit,normalEdit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_detail);
		nameEdit = (EditText)findViewById(R.id.name_edit);
		sexEdit = (EditText)findViewById(R.id.sex_edit);
		newPhoneEdit = (EditText)findViewById(R.id.newPhone_edit);
		normalEdit = (EditText)findViewById(R.id.normalPhone_edit);
		
		mgr = new DBManager(this);
		
	}
	
	public void DetailOnclick(View view){
		switch(view.getId()){
		case R.id.back_list_btn:
			Intent intent = new Intent();
			intent.setClass(this, ContactorsListActivity.class);
			startActivity(intent);
			this.finish();
			break;
		case R.id.OK_btn:
			
			mgr.addPerson(getFriend());
			Toast.makeText(this, "Add Successed!", Toast.LENGTH_SHORT).show();
			break;
		case R.id.edit_btn:
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mgr.closeDB();
	}

	public Contactor getFriend(){
		Contactor friend = new Contactor();
		String name = nameEdit.getText().toString();
		String group = sexEdit.getText().toString();
		String ID = newPhoneEdit.getText().toString();
		String TEl = normalEdit.getText().toString();
		friend.setName(name);
		friend.setNewTel(ID);
		friend.setNormalPhone(TEl);
		friend.setGroups(group);
		return friend;
		
	}
}
