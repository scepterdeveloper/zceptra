package com.zceptra.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.zceptra.entities.Account;

public interface AccountRepository extends CrudRepository<Account, Long> {
	
	public List<Account> findByCategoryId(Long categoryId);
	
}
