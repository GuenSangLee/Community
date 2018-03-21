package com.ktds.member.vo;

import javax.validation.constraints.NotEmpty;

public class MemberVO {
	private int id;
	@NotEmpty(message="Nickname 빼먹었다.")
	private String nickname;
	@NotEmpty(message="Password 빼먹었다.")
	private String password;
	@NotEmpty(message="Email 빼먹었다.")
	private String email;
	private String registDate;
	public String getRegistDate() {
		return registDate;
	}
	
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
