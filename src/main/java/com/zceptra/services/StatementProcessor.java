package com.zceptra.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zceptra.entities.Account;
import com.zceptra.entities.Statement;
import com.zceptra.entities.StatementProfile;
import com.zceptra.entities.Transaction;
import com.zceptra.repositories.AccountRepository;

@Service
public class StatementProcessor {
	
	@Autowired
	private AccountRepository accountRepository;
	
	public void processStatement(Statement statement){
		
		extractTranscations(statement);		
	}

	
	private void extractTranscations(Statement statement)	{
		
		StatementProfile profile = statement.getProfile();
		String[] lines = statement.getContent().split(profile.getRowSeparator());
		String header = lines[0];
		String[] headerFields = header.split(profile.getColumnSeparator());
		Account statementAccount = accountRepository.getOne(Long.parseLong(statement.getAccountId()));
		
		int dateIndex = findIndex(headerFields, profile.getDateHeader());
		int amountIndex = findIndex(headerFields, profile.getAmountHeader());
		int textIndex = findIndex(headerFields, profile.getTextHeader());
		
		Iterable<Account> accounts = accountRepository.findAll();		
		
		for(int i=1; i<lines.length; i++)	{
			
			Account matchingAccount = statementAccount;
			String[] fields = lines[i].split(profile.getColumnSeparator());
			String date = fields[dateIndex];
			String amount = fields[amountIndex];
			String text = fields[textIndex];
			
			for(Account account: accounts)	{
				
				if(account.matchesPattern(text))	{
					
					matchingAccount = account;
					break;
				}
			}

			Transaction transaction = new Transaction();
			transaction.setDate(LocalDateTime.now());
			transaction.setAmount(amount);
			transaction.setAccount(statementAccount);
			transaction.setParticipatingAccount(matchingAccount);
			transaction.setText(date + " | " + amount + " | " + text + " | " + matchingAccount.getName());
			//statementAccount.addTransaction(transaction);			
		}
		
		accountRepository.save(statementAccount);
	}

	private int findIndex(String[] headerFields, String header) {
		
		int index = 0;
		
		for(String s: headerFields)	{
			
			if(s.equals(header))return index;
			index++;
		}
		
		return -1;
	}
}
