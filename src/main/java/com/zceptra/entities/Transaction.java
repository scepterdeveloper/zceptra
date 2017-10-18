package com.zceptra.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Transaction {
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;    
    private String date;    
    private Double amount;
    
    @Lob
    @Type(type = "org.hibernate.type.TextType")    
    private String text;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn
    private Account account;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn
    private Account participatingAccount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@JsonIgnore
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@JsonIgnore
	public Account getParticipatingAccount() {
		return participatingAccount;
	}

	public void setParticipatingAccount(Account participatingAccount) {
		this.participatingAccount = participatingAccount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public void setAmount(String amount) {
		
		amount = amount.replace(',', '.');
		if(amount.charAt(0)=='-')	{
			
			this.amount = Double.parseDouble(amount.substring(1)) * -1;
		}		
		else	{
			
			this.amount = Double.parseDouble(amount);
		}
	}	
}
