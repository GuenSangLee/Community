package com.ktds.actionhistory.vo;

public class ActionHistoryVO {
	private int histId;
	private String histDate;
	private String reqType;
	private String ip;
	private int userId;
	private String email;
	private String log;
	
	
	
	public void setHistId(int histId) {
		this.histId = histId;
	}
	private String asIs;
	private String toBe;
	
	public int getHistId() {
		return histId;
	}
	public String getHistDate() {
		return histDate;
	}
	public void setHistDate(String histDate) {
		this.histDate = histDate;
	}
	public String getReqType() {
		return reqType;
	}
	public void setReqType(String reqType) {
		this.reqType = reqType;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	public String getAsIs() {
		return asIs;
	}
	public void setAsIs(String asIs) {
		this.asIs = asIs;
	}
	public String getToBe() {
		return toBe;
	}
	public void setToBe(String toBe) {
		this.toBe = toBe;
	}
	
	
}
