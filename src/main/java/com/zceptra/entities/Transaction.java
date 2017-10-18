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
    
    @Lob
    @Type(type = "org.hibernate.type.TextType")    
    private String text;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn
    private Account account;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn
    private Account matchingAccount;

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
	public Account getMatchingAccount() {
		return matchingAccount;
	}

	public void setMatchingAccount(Account matchingAccount) {
		this.matchingAccount = matchingAccount;
	}
}
