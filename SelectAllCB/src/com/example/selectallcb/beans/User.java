package com.example.selectallcb.beans;

public class User {
	int id;
	
	int imgId;

	String name;
	
	String tel;
	
	public int getImgId() {
		return imgId;
	}
	public String getName() {
		return name;
	}
	public String getTel() {
		return tel;
	}
	public void setImgId(int imgId) {
		this.imgId = imgId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	@Override
	public String toString() {
		// TODO �Զ����ɵķ������
		return name+":"+tel;
	}
	public int getId() {
		// TODO �Զ����ɵķ������
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
