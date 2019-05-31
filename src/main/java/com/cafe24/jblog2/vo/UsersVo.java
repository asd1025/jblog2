package com.cafe24.jblog2.vo;

public class UsersVo {
private String id;
private String name;
private String password;
private String reg_date;
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getReg_date() {
	return reg_date;
}
public void setReg_date(String reg_date) {
	this.reg_date = reg_date;
}
@Override
public String toString() {
	return "UsersVo [id=" + id + ", name=" + name + ", password=" + password + ", reg_date=" + reg_date + "]";
}

}
