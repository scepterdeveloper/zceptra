package com.zceptra.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zceptra.entities.Account;
import com.zceptra.repositories.AccountRepository;

@Service
public class AccountsManager {
	
	@Autowired
	private AccountRepository repository;
	
	public Iterable<Account> getAllAccounts()	{
		
		return repository.findAll();
	}
}
