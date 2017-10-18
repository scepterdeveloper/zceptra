package com.zceptra.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@RequestMapping(value="get-statement")
	public List<Transaction> getStatement(@RequestParam Long accountId)	{
		
		Account account = repository.findOne(accountId);
		List<Transaction> transactions = account.getTransactions();
		List<Transaction> participatingTransactions = account.getParticipatingTransactions();
		
		for(Transaction transaction: participatingTransactions)	{
			
			transaction.setAmount(transaction.getAmount() * -1);
			transactions.add(transaction);
		}
				
		return transactions;
	}
}