package com.zceptra.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zceptra.entities.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
