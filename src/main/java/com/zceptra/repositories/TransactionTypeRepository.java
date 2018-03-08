package com.zceptra.repositories;

import org.springframework.data.repository.CrudRepository;

import com.zceptra.entities.TransactionType;

public interface TransactionTypeRepository extends CrudRepository<TransactionType, Long> {

}
