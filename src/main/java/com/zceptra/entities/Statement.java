package com.zceptra.entities;

public class Statement {
	
	private String content; 
	private String accountId;
	private StatementProfile profile;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public StatementProfile getProfile() {
		return profile;
	}
	public void setProfile(StatementProfile profile) {
		this.profile = profile;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
}
