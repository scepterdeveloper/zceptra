package com.zceptra.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zceptra.entities.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	
	public List<Account> findByCategoryId(Long categoryId);
	
}
