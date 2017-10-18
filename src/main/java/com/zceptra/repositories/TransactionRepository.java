package com.zceptra.repositories;

import org.springframework.data.repository.CrudRepository;

import com.zceptra.entities.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
}
