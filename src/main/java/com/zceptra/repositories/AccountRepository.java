package com.zceptra.repositories;

import org.springframework.data.repository.CrudRepository;

import com.zceptra.entities.Account;

public interface AccountRepository extends CrudRepository<Account, Long> {
	
}
