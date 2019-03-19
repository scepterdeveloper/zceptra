package com.zceptra.controllers;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zceptra.api.CreateTransactionRequest;
import com.zceptra.entities.Account;
import com.zceptra.entities.AccountSummary;
import com.zceptra.entities.AccountingOperation;
import com.zceptra.entities.Transaction;
import com.zceptra.repositories.AccountRepository;
import com.zceptra.repositories.AccountSummaryRepository;
import com.zceptra.repositories.TransactionRepository;

@RestController
public class TransactionController {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AccountSummaryRepository summaryRepository;
	
	@CrossOrigin(origins = {"https://zceptra-ui.herokuapp.com", "http://localhost:4200"})
	@RequestMapping(value = "/create-transaction", method = RequestMethod.POST)
	public void createTransaction(@RequestBody CreateTransactionRequest request)	{
		
		Transaction transaction = new Transaction();
		transaction.setDate(request.getDate());
		transaction.setAmount(request.getAmount());
		transaction.setText(request.getText());
		
		transaction.setAccount(accountRepository.getOne(request.getAccountId()));
		transaction.setParticipatingAccount(accountRepository.getOne(request.getParticipatingAccountId()));
		
		transactionRepository.save(transaction);		
	}
	
	private void maintainSummaryForAccount(Account account, int year, int month, AccountingOperation operation, double amount)	{
		
		AccountSummary accountSummary = null;
		
		List<AccountSummary> accountSummaryList = summaryRepository.findByAccountIdAndYearAndMonth(account.getId(), year, month);
		if(accountSummaryList!=null && !accountSummaryList.isEmpty())	{
			accountSummary = accountSummaryList.get(0);
		}
		else	{
			
			accountSummary = new AccountSummary();
			accountSummary.setAccountId(account.getId());
			accountSummary.setYear(year);
			accountSummary.setMonth(month);
		}	
		
		if(operation == AccountingOperation.DEBIT) accountSummary.debit(amount);
		else accountSummary.credit(amount);
		summaryRepository.save(accountSummary);		
	}
	
	private void maintainSummaryForTransaction(Transaction transaction)	{
		
		if(transaction.getId()!=null) rollBackOldSummary(transaction);
		maintainSummaryForAccount(transaction.getAccount(), 2019, 2, AccountingOperation.DEBIT, transaction.getAmount());
		maintainSummaryForAccount(transaction.getParticipatingAccount(), 2019, 2, AccountingOperation.CREDIT, transaction.getAmount());				
	}
	
	private void rollBackOldSummary(Transaction transaction)	{
		
		Transaction unmodifiedTransaction = transactionRepository.getOne(transaction.getId());
		summaryRepository.findByAccountIdAndYearAndMonth(unmodifiedTransaction.getAccount().getId(), 2019, 2).get(0).debit(unmodifiedTransaction.getAmount()*-1);
		summaryRepository.findByAccountIdAndYearAndMonth(unmodifiedTransaction.getParticipatingAccount().getId(), 2019, 2).get(0).credit(unmodifiedTransaction.getAmount()*-1);
	}
	
	@CrossOrigin(origins = {"https://zceptra-ui.herokuapp.com", "http://localhost:4200"})
	@RequestMapping(value="save-transaction", method=RequestMethod.POST)
	@ResponseBody
	public Transaction saveTransaction(@RequestBody Transaction editedTransaction)	{
			
		maintainSummaryForTransaction(editedTransaction);
		Transaction savedTransaction = transactionRepository.save(editedTransaction);
		return savedTransaction;
	}
	
	@CrossOrigin(origins = {"https://zceptra-ui.herokuapp.com", "http://localhost:4200"})
	@RequestMapping(value="get-all-transactions", method=RequestMethod.GET)
	public List<Transaction> getAllTransactions()	{
		
		return transactionRepository.findAll();				
	}	
	
	@CrossOrigin(origins = {"https://zceptra-ui.herokuapp.com", "http://localhost:4200"})
	@RequestMapping(value="get-transaction", method=RequestMethod.GET)
	public Transaction getTransaction(@RequestParam Long id)	{
		
		return transactionRepository.getOne(id);				
	}	
	
	@CrossOrigin(origins = {"https://zceptra-ui.herokuapp.com", "http://localhost:4200"})
	@RequestMapping(value="delete-transactions", method=RequestMethod.POST)
	public void deleteTransactions(@RequestBody Collection<Long> transactionsToDelete)	{
		
		for(Long transactionToDelete: transactionsToDelete)	{
			
			rollBackOldSummary(transactionRepository.getOne(transactionToDelete));			
			transactionRepository.deleteById(transactionToDelete);
		}						
	}	
}
