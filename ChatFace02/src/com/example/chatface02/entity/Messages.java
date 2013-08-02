package com.example.chatface02.entity;

public class Messages {
	private String Name;
	private String NewTel;
	private String msgs;
	private int ISREAD;
	public Messages(){
		
	}
	public Messages(String name, String Newtel, String msgs,int ISREAD){
		this.Name =name;
		this.NewTel =Newtel;
		this.msgs = msgs;
		this.ISREAD = ISREAD;
		
	}
	
	public int getISREAD() {
		return ISREAD;
	}
	public void setISREAD(int iSREAD) {
		ISREAD = iSREAD;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getNewTel() {
		return NewTel;
	}
	public void setNewTel(String newTel) {
		NewTel = newTel;
	}
	public String getMsgs() {
		return msgs;
	}
	public void setMsgs(String msgs) {
		this.msgs = msgs;
	}
	
}
