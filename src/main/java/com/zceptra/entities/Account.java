package com.zceptra.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Account {	

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)	
	private Long id;
	private String name;
	private String description;

	/*@OneToMany(mappedBy="account", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Transaction> transactions;*/
	
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn
	private Category category;
    
	@OneToMany(mappedBy="account", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	private List<AccountSummary> accountSummary;
    
	
    public Category getCategory() {
    	
		category.setAccounts(null); //Avoid Jackson exception of recursive serialization
    	return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<AccountSummary> getAccountSummary() {
		return accountSummary;
	}

	public void setAccountSummary(List<AccountSummary> accountSummary) {
		this.accountSummary = accountSummary;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/*@OneToMany(mappedBy="participatingAccount", cascade = CascadeType.ALL)
	private List<Transaction> participatingTransactions;*/
		
	private String identificationPattern;	
	
	public Account()	{
		
		//transactions = new ArrayList<>();
	}
	
	public Account(Category category, String name, String description, String identificationPattern)	{
		
		this.category = category;
		this.setName(name);
		this.setDescription(description);
		this.identificationPattern = identificationPattern;
		//transactions = new ArrayList<>();
	}	
	
	/*public List<Transaction> getTransactions() {
		
		return transactions;		
	}*/
	
	/*public void addTransaction(Transaction transaction)	{
		
		transactions.add(transaction);
	}*/
	
	public boolean matchesPattern(String text)	{
		
		String[] patterns = identificationPattern.split(";");
		
		for(String pattern: patterns)	{
			
			if(text.toLowerCase().contains(pattern.toLowerCase())) return true;
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

	/*public List<Transaction> getParticipatingTransactions() {
		return participatingTransactions;
	}

	public void setParticipatingTransactions(List<Transaction> participatingTransactions) {
		this.participatingTransactions = participatingTransactions;
	}*/

	public String getIdentificationPattern() {
		return identificationPattern;
	}

	public void setIdentificationPattern(String identificationPattern) {
		this.identificationPattern = identificationPattern;
	}

	/*public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}*/
}
