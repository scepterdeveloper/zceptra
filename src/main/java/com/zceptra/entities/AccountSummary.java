package com.zceptra.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class AccountSummary {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)	
	private Long id;
	private int year;
	private int month;
	private double credits;
	private double debits;
	private LocalDate validFrom;
	private LocalDate validTo;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn
    @JsonIgnore
	private Account account;
	
	
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

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
    public LocalDate getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(LocalDate validFrom) {
		this.validFrom = validFrom;
	}

	public LocalDate getValidTo() {
		return validTo;
	}

	public void setValidTo(LocalDate validTo) {
		this.validTo = validTo;
	}	
}
