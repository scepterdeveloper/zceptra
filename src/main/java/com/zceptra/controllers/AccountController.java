package com.zceptra.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zceptra.entities.Account;
import com.zceptra.entities.Transaction;
import com.zceptra.repositories.AccountRepository;

@RestController
public class AccountController {
	
	@Autowired
	private AccountRepository repository;
	
	@RequestMapping(value="get-all-accounts")
	public Iterable<Account> getAllAccounts()	{
		
		Iterable<Account> accounts = repository.findAll();
		
		for(Account account: accounts)	{
			
			System.out.println("Account: " + account.getName());
			System.out.println("-------------------------------");
			for(Transaction transaction: account.getTransactions())	{
				
				System.out.println(transaction.getText());
			}
		}
		
		return accounts;
	}
}
