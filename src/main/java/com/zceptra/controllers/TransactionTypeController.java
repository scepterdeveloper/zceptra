package com.zceptra.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zceptra.entities.TransactionType;
import com.zceptra.repositories.TransactionTypeRepository;

@RestController
public class TransactionTypeController {
	
	@Autowired
	private TransactionTypeRepository repository;
	
	@CrossOrigin(origins = {"https://zceptra-ui.herokuapp.com", "http://localhost:4200"})
	@RequestMapping(value="get-all-transaction-types")
	public Iterable<TransactionType> getAllTransactionTypes()	{
		
		return repository.findAll();
	}
	
	@CrossOrigin(origins = {"https://zceptra-ui.herokuapp.com", "http://localhost:4200"})
	@RequestMapping(value="get-transaction-type")
	public TransactionType getTransactionType(@RequestParam Long id)	{
		
		return repository.findOne(id);
	}	
	
	@CrossOrigin(origins = {"https://zceptra-ui.herokuapp.com", "http://localhost:4200"})
	@RequestMapping(value="edit-transaction-type", method=RequestMethod.POST)
	@ResponseBody
	public TransactionType saveTransactionType(@RequestBody TransactionType editedTransactionType)	{
		
		TransactionType transactionType = null;
		
		if(editedTransactionType.getId() != null)	{
			
			transactionType = repository.findOne(editedTransactionType.getId());
		}
		else {
			transactionType = new TransactionType();
		}
		
		transactionType.setName(editedTransactionType.getName());
		transactionType.setDescription(editedTransactionType.getDescription());
		transactionType.setDateLabel(editedTransactionType.getDateLabel());
		transactionType.setAmountLabel(editedTransactionType.getAmountLabel());
		transactionType.setDescriptionLabel(editedTransactionType.getDescriptionLabel());
		
		transactionType.setCreditAccountLabel(editedTransactionType.getCreditAccountLabel());
		transactionType.setCreditableAccounts(editedTransactionType.getCreditableAccounts());
		transactionType.setCreditAccountHidden(editedTransactionType.getCreditAccountHidden());
		transactionType.setCreditAccountLeading(editedTransactionType.getCreditAccountLeading());
		
		transactionType.setDebitAccountLabel(editedTransactionType.getDebitAccountLabel());
		transactionType.setDebitableAccounts(editedTransactionType.getDebitableAccounts());
		transactionType.setDebitAccountHidden(editedTransactionType.getDebitAccountHidden());
		
		transactionType.setDebitAccountOrganizingEntityType(editedTransactionType.getDebitAccountOrganizingEntityType());
		
		
		return repository.save(transactionType);
	}
}
