package com.fizay.vo;

/**
 * 用于封装用户信息
 * 暂时缺了String password
 * 要用md5加密 还没做
 * @author FUZIYAN
 * 
 * 2020/8/5
 */
public class User {
	private Integer id;
	private String username;
	private String salt;
	private String email;
	private String phone;
	private Integer qpsLevel;
	private Integer deptId;
	private String note;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getQpsLevel() {
		return qpsLevel;
	}
	public void setQpsLevel(Integer qpsLevel) {
		this.qpsLevel = qpsLevel;
	}
	public Integer getDeptId() {
		return deptId;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", salt=" + salt + ", email=" + email + ", phone=" + phone
				+ ", qpsLevel=" + qpsLevel + ", deptId=" + deptId + ", note=" + note + "]";
	}
	
}
