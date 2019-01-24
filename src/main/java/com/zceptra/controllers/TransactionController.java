package com.zceptra.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zceptra.api.CreateTransactionRequest;
import com.zceptra.entities.Category;
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
		
		transaction.setAccount(accountRepository.findOne(request.getAccountId()));
		transaction.setParticipatingAccount(accountRepository.findOne(request.getParticipatingAccountId()));
		
		transactionRepository.save(transaction);		
	}
	
	@CrossOrigin(origins = {"https://zceptra-ui.herokuapp.com", "http://localhost:4200"})
	@RequestMapping(value="save-transaction", method=RequestMethod.POST)
	@ResponseBody
	public Transaction saveTransaction(@RequestBody Transaction editedTransaction)	{
		
		//System.out.print("Txn: " + editedTransaction.getDate() + " / " + editedTransaction.getParticipatingAccount().getId());
		
		Transaction transaction = null;
		
		if(editedTransaction.getId() != null)	{
			
			transaction = transactionRepository.findOne(editedTransaction.getId());
		}
		else {
			transaction = new Transaction();
		}
		
		/*category.setName(editedCategory.getName());
		category.setDescription(editedCategory.getDescription());*/
		
		Transaction savedTransaction = transactionRepository.save(editedTransaction);
		
		return savedTransaction;
	}
	
	
	
}
