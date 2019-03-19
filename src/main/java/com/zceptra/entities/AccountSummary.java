package com.zceptra.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AccountSummary {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)	
	private Long id;
	private Long accountId;
	private int year;
	private int month;
	private double credits;
	private double debits;
	
	public void credit(double amount)	{
		credits += amount;
	}
	
	public void debit(double amount)	{
		
		debits += amount;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public double getCredits() {
		return credits;
	}
	public void setCredits(double credits) {
		this.credits = credits;
	}
	public double getDebits() {
		return debits;
	}
	public void setDebits(double debits) {
		this.debits = debits;
	}
}
