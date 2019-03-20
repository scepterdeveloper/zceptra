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
import com.zceptra.entities.SummaryInterval;
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
	
	private void maintainSummaryForAccount(Account account, int year, int month, AccountingOperation operation, double amount) throws Exception	{
		
		AccountSummary accountSummary = null;
		SummaryInterval interval = account.getCategory().getInterval();
		List<AccountSummary> accountSummaryList = null;
		
		if(interval == SummaryInterval.MONTHLY) accountSummaryList = summaryRepository.findByAccountIdAndYearAndMonth(account.getId(), year, month);
		else if(interval == SummaryInterval.YEARLY) accountSummaryList = summaryRepository.findByAccountIdAndYear(account.getId(), year);
		else if(interval == SummaryInterval.COMPLETE) accountSummaryList = summaryRepository.findByAccountId(account.getId());
		else throw new Exception("Unsupported SummaryInterval");
		
		
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
	
	private void maintainSummaryForTransaction(Transaction transaction) throws Exception	{
		
		if(transaction.getId()!=null) rollBackOldSummary(transaction);
		int year = transaction.getDate().getYear();
		int month = transaction.getDate().getMonthValue();
		maintainSummaryForAccount(transaction.getAccount(), year, month, AccountingOperation.DEBIT, transaction.getAmount());
		maintainSummaryForAccount(transaction.getParticipatingAccount(), year, month, AccountingOperation.CREDIT, transaction.getAmount());				
	}
	
	private void rollBackOldSummary(Transaction transaction) throws Exception	{
		
		Transaction unmodifiedTransaction = transactionRepository.getOne(transaction.getId());
		rollBackSummaryForAccount(unmodifiedTransaction.getAccount(), unmodifiedTransaction, AccountingOperation.DEBIT);
		rollBackSummaryForAccount(unmodifiedTransaction.getParticipatingAccount(), unmodifiedTransaction, AccountingOperation.CREDIT);		
	}
	
	private void rollBackSummaryForAccount(Account account, Transaction transaction, AccountingOperation operation) throws Exception	{
		
		AccountSummary accountSummary = null;
		SummaryInterval iInterval = account.getCategory().getInterval();
		int year = transaction.getDate().getYear();
		int month = transaction.getDate().getMonthValue();			
		
		if(iInterval == SummaryInterval.MONTHLY) accountSummary = summaryRepository.findByAccountIdAndYearAndMonth(account.getId(), year, month).get(0);
		else if(iInterval == SummaryInterval.YEARLY) accountSummary = summaryRepository.findByAccountIdAndYear(account.getId(), year).get(0);
		else if(iInterval == SummaryInterval.COMPLETE) accountSummary = summaryRepository.findByAccountId(account.getId()).get(0);
		else throw new Exception("Unsupported SummaryInterval");		
		
		if(operation == AccountingOperation.DEBIT) accountSummary.debit(transaction.getAmount()*-1);
		else accountSummary.credit(transaction.getAmount()*-1);
	}
	
	@CrossOrigin(origins = {"https://zceptra-ui.herokuapp.com", "http://localhost:4200"})
	@RequestMapping(value="save-transaction", method=RequestMethod.POST)
	@ResponseBody
	public Transaction saveTransaction(@RequestBody Transaction editedTransaction) throws Exception	{
			
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
	public void deleteTransactions(@RequestBody Collection<Long> transactionsToDelete) throws Exception	{
		
		for(Long transactionToDelete: transactionsToDelete)	{
			
			rollBackOldSummary(transactionRepository.getOne(transactionToDelete));			
			transactionRepository.deleteById(transactionToDelete);
		}						
	}	
}
