package com.zceptra.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zceptra.entities.Account;
import com.zceptra.entities.Category;
import com.zceptra.entities.OrganizingEntityType;
import com.zceptra.entities.TransactionType;
import com.zceptra.repositories.AccountRepository;
import com.zceptra.repositories.CategoryRepository;
import com.zceptra.repositories.TransactionTypeRepository;

@RestController
public class TransactionTypeController {
	
	@Autowired
	private TransactionTypeRepository repository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@CrossOrigin(origins = {"https://zceptra-ui.herokuapp.com", "http://localhost:4200"})
	@RequestMapping(value="get-all-transaction-types")
	public Iterable<TransactionType> getAllTransactionTypes()	{
		
		return repository.findAll();
	}
	
	@CrossOrigin(origins = {"https://zceptra-ui.herokuapp.com", "http://localhost:4200"})
	@RequestMapping(value="get-transaction-type")
	public TransactionType getTransactionType(@RequestParam Long id)	{
		
		return addTransientInfo(repository.findOne(id));
	}	
	
	private TransactionType addTransientInfo(TransactionType transactionType) {
		
		transactionType.setDebitableAccounts(getDebitableAccounts(transactionType));
		transactionType.setCreditableAccounts(getCreditableAccounts(transactionType));
		
		return transactionType;
	}
	
	private List<Account> getDebitableAccounts(TransactionType transactionType)	{

		if(transactionType.getDebitAccountOrganizingEntityType() == OrganizingEntityType.ACCOUNT)	{			
			return CollectionUtilities.getListFrom(accountRepository.findAll(transactionType.getDebitableEntities()));
		}
		else	{
			List<Account> debitableAccounts = new ArrayList<>();
			Iterable<Category> categories = categoryRepository.findAll(transactionType.getDebitableEntities());
			for(Category category: categories) {
				debitableAccounts.addAll(category.getAccounts());				
			}
			
			return debitableAccounts;
		}	
	}
	
	private List<Account> getCreditableAccounts(TransactionType transactionType)	{

		if(transactionType.getCreditAccountOrganizingEntityType() == OrganizingEntityType.ACCOUNT)	{			
			return CollectionUtilities.getListFrom(accountRepository.findAll(transactionType.getCreditableEntities()));
		}
		else	{
			List<Account> creditableAccounts = new ArrayList<>();
			Iterable<Category> categories = categoryRepository.findAll(transactionType.getCreditableEntities());
			for(Category category: categories) {
				creditableAccounts.addAll(category.getAccounts());				
			}
			
			return creditableAccounts;
		}	
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
		transactionType.setCreditableEntities(editedTransactionType.getCreditableEntities());
		transactionType.setCreditAccountHidden(editedTransactionType.getCreditAccountHidden());
		transactionType.setCreditAccountLeading(editedTransactionType.getCreditAccountLeading());
		
		transactionType.setDebitAccountLabel(editedTransactionType.getDebitAccountLabel());
		transactionType.setDebitableEntities(editedTransactionType.getDebitableEntities());
		transactionType.setDebitAccountHidden(editedTransactionType.getDebitAccountHidden());
		
		transactionType.setDebitAccountOrganizingEntityType(editedTransactionType.getDebitAccountOrganizingEntityType());
		transactionType.setCreditAccountOrganizingEntityType(editedTransactionType.getCreditAccountOrganizingEntityType());		
		
		return repository.save(transactionType);
	}

	public AccountRepository getAccountRepository() {
		return accountRepository;
	}

	public void setAccountRepository(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public CategoryRepository getCategoryRepository() {
		return categoryRepository;
	}

	public void setCategoryRepository(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
}
