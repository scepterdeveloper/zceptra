package com.zceptra.controllers;

import java.time.LocalDate;
import java.time.YearMonth;
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
	
	private void maintainSummaryForAccount(Account account, Transaction transaction, int year, int month, AccountingOperation operation, double amount) throws Exception	{
		
		AccountSummary encompassingTimeline = null;
		SummaryInterval interval = account.getCategory().getInterval();
		List<AccountSummary> accountSummaryList = null;
		LocalDate transactionDate = transaction.getDate().toLocalDate();
		
		LocalDate validFrom = LocalDate.of(1900, 1, 1);
		LocalDate validTo = LocalDate.of(9999, 12, 31);
		
		if(interval == SummaryInterval.MONTHLY) {
			validFrom = LocalDate.of(year, month, 1);
			YearMonth yearMonth = YearMonth.of(year, month);
			validTo = LocalDate.of(year, month, yearMonth.lengthOfMonth());
		}
		else if(interval == SummaryInterval.YEARLY) {
			validFrom = LocalDate.of(year, 1, 1);
			validTo = LocalDate.of(year, 12, 31);
		}
		
		accountSummaryList = summaryRepository.findAllByAccountIdAndValidFromLessThanEqualAndValidToGreaterThanEqual(account.getId(), transactionDate, transactionDate);
		
		if(accountSummaryList!=null && accountSummaryList.size()>0)	{
			encompassingTimeline= accountSummaryList.get(0);
		}
		else throw new Exception("No encompassing account summary, check if default for the account was created.");
		
		if(!(encompassingTimeline.getValidFrom().equals(validFrom) && encompassingTimeline.getValidTo().equals(validTo)))	{
			
			//Do Splitting
			if(!encompassingTimeline.getValidFrom().equals(validFrom))	{
				
				AccountSummary precedingTimeline = new AccountSummary();
				precedingTimeline.setAccount(account);
				precedingTimeline.setValidFrom(encompassingTimeline.getValidFrom());
				precedingTimeline.setValidTo(validFrom.minusDays(1));
				summaryRepository.save(precedingTimeline);
			}
			
			if(!encompassingTimeline.getValidTo().equals(validTo))	{
				
				AccountSummary succeedingTimeline = new AccountSummary();
				succeedingTimeline.setAccount(account);
				succeedingTimeline.setValidFrom(validTo.plusDays(1));
				succeedingTimeline.setValidTo(encompassingTimeline.getValidTo());
				summaryRepository.save(succeedingTimeline);				
			}
			
			encompassingTimeline.setValidFrom(validFrom);
			encompassingTimeline.setValidTo(validTo);			
		}
				
		encompassingTimeline.setMonth(month);
		encompassingTimeline.setYear(year);
		
		if(operation == AccountingOperation.DEBIT) encompassingTimeline.debit(amount);
		else encompassingTimeline.credit(amount);
		summaryRepository.save(encompassingTimeline);							
	}
	
	private void maintainSummaryForTransaction(Transaction transaction) throws Exception	{
		
		if(transaction.getId()!=null) rollBackOldSummary(transaction);
		int year = transaction.getDate().getYear();
		int month = transaction.getDate().getMonthValue();
		maintainSummaryForAccount(transaction.getAccount(), transaction, year, month, AccountingOperation.DEBIT, transaction.getAmount());
		maintainSummaryForAccount(transaction.getParticipatingAccount(), transaction, year, month, AccountingOperation.CREDIT, transaction.getAmount());				
	}
	
	private void rollBackOldSummary(Transaction transaction) throws Exception	{
		
		Transaction unmodifiedTransaction = transactionRepository.getOne(transaction.getId());
		rollBackSummaryForAccount(unmodifiedTransaction.getAccount(), unmodifiedTransaction, AccountingOperation.DEBIT);
		rollBackSummaryForAccount(unmodifiedTransaction.getParticipatingAccount(), unmodifiedTransaction, AccountingOperation.CREDIT);		
	}
	
	private void rollBackSummaryForAccount(Account account, Transaction transaction, AccountingOperation operation) throws Exception	{
		
		AccountSummary accountSummary = null;
		SummaryInterval iInterval = account.getCategory().getInterval();
		LocalDate transactionDate = transaction.getDate().toLocalDate();	
		
		if(iInterval == SummaryInterval.MONTHLY) accountSummary = summaryRepository.findAllByAccountIdAndValidFromLessThanEqualAndValidToGreaterThanEqual(account.getId(), transactionDate, transactionDate).get(0);
		else if(iInterval == SummaryInterval.YEARLY) accountSummary = summaryRepository.findAllByAccountIdAndValidFromLessThanEqualAndValidToGreaterThanEqual(account.getId(), transactionDate, transactionDate).get(0);
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
