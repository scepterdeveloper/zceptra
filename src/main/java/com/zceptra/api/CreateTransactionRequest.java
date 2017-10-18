package com.zceptra.api;

public class CreateTransactionRequest {
	
	private String date;
	private Long accountId;
	private Double amount;
	private Long participatingAccountId;
	private String text;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Long getParticipatingAccountId() {
		return participatingAccountId;
	}
	public void setParticipatingAccountId(Long participatingAccountId) {
		this.participatingAccountId = participatingAccountId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}	
}
