package com.zceptra.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Account {	

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)	
	private Long id;
	private String name;
	private String description;
	
	@OneToMany(mappedBy="account", cascade = CascadeType.ALL)
	private List<Transaction> transactions;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToMany(mappedBy="matchingAccount", cascade = CascadeType.ALL)
	private List<Transaction> matchingTransactions;
		
	private String identificationPattern;	
	
	public Account()	{
		
		transactions = new ArrayList<>();
	}
	
	public Account(String name, String description, String identificationPattern)	{
		
		this.setName(name);
		this.setDescription(description);
		this.identificationPattern = identificationPattern;
		transactions = new ArrayList<>();
	}	
	
	public List<Transaction> getTransactions() {
		
		return transactions;		
	}
	
	public void addTransaction(Transaction transaction)	{
		
		transactions.add(transaction);
	}
	
	public boolean matchesPattern(String text)	{
		
		String[] patterns = identificationPattern.split(";");
		
		for(String pattern: patterns)	{
			
			if(text.contains(pattern)) return true;
		}
		
		return false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Transaction> getMatchingTransactions() {
		return matchingTransactions;
	}

	public void setMatchingTransactions(List<Transaction> matchingTransactions) {
		this.matchingTransactions = matchingTransactions;
	}

	public String getIdentificationPattern() {
		return identificationPattern;
	}

	public void setIdentificationPattern(String identificationPattern) {
		this.identificationPattern = identificationPattern;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
}
