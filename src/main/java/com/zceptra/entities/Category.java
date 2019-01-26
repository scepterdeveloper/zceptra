package com.zceptra.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Category {
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)	
	private Long id;
	private String name;
	private String description;
	
	public Category()	{
		
	}
	
	public Category(String name, String description)	{
		
		this.name = name;
		this.description = description;
	}
	
	@OneToMany(mappedBy="category", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private List<Account> accounts;
		
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public List<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
}
