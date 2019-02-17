package com.zceptra.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zceptra.api.CreateTransactionRequest;
import com.zceptra.entities.Transaction;
import com.zceptra.repositories.AccountRepository;
import com.zceptra.repositories.TransactionRepository;

@RestController
public class TransactionController {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
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
	
	@CrossOrigin(origins = {"https://zceptra-ui.herokuapp.com", "http://localhost:4200"})
	@RequestMapping(value="save-transaction", method=RequestMethod.POST)
	@ResponseBody
	public Transaction saveTransaction(@RequestBody Transaction editedTransaction)	{
				
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
}
