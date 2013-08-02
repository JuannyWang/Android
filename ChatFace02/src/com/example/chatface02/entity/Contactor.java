package com.example.chatface02.entity;

public class Contactor {

	private String Name;
	private String NewTel;
	private String Groups;
	private String NormalPhone;

	public Contactor() {

	}

	public Contactor(String name, String tel, String ID, String group) {
		this.Name = name;
		this.NewTel = ID;
		this.Groups = group;
		this.NormalPhone = tel;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		this.Name = name;
	}

	public String getNormalPhone() {
		return NormalPhone;
	}

	public void setNormalPhone(String normalPhone) {
		NormalPhone = normalPhone;
	}

	public String getNewTel() {
		return NewTel;
	}

	public void setNewTel(String newTel) {
		NewTel = newTel;
	}

	public String getGroups() {
		return Groups;
	}

	public void setGroups(String group) {
		this.Groups = group;
	}

}
