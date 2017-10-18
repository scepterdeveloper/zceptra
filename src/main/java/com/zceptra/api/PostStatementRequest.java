package com.zceptra.api;

public class PostStatementRequest {
	
	private Long profileId;
	private String statementContent;
	
	public Long getProfileId() {
		return profileId;
	}
	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}
	public String getStatementContent() {
		return statementContent;
	}
	public void setStatementContent(String statementContent) {
		this.statementContent = statementContent;
	}
}
