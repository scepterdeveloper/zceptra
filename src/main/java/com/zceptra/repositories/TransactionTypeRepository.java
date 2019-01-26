package com.zceptra.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zceptra.entities.TransactionType;

public interface TransactionTypeRepository extends JpaRepository<TransactionType, Long> {

}
