package com.example.chatface02;

public class ContactorHistory {

	private String tel;
	private String toucherName;
	private String date;
	private String time;
	private int type;  

	ContactorHistory() {
		
	}
	
	public ContactorHistory(String tel, String toucherName, String date, String time, int type) {
		this.tel = tel;
		this.toucherName = toucherName;
		this.date = date;
		this.time = time;
		this.type = type;
	}
	
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getToucherName() {
		return toucherName;
	}
	public void setToucherName(String toucherName) {
		this.toucherName = toucherName;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDate() {
		return date;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
	
}
